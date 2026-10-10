import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.rememberTrayState
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dimension
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import java.security.SecureRandom
import kotlin.math.min
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import org.olcbox.app.CurrentAppInfo
import org.olcbox.app.desktop.DesktopDeepLinks
import org.olcbox.app.desktop.DesktopDeepLinkRegistration
import org.olcbox.app.data.datasource.JvmLocationsDataSourceImpl
import org.olcbox.app.data.datasource.LocationsRepositoryImpl
import org.olcbox.app.data.exporter.JvmLogExporter
import org.olcbox.app.data.identity.PersistentDeviceIdentityProvider
import org.olcbox.app.data.importer.JvmConfigImporter
import org.olcbox.app.data.share.ConfigShareService
import org.olcbox.app.data.share.SubscriptionShareItem
import org.olcbox.app.ui.OlcboxAppContent
import org.olcbox.app.ui.components.ApplicationSocksProxySettings
import org.olcbox.app.ui.components.ApplicationRoutingModeOption
import org.olcbox.app.ui.components.ApplicationSettingsSheet
import org.olcbox.app.ui.components.ApplicationUpdateOfferSheet
import org.olcbox.app.ui.features.home.HomeScreenViewModel
import org.olcbox.app.ui.features.locations.LocationItem
import org.olcbox.app.ui.features.locations.LocationViewModel
import org.olcbox.app.ui.localization.AppStrings
import org.olcbox.app.ui.localization.LocalAppStrings
import org.olcbox.app.ui.navigation.AppScreen
import org.olcbox.app.ui.theme.AppTheme
import org.olcbox.app.update.AppUpdateInfo
import org.olcbox.app.update.AppUpdateSettings
import org.olcbox.app.update.AppUpdateService
import org.olcbox.app.update.JvmUpdateInstaller
import org.olcbox.app.update.JvmUpdateSettingsStore
import org.olcbox.app.update.identity
import org.olcbox.app.update.isDownloaded
import org.olcbox.app.update.isUpdateCheckDue
import org.olcbox.app.update.shouldShowOffer
import org.olcbox.app.vpn.DesktopSocksProxySettings
import org.olcbox.app.vpn.DesktopRoutingMode
import org.olcbox.app.vpn.DesktopVpnManager
import org.olcbox.app.vpn.JvmDesktopSocksProxySettingsStore

private class DesktopAppDependencies {
    private val locationsDataSource = JvmLocationsDataSourceImpl()
    val configImporter = JvmConfigImporter()

    val locationsRepository = LocationsRepositoryImpl(locationsDataSource)
    val updateService = AppUpdateService(
        deviceIdentityProvider = PersistentDeviceIdentityProvider(locationsDataSource)
    )
    val updateSettingsStore = JvmUpdateSettingsStore()
    val updateInstaller = JvmUpdateInstaller()
    val socksProxySettingsStore = JvmDesktopSocksProxySettingsStore()

    val vpnManager = DesktopVpnManager(locationsRepository)

    val homeViewModel = HomeScreenViewModel(
        vpnManager = vpnManager,
        locationsRepository = locationsRepository,
        configImporter = configImporter,
        logExporter = JvmLogExporter()
    )

    val locationViewModel = LocationViewModel(locationsRepository)

    fun close() {
        vpnManager.close()
    }
}

private const val WINDOWS_ELEVATED_START_ARGUMENT = "--olcbox-start-vpn-after-elevation"

