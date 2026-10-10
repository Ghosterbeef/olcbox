package org.olcbox.app.ui.features.locations

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import org.olcbox.app.data.LocationsRepository
import org.olcbox.app.data.model.LocationConfig
import org.olcbox.app.data.model.LocationMetadata
import org.olcbox.app.ui.localization.AppLocalization

data class LocationItem(
    val storageId: String,
    val fullName: String,
    val config: LocationConfig?,
    val subscriptionUrl: String? = null,
    val metadata: LocationMetadata? = null
)

sealed class PingsState {
    object Idle : PingsState()

    data class Loading(
        val pendingLocationIds: Set<String>,
        val currentPings: Map<String, Int?> = emptyMap(),
        val lastPings: Map<String, Int?>? = null
    ) : PingsState()

    data class Success(val pings: Map<String, Int?>) : PingsState()

    data class Error(
        val message: String,
        val lastPings: Map<String, Int?>? = null
    ) : PingsState()
}

class LocationViewModel(
    private val locationsRepository: LocationsRepository
) : ViewModel() {

    private val pingSemaphore = Semaphore(5)

    var locations = mutableStateListOf<LocationItem>()
        private set

    var selectedLocationId by mutableStateOf<String?>(null)
        private set

    var pingsState by mutableStateOf<PingsState>(PingsState.Idle)
        private set

    private val activePingJobs = mutableMapOf<String, Job>()
    private var loadLocationsRequest = 0L
    private var loadLocationsJob: Job? = null

    var editingId by mutableStateOf<String?>(null)
        private set

    var editingConfig by mutableStateOf(LocationConfig())
        private set

    var editingName by mutableStateOf("")
        private set

    var editingServiceProvider by mutableStateOf(LocationConfig.DEFAULT_BYPASS_PROVIDER)
        private set

    private val providerDrafts = mutableMapOf<String, ProviderDraft>()

    var isSaving by mutableStateOf(false)
        private set

    var nameError by mutableStateOf<String?>(null)
        private set

    var serverError by mutableStateOf<String?>(null)
        private set

    var keyError by mutableStateOf<String?>(null)
        private set

    var dnsError by mutableStateOf<String?>(null)
        private set

    val isFormValid: Boolean
        get() = nameError == null &&
                serverError == null &&
                keyError == null &&
                dnsError == null &&
                editingName.isNotBlank() &&
                editingConfig.id.isNotBlank() &&
                editingConfig.key.isNotBlank()

    init {
        loadLocations()
        viewModelScope.launch {
            locationsRepository.changes
                .drop(1)
                .collect {
                    loadLocations()
                }
        }
    }

    fun loadLocations(onComplete: () -> Unit = {}) {
        val requestId = ++loadLocationsRequest
        loadLocationsJob?.cancel()
        loadLocationsJob = viewModelScope.launch {
            val bundle = locationsRepository.getBundle()
            val savedConfigs = bundle.locations
            val currentSelectedId = bundle.activeLocationId

            val nextLocations = savedConfigs.map { entry ->
                val normalized = entry.location
                LocationItem(
                    storageId = entry.storageId,
                    fullName = normalized.displayName(),
                    config = normalized,
                    subscriptionUrl = entry.subscriptionUrl,
                    metadata = entry.metadata
                )
            }

            if (requestId != loadLocationsRequest) return@launch

            locations.clear()
            locations.addAll(nextLocations)

            val nextSelectedId = if (
                nextLocations.isNotEmpty() &&
                (
                        currentSelectedId.isNullOrBlank() ||
                                nextLocations.none { it.storageId == currentSelectedId }
                        )
            ) {
                nextLocations.first().storageId
            } else {
                currentSelectedId
            }

            if (selectedLocationId != nextSelectedId) {
                selectedLocationId = nextSelectedId
                if (nextSelectedId != null) {
                    locationsRepository.setActiveLocationId(nextSelectedId)
                }
            }

            val validIds = nextLocations.map { it.storageId }.toSet()
            activePingJobs.keys.retainAll(validIds)

            when (val current = pingsState) {
                is PingsState.Success -> {
                    pingsState = PingsState.Success(
                        current.pings.filterKeys { it in validIds }
                    )
                }

                is PingsState.Loading -> {
                    pingsState = current.copy(
                        pendingLocationIds = current.pendingLocationIds.intersect(validIds),
                        currentPings = current.currentPings.filterKeys { it in validIds },
                        lastPings = current.lastPings?.filterKeys { it in validIds }
                    )
                }

                else -> Unit
            }

            onComplete()
        }
    }

    fun refreshPings(
        targetLocationIds: List<String>? = null,
        performPing: suspend (LocationConfig) -> Long?,
        onComplete: (onlineCount: Int, totalCount: Int) -> Unit = { _, _ -> },
        onError: (String) -> Unit = {}
    ) {
        val previousPings = currentPingsSnapshot()
        val locationsSnapshot = locations.toList()

        val pingableLocations = locationsSnapshot
            .filter { location ->
                location.config?.isComplete() == true &&
                        (targetLocationIds == null || targetLocationIds.contains(location.storageId))
            }
            .filterNot { location ->
                activePingJobs.containsKey(location.storageId)
            }

        if (locationsSnapshot.isEmpty()) {
            if (activePingJobs.isEmpty()) {
                pingsState = PingsState.Success(emptyMap())
            }
            onComplete(0, 0)
            return
        }

        if (pingableLocations.isEmpty()) {
            emitPingState(previousPings)
            onComplete(0, 0)
            return
        }

        var completedForThisRequest = 0
        var onlineForThisRequest = 0
        val totalForThisRequest = pingableLocations.size
        val jobsToStart = mutableListOf<Job>()

        pingableLocations.forEach { location ->
            val job = viewModelScope.launch(start = CoroutineStart.LAZY) {
                try {
                    val ping = try {
                        pingSemaphore.withPermit {
                            checkLocationPing(location, performPing)?.toInt()
                        }
                    } catch (e: CancellationException) {
                        throw e
                    } catch (_: Exception) {
                        null
                    }

                    val updatedPings = currentPingsSnapshot().toMutableMap()
                    updatedPings[location.storageId] = ping

                    activePingJobs.remove(location.storageId)

                    if (ping != null) {
                        onlineForThisRequest++
                    }

                    completedForThisRequest++

                    emitPingState(updatedPings.toMap())

                    if (completedForThisRequest == totalForThisRequest) {
                        onComplete(onlineForThisRequest, totalForThisRequest)
                    }
                } catch (e: CancellationException) {
                    activePingJobs.remove(location.storageId)
                    emitPingState()
                    throw e
                } catch (e: Exception) {
                    activePingJobs.remove(location.storageId)

                    val message = e.message ?: "HTTP ping failed"
                    onError(message)

                    emitPingState()
                }
            }

            activePingJobs[location.storageId] = job
            jobsToStart.add(job)
        }

        emitPingState(previousPings)
        jobsToStart.forEach { it.start() }
    }

    private fun emitPingState(basePings: Map<String, Int?> = currentPingsSnapshot()) {
        val pendingIds = activePingJobs.keys.toSet()
        pingsState = if (pendingIds.isNotEmpty()) {
            PingsState.Loading(
                pendingLocationIds = pendingIds,
                currentPings = basePings,
                lastPings = basePings
            )
        } else {
            PingsState.Success(basePings)
        }
    }

    private suspend fun checkLocationPing(
        location: LocationItem,
        performPing: suspend (LocationConfig) -> Long?
    ): Long? {
        val config = location.config ?: return null
        return performPing(config)
    }

    private fun currentPingsSnapshot(): Map<String, Int?> {
        return when (val state = pingsState) {
            is PingsState.Success -> state.pings
            is PingsState.Loading -> state.currentPings
            is PingsState.Error -> state.lastPings ?: emptyMap()
            PingsState.Idle -> emptyMap()
        }
    }

    fun selectLocation(id: String, onSelected: () -> Unit = {}) {
        if (selectedLocationId == id) return
        selectedLocationId = id
        viewModelScope.launch {
            locationsRepository.setActiveLocationId(id)
            onSelected()
        }
    }

    fun startEditing(id: String?) {
        editingId = id
        providerDrafts.clear()

        if (id == null) {
            editingConfig = LocationConfig()
            editingName = ""
            nameError = null
            serverError = null
            keyError = null
            dnsError = null
        } else {
            val item = locations.find { it.storageId == id }
            editingConfig = item?.config?.normalized() ?: LocationConfig()
            editingName = editingConfig.name
            validateName(editingName)
            validateServer(editingConfig.id)
            validateKey(editingConfig.key)
            validateDnsServer(editingConfig.dnsServer)
        }
        val provider = LocationConfig.normalizeProvider(editingConfig.bypassProvider)
        editingServiceProvider = if (provider == LocationConfig.PROVIDER_JITSI) {
            LocationConfig.DEFAULT_BYPASS_PROVIDER
        } else {
            provider
        }
        providerDrafts[provider] = ProviderDraft(
            room = editingConfig.id,
            key = editingConfig.key
        )
    }

    fun onNameChanged(value: String) {
        editingName = value
        validateName(value)
    }

    fun onServerChanged(value: String) {
        editingConfig = editingConfig.copy(id = value)
        validateServer(value)
    }

    fun onSniChanged(value: String) = Unit

    fun onPasswordChanged(value: String) {
        editingConfig = editingConfig.copy(key = value)
        validateKey(value)
    }

    fun onBypassProviderChanged(value: String) {
        val provider = LocationConfig.normalizeProvider(value)
        val currentProvider = LocationConfig.normalizeProvider(editingConfig.bypassProvider)
        if (provider == currentProvider) return

        providerDrafts[currentProvider] = ProviderDraft(
            room = editingConfig.id,
            key = editingConfig.key
        )

        if (provider != LocationConfig.PROVIDER_JITSI) {
            editingServiceProvider = provider
        }

        val restored = providerDrafts[provider] ?: ProviderDraft()

        editingConfig = editingConfig.copy(
            bypassProvider = provider,
            transport = if (provider == LocationConfig.PROVIDER_JITSI) {
                LocationConfig.TRANSPORT_DATACHANNEL
            } else {
                LocationConfig.normalizeTransport(editingConfig.transport, provider)
            },
            id = restored.room,
            key = restored.key
        )
        serverError = null
        keyError = null
    }

    fun onTransportChanged(value: String) {
        editingConfig = editingConfig.copy(
            transport = LocationConfig.normalizeTransport(value, editingConfig.bypassProvider)
        )
    }

    fun onVp8FpsChanged(value: String) {
        editingConfig = editingConfig.copy(
            vp8Fps = value.filter { it.isDigit() }.toIntOrNull() ?: 0
        )
    }

    fun onVp8BatchChanged(value: String) {
        editingConfig = editingConfig.copy(
            vp8Batch = value.filter { it.isDigit() }.toIntOrNull() ?: 0
        )
    }

    fun onVp8TracksChanged(value: String) {
        editingConfig = editingConfig.copy(
            vp8Tracks = value.filter { it.isDigit() }.toIntOrNull() ?: 1
        )
    }

    fun onDnsServerChanged(value: String) {
        editingConfig = editingConfig.copy(
            dnsServer = value
                .replace("\r", "")
                .replace("\n", "")
                .take(LocationConfig.MAX_DNS_SERVER_LENGTH)
        )
        validateDnsServer(editingConfig.dnsServer)
    }

    private fun validateName(name: String) {
        val strings = AppLocalization.strings
        nameError = when {
            name.isBlank() -> strings.errorNameEmpty
            name.length > 30 -> strings.errorNameTooLong
            else -> null
        }
    }

    private fun validateServer(server: String) {
        val strings = AppLocalization.strings
        val roomLabel = if (editingConfig.bypassProvider == LocationConfig.PROVIDER_JITSI) {
            strings.roomUrlLabel
        } else {
            strings.roomIdLabel
        }
        serverError = when {
            server.isBlank() -> strings.errorRoomEmpty(roomLabel)
            server.length > 256 -> strings.errorRoomTooLong(roomLabel)
            else -> null
        }
    }

    private fun validateKey(key: String) {
        val strings = AppLocalization.strings
        keyError = when {
            key.isBlank() -> strings.errorKeyEmpty
            !key.matches(Regex("^[a-fA-F0-9]{64}$")) -> strings.errorKeyInvalid
            else -> null
        }
    }

    private fun validateDnsServer(dnsServer: String) {
        val strings = AppLocalization.strings
        dnsError = if (LocationConfig.isValidDnsServer(dnsServer)) {
            null
        } else {
            strings.errorDnsInvalid
        }
    }

    fun saveEditing(onComplete: () -> Unit) {
        validateName(editingName)
        validateServer(editingConfig.id)
        validateKey(editingConfig.key)
        validateDnsServer(editingConfig.dnsServer)

        if (!isFormValid || isSaving) return

        viewModelScope.launch {
            isSaving = true
            try {
                val id = editingId ?: "custom_${(100..999).random()}"
                val finalConfig = editingConfig.copy(name = editingName).normalized()

                locationsRepository.saveLocation(id, finalConfig)
                locationsRepository.setActiveLocationId(id)

                loadLocations()

                delay(600)
                onComplete()
            } finally {
                isSaving = false
            }
        }
    }

    fun deleteLocation(id: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            locationsRepository.deleteLocation(id)
            activePingJobs[id]?.cancel()
            activePingJobs.remove(id)

            val currentSelected = locationsRepository.getActiveLocationId()
            if (currentSelected == id) {
                val remaining = locationsRepository.getBundle().locations
                val nextSelected = remaining.firstOrNull()?.storageId
                if (nextSelected != null) {
                    locationsRepository.setActiveLocationId(nextSelected)
                }
            }

            loadLocations()
            onComplete()
        }
    }
}

private data class ProviderDraft(
    val room: String = "",
    val key: String = ""
)