fun main(args: Array<String>) {
    val launchArgs = if (args.any { it.startsWith("olcbox:", ignoreCase = true) }) {
        args.filterNot { it == WINDOWS_ELEVATED_START_ARGUMENT }.toTypedArray()
    } else args
    val deepLinks = DesktopDeepLinks.open(
        launchArgs,
        waitForPreviousExit = WINDOWS_ELEVATED_START_ARGUMENT in launchArgs
    )
    val dependencies = DesktopAppDependencies()

    Runtime.getRuntime().addShutdownHook(
        Thread(
            {
                dependencies.close()
            },
            "olcbox-shutdown-cleanup"
        )
    )

    application {
        DesktopApp(
            dependencies = dependencies,
            deepLinks = deepLinks,
            initialUri = launchArgs.firstOrNull { it.startsWith("olcbox:", ignoreCase = true) },
            autoStartVpn = WINDOWS_ELEVATED_START_ARGUMENT in args
        )
    }
}

@Composable
private fun DesktopApp(
    dependencies: DesktopAppDependencies,
    deepLinks: DesktopDeepLinks,
    initialUri: String?,
    autoStartVpn: Boolean
) {
    var isWindowVisible by remember { mutableStateOf(true) }
    var currentScreen by remember { mutableStateOf<AppScreen>(AppScreen.Home) }
    var sharePayload by remember { mutableStateOf<Pair<String, String>?>(null) }
    var showDesktopSettings by remember { mutableStateOf(false) }
    var updateSettings by remember { mutableStateOf(AppUpdateSettings()) }
    var updateOffer by remember { mutableStateOf<AppUpdateInfo?>(null) }
    var updateMessage by remember { mutableStateOf<String?>(null) }
    var updateProgress by remember { mutableStateOf<Float?>(null) }
    var desktopNotice by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    val trayState = rememberTrayState()

    suspend fun saveUpdateSettings(nextSettings: AppUpdateSettings) {
        updateSettings = nextSettings
        dependencies.updateSettingsStore.save(nextSettings)
    }

    fun postponeUpdate(info: AppUpdateInfo) {
        updateOffer = null
        scope.launch {
            saveUpdateSettings(updateSettings.postponed(info.version))
        }
    }

    fun downloadUpdate(info: AppUpdateInfo) {
        scope.launch {
            updateProgress = 0f
            updateMessage = "Downloading ${info.asset.name}..."
            val file = dependencies.updateService.downloadAsset(info.asset) { progress ->
                updateProgress = progress
            }
            updateProgress = null
            if (file != null) {
                updateMessage = "Installing ${info.asset.name}"
                dependencies.updateInstaller.install(file)
            } else {
                updateMessage = "Download failed"
            }
        }
    }

    fun checkUpdate(manual: Boolean) {
        scope.launch {
            if (manual) updateMessage = "Checking updates..."
            val result = dependencies.updateService.check()
            val nowMs = System.currentTimeMillis()
            saveUpdateSettings(updateSettings.copy(lastCheckAtEpochMs = nowMs))
            if (result.isSuccess) {
                val available = result.getOrNull()
                if (available == null) {
                    if (manual) updateMessage = "Installed version is current"
                } else if (available.isDownloaded()) {
                    updateOffer = null
                    updateMessage = "Update downloaded. Ready to install."
                } else if (manual || updateSettings.shouldShowOffer(available.version, nowMs)) {
                    updateOffer = available
                    if (manual) updateMessage = null
                }
            } else if (manual) {
                updateMessage = "Update check failed"
            }
        }
    }

    LaunchedEffect(Unit) {
        val loaded = dependencies.updateSettingsStore.load()
        updateSettings = loaded
        DesktopDeepLinkRegistration.ensureRegistered()
        val loadedSocks = dependencies.socksProxySettingsStore.load()
        dependencies.vpnManager.updateSocksProxySettings(loadedSocks)
        if (autoStartVpn) {
            dependencies.homeViewModel.ToggleVpn()
        }
        if (initialUri != null) {
            dependencies.homeViewModel.importLinks.open(initialUri)
        }
        if (loaded.isUpdateCheckDue(System.currentTimeMillis())) {
            checkUpdate(manual = false)
        }
    }

    LaunchedEffect(desktopNotice) {
        if (desktopNotice != null) {
            delay(2200)
            desktopNotice = null
        }
    }

    if (java.awt.SystemTray.isSupported()) {
        Tray(
            state = trayState,
            icon = painterResource("icon.png"),
            tooltip = "olcbox",
            onAction = {
                isWindowVisible = true
            },
            menu = {
                Item("Show", onClick = { isWindowVisible = true })
                Item("Quit", onClick = {
                    dependencies.close()
                    exitApplication()
                })
            }
        )
    }

    Window(
        title = "olcbox",
        visible = isWindowVisible,
        state = rememberWindowState(width = 430.dp, height = 780.dp),
        onCloseRequest = {
            if (java.awt.SystemTray.isSupported()) {
                isWindowVisible = false
            } else {
                dependencies.close()
                exitApplication()
            }
        },
    ) {
        window.minimumSize = Dimension(350, 600)

        LaunchedEffect(deepLinks) {
            deepLinks.events.collect { uri ->
                isWindowVisible = true
                window.isVisible = true
                window.extendedState = window.extendedState and Frame.ICONIFIED.inv()
                window.toFront()
                window.requestFocus()
                if (uri.isNotEmpty()) dependencies.homeViewModel.importLinks.open(uri)
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                dependencies.close()
            }
        }

        AppTheme {
            val strings = LocalAppStrings.current
            val logs by dependencies.homeViewModel.logs.collectAsState()
            val homeState by dependencies.homeViewModel.state.collectAsState()
            val socksProxySettings by dependencies.vpnManager.socksProxySettings.collectAsState()

            fun reloadLocationsAfterImport(onComplete: () -> Unit = {}) {
                dependencies.locationViewModel.loadLocations {
                    dependencies.homeViewModel.loadCurrentConfig(onComplete)
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                OlcboxAppContent(
                    homeViewModel = dependencies.homeViewModel,
                    locationViewModel = dependencies.locationViewModel,
                    currentScreen = currentScreen,
                    onNavigate = { screen ->
                        currentScreen = screen
                    },
                    onToggleClick = {
                        dependencies.homeViewModel.ToggleVpn()
                    },
                    onImportFileRequested = {
                        chooseConfigFile(window, strings.importFromFile)?.let { file ->
                            dependencies.homeViewModel.onFileSelected(
                                fileSource = file,
                                onComplete = {
                                    reloadLocationsAfterImport()
                                }
                            )
                        }
                    },
                    onImportFromClipboardRequested = { onImported, onError ->
                        dependencies.homeViewModel.onPasteFromClipboard(
                            onComplete = {
                                reloadLocationsAfterImport(onImported)
                            },
                            onError = onError
                        )
                    },
                    onScanQrRequested = {},
                    onCopyConfigRequested = {
                        dependencies.homeViewModel.onCopyFullConfigClicked()
                    },
                    onShareLocationRequested = { config ->
                        sharePayload = strings.locationQrTitle to ConfigShareService.olcRtcUri(config)
                    },
                    onSaveLogsRequested = { onSaved, onError ->
                        chooseSaveFile(
                            owner = window,
                            defaultName = dependencies.homeViewModel.suggestedLogsFileName(),
                            dialogTitle = strings.applicationLogs
                        )?.let { file ->
                            dependencies.homeViewModel.onSaveLogsToFile(
                                target = file,
                                onSaved = onSaved,
                                onError = onError
                            )
                        }
                    },
                    showAppSettingsButton = true,
                    showSplitTunnelingButton = false,
                    canScanQr = false,
                    onAppSettingsClick = { showDesktopSettings = true },
                    onSplitTunnelingClick = {},
                    onDeepLinkOpened = {
                        showDesktopSettings = false
                        sharePayload = null
                        updateOffer = null
                    }
                )

                if (showDesktopSettings) {
                    ApplicationSettingsSheet(
                        updateSettings = updateSettings,
                        updateStatusText = updateMessage,
                        updateDownloadProgress = updateProgress,
                        updateOffer = updateOffer,
                        subscriptions = desktopSubscriptionItems(dependencies.locationViewModel.locations.toList()),
                        logs = logs,
                        connectionSummary = "${socksProxySettings.routingMode.effectiveDisplayName(strings)} · " +
                            "SOCKS5 ${socksProxySettings.host}:${socksProxySettings.port}",
                        connectionDetails = buildList {
                            add("Mode" to socksProxySettings.routingMode.effectiveDisplayName(strings))
                            if (socksProxySettings.routingMode.effectiveMode() == DesktopRoutingMode.SystemProxy) {
                                add("PAC URL" to "http://127.0.0.1:10809/proxy.pac")
                                add("PAC Target" to "SOCKS5 ${socksProxySettings.host}:${socksProxySettings.port}")
                            }
                        },
                        socksProxySettings = socksProxySettings.toApplicationSocksProxySettings(),
                        routingModeOptions = DesktopRoutingMode.availableForCurrentPlatform().map { mode ->
                            ApplicationRoutingModeOption(
                                id = mode.name,
                                title = mode.displayName(strings),
                                subtitle = mode.description(strings)
                            )
                        },
                        selectedRoutingModeId = socksProxySettings.routingMode.name,
                        isConnectionActive = homeState.isVpnConnected,
                        onDismiss = { showDesktopSettings = false },
                        onCopyConfigClick = {
                            dependencies.homeViewModel.onCopyFullConfigClicked()
                            desktopNotice = strings.copied
                        },
                        onSaveLogsClick = {
                            chooseSaveFile(
                                owner = window,
                                defaultName = dependencies.homeViewModel.suggestedLogsFileName(),
                                dialogTitle = strings.applicationLogs
                            )?.let { file ->
                                dependencies.homeViewModel.onSaveLogsToFile(
                                    target = file,
                                    onSaved = { message -> updateMessage = message },
                                    onError = { message -> updateMessage = message }
                                )
                            }
                        },
                        onShareLogsClick = {
                            dependencies.homeViewModel.onShareLogs(
                                onShared = { message -> updateMessage = message },
                                onError = { message -> updateMessage = message }
                            )
                        },
                        onUpdateIntervalSelected = { hours ->
                            scope.launch {
                                saveUpdateSettings(updateSettings.copy(intervalHours = hours))
                            }
                        },
                        onCheckUpdatesClick = { checkUpdate(manual = true) },
                        onDownloadUpdateClick = { info -> downloadUpdate(info) },
                        onLaterUpdateClick = { info -> postponeUpdate(info) },
                        onSubscriptionShareClick = { url ->
                            sharePayload = strings.subscriptionQrTitle to ConfigShareService.subscriptionQrText(url)
                        },
                        onSubscriptionRefreshClick = { url, onFinished ->
                            dependencies.homeViewModel.refreshSubscription(url) { updatedCount ->
                                reloadLocationsAfterImport {
                                    dependencies.homeViewModel.restartVpnIfRunning()
                                    updateMessage = if (updatedCount > 0) {
                                        strings.subscriptionUpdated
                                    } else {
                                        strings.subscriptionAlreadyUpToDate
                                    }
                                    onFinished()
                                }
                            }
                        },
                        onSubscriptionRefreshIntervalChanged = { url, intervalMs ->
                            dependencies.homeViewModel.setSubscriptionRefreshInterval(url, intervalMs) {
                                dependencies.locationViewModel.loadLocations()
                                updateMessage = if (intervalMs == null) {
                                    strings.subscriptionRefreshSetToAuto
                                } else {
                                    strings.subscriptionRefreshRateSaved
                                }
                            }
                        },
                        onSubscriptionDeleteClick = { url ->
                            dependencies.homeViewModel.deleteSubscription(url) { removedLocations ->
                                reloadLocationsAfterImport {
                                    dependencies.homeViewModel.restartVpnIfRunning()
                                    updateMessage = strings.subscriptionDeletedSummary(removedLocations)
                                }
                            }
                        },
                        onSocksProxySettingsSaved = { username, password, port ->
                            val settings = socksProxySettings.copy(
                                port = port,
                                username = username,
                                password = password
                            ).normalized()
                            dependencies.vpnManager.updateSocksProxySettings(settings)
                            scope.launch {
                                dependencies.socksProxySettingsStore.save(settings)
                            }
                            desktopNotice = strings.socks5ProxySaved
                            if (homeState.isVpnConnected) {
                                dependencies.homeViewModel.restartVpnIfRunning()
                            }
                        },
                        onSocksProxyPasswordRegenerated = {
                            val settings = socksProxySettings.copy(
                                password = generateDesktopProxyPassword()
                            ).normalized()
                            dependencies.vpnManager.updateSocksProxySettings(settings)
                            scope.launch {
                                dependencies.socksProxySettingsStore.save(settings)
                            }
                            desktopNotice = strings.passwordRegenerated
                            if (homeState.isVpnConnected) {
                                dependencies.homeViewModel.restartVpnIfRunning()
                            }
                        },
                        onRoutingModeSelected = { id ->
                            val mode = runCatching { DesktopRoutingMode.valueOf(id) }
                                .getOrDefault(DesktopRoutingMode.Auto)
                            if (mode != socksProxySettings.routingMode) {
                                val settings = socksProxySettings.copy(routingMode = mode).normalized()
                                dependencies.vpnManager.updateSocksProxySettings(settings)
                                scope.launch {
                                    dependencies.socksProxySettingsStore.save(settings)
                                }
                                desktopNotice = strings.connectionModeSaved
                                if (homeState.isVpnConnected) {
                                    dependencies.homeViewModel.restartVpnIfRunning()
                                }
                            }
                        }
                    )
                }

                updateOffer?.let { info ->
                    ApplicationUpdateOfferSheet(
                        info = info,
                        downloadProgress = updateProgress,
                        onLater = { postponeUpdate(info) },
                        onDownload = { downloadUpdate(info) }
                    )
                }

                sharePayload?.let { (title, payload) ->
                    DesktopConfigShareOverlay(
                        title = title,
                        payload = payload,
                        strings = strings,
                        onCopy = {
                            dependencies.configImporter.copyToClipboard(payload)
                            desktopNotice = strings.copied
                        },
                        onDismiss = {
                            sharePayload = null
                        }
                    )
                }

                desktopNotice?.let { notice ->
                    DesktopNotice(
                        text = notice,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 24.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun DesktopConfigShareOverlay(
    title: String,
    payload: String,
    strings: AppStrings,
    onCopy: () -> Unit,
    onDismiss: () -> Unit
) {
    var copied by remember(payload) { mutableStateOf(false) }
    val qrMatrix = remember(payload) {
        runCatching {
            MultiFormatWriter().encode(payload, BarcodeFormat.QR_CODE, 128, 128)
        }.getOrNull()
    }

    Popup(
        alignment = Alignment.Center,
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.28f))
                .clickable(onClick = onDismiss),
            contentAlignment = Alignment.Center
        ) {
            val noOpInteraction = remember { MutableInteractionSource() }

            Surface(
                modifier = Modifier
                    .padding(24.dp)
                    .widthIn(max = 440.dp)
                    .clickable(
                        interactionSource = noOpInteraction,
                        indication = null,
                        onClick = {}
                    ),
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp,
                shadowElevation = 12.dp,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = title,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = if (copied) strings.copiedToClipboard else strings.scanQrOrCopyLink,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 13.sp
                        )
                    }

                    if (qrMatrix != null) {
                        Surface(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .size(240.dp),
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        ) {
                            DesktopQrCode(
                                matrix = qrMatrix,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                    ) {
                        SelectionContainer {
                            Text(
                                text = payload,
                                modifier = Modifier.padding(14.dp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 12.sp,
                                maxLines = 5,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text(strings.close)
                        }
                        Spacer(Modifier.width(8.dp))
                        Button(
                            onClick = {
                                onCopy()
                                copied = true
                            }
                        ) {
                            Text(strings.copy)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DesktopQrCode(
    matrix: BitMatrix,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        drawRect(Color.White)
        val cellSize = min(size.width / matrix.width, size.height / matrix.height)
        val qrWidth = cellSize * matrix.width
        val qrHeight = cellSize * matrix.height
        val left = (size.width - qrWidth) / 2f
        val top = (size.height - qrHeight) / 2f

        for (x in 0 until matrix.width) {
            for (y in 0 until matrix.height) {
                if (matrix[x, y]) {
                    drawRect(
                        color = Color.Black,
                        topLeft = Offset(left + x * cellSize, top + y * cellSize),
                        size = Size(cellSize, cellSize)
                    )
                }
            }
        }
    }
}

@Composable
private fun DesktopNotice(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.inverseSurface,
        tonalElevation = 6.dp,
        shadowElevation = 8.dp
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
            color = MaterialTheme.colorScheme.inverseOnSurface,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

private fun DesktopSocksProxySettings.toApplicationSocksProxySettings(): ApplicationSocksProxySettings {
    return ApplicationSocksProxySettings(
        host = host,
        port = port,
        username = username,
        password = password
    )
}

private fun generateDesktopProxyPassword(length: Int = 24): String {
    val random = SecureRandom()
    return buildString(length) {
        repeat(length) {
            append(DESKTOP_PROXY_PASSWORD_ALPHABET[random.nextInt(DESKTOP_PROXY_PASSWORD_ALPHABET.length)])
        }
    }
}

private const val DESKTOP_PROXY_PASSWORD_ALPHABET =
    "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz23456789"

private fun desktopSubscriptionItems(items: List<LocationItem>): List<SubscriptionShareItem> {
    return items
        .mapNotNull { item ->
            val url = item.subscriptionUrl
                ?.trim()
                ?.takeIf { it.startsWith("https://") || it.startsWith("http://") }
                ?: return@mapNotNull null
            url to item
        }
        .groupBy({ it.first }, { it.second })
        .entries
        .sortedBy { it.key }
        .map { (url, subscriptionItems) ->
            val metadata = subscriptionItems.firstNotNullOfOrNull { it.metadata?.subscription }
            SubscriptionShareItem(
                url = url,
                name = metadata?.name?.takeIf { it.isNotBlank() }
                    ?: subscriptionItems.first().fullName,
                updateIntervalMs = metadata?.effectiveUpdateIntervalMs(),
                sourceUpdateIntervalMs = metadata?.updateIntervalMs,
                manualUpdateIntervalMs = metadata?.manualUpdateIntervalMs,
                updateIntervalHours = metadata?.updateIntervalHours,
                lastRefreshAtEpochMs = metadata?.lastRefreshAtEpochMs,
                nextRefreshAtEpochMs = metadata?.nextRefreshAtEpochMs(),
                locationCount = subscriptionItems.size
            )
        }
}

private fun chooseConfigFile(owner: Frame, dialogTitle: String = "Import Olcbox Config"): File? {
    val dialog = FileDialog(owner, dialogTitle, FileDialog.LOAD)
    dialog.isVisible = true

    return dialog.files.firstOrNull()
}

private fun chooseSaveFile(owner: Frame, defaultName: String, dialogTitle: String = "Save Olcbox Logs"): File? {
    val dialog = FileDialog(owner, dialogTitle, FileDialog.SAVE)
    dialog.file = defaultName
    dialog.isVisible = true

    val fileName = dialog.file ?: return null
    val directory = dialog.directory ?: return File(fileName)

    return File(directory, fileName)
}
