package org.olcbox.app.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.ContentPaste
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.olcbox.app.CurrentAppInfo
import org.olcbox.app.data.model.formatSubscriptionRefreshInterval
import org.olcbox.app.data.model.parseSubscriptionRefreshIntervalMs
import org.olcbox.app.data.share.SubscriptionShareItem
import org.olcbox.app.ui.features.home.components.LogLines
import org.olcbox.app.ui.localization.AppLanguage
import org.olcbox.app.ui.localization.AppLocalization
import org.olcbox.app.ui.localization.AppStrings
import org.olcbox.app.ui.localization.LocalAppStrings
import org.olcbox.app.update.AppUpdateInfo
import org.olcbox.app.update.AppUpdateSettings
import kotlin.time.Clock
import kotlin.time.Instant

data class ApplicationSocksProxySettings(
    val host: String = "127.0.0.1",
    val port: Int = DEFAULT_PORT,
    val username: String = "",
    val password: String = ""
) {
    companion object {
        const val DEFAULT_PORT = 10808
        const val MIN_PORT = 1024
        const val MAX_PORT = 65535
        const val MAX_CREDENTIAL_LENGTH = 64

        fun isValidPort(port: Int): Boolean = port in MIN_PORT..MAX_PORT
    }
}

data class ApplicationRoutingModeOption(
    val id: String,
    val title: String,
    val subtitle: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationSettingsSheet(
    updateSettings: AppUpdateSettings,
    updateStatusText: String?,
    updateDownloadProgress: Float?,
    updateOffer: AppUpdateInfo?,
    subscriptions: List<SubscriptionShareItem>,
    logs: List<String>,
    connectionSummary: String,
    connectionDetails: List<Pair<String, String>>,
    socksProxySettings: ApplicationSocksProxySettings? = null,
    routingModeOptions: List<ApplicationRoutingModeOption> = listOf(
        ApplicationRoutingModeOption("proxy", "Proxy", "Local SOCKS endpoint")
    ),
    selectedRoutingModeId: String = routingModeOptions.firstOrNull()?.id.orEmpty(),
    isConnectionActive: Boolean = false,
    onDismiss: () -> Unit,
    onCopyConfigClick: () -> Unit,
    onSaveLogsClick: () -> Unit,
    onShareLogsClick: () -> Unit,
    onUpdateIntervalSelected: (Int) -> Unit,
    onCheckUpdatesClick: () -> Unit,
    onDownloadUpdateClick: (AppUpdateInfo) -> Unit,
    onLaterUpdateClick: (AppUpdateInfo) -> Unit,
    onSubscriptionShareClick: (String) -> Unit,
    onSubscriptionRefreshClick: (String, () -> Unit) -> Unit,
    onSubscriptionRefreshIntervalChanged: (String, Long?) -> Unit = { _, _ -> },
    onSubscriptionDeleteClick: (String) -> Unit = {},
    onSocksProxySettingsSaved: (String, String, Int) -> Unit = { _, _, _ -> },
    onSocksProxyPasswordRegenerated: () -> Unit = {},
    onRoutingModeSelected: (String) -> Unit = {}
) {
    val strings = LocalAppStrings.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var route by remember { mutableStateOf(SharedSettingsRoute.Hub) }
    var selectedSubscriptionUrl by remember { mutableStateOf<String?>(null) }
    var refreshingSubscriptionUrl by remember { mutableStateOf<String?>(null) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        AnimatedContent(
            targetState = route,
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = 180,
                        delayMillis = 60,
                        easing = LinearOutSlowInEasing
                    )
                ).togetherWith(
                    fadeOut(
                        animationSpec = tween(
                            durationMillis = 90,
                            easing = FastOutLinearInEasing
                        )
                    )
                ).using(
                    SizeTransform(
                        clip = false,
                        sizeAnimationSpec = { _, _ ->
                            tween(
                                durationMillis = 320,
                                easing = FastOutSlowInEasing
                            )
                        }
                    )
                )
            },
            label = "sharedApplicationSettingsRoute"
        ) { currentRoute ->
            when (currentRoute) {
                SharedSettingsRoute.Hub -> SharedSettingsHubContent(
                    updateSettings = updateSettings,
                    subscriptionsCount = subscriptions.size,
                    strings = strings,
                    onConnectionClick = { route = SharedSettingsRoute.Connection },
                    onSubscriptionsClick = { route = SharedSettingsRoute.Subscriptions },
                    onUpdatesClick = { route = SharedSettingsRoute.Updates },
                    onLogsClick = { route = SharedSettingsRoute.Logs },
                    onLanguageClick = { route = SharedSettingsRoute.Language }
                )

                SharedSettingsRoute.Language -> SharedLanguageSettingsContent(
                    strings = strings,
                    onBack = { route = SharedSettingsRoute.Hub }
                )

                SharedSettingsRoute.Connection -> SharedConnectionSettingsContent(
                    summary = connectionSummary,
                    details = connectionDetails,
                    socksProxySettings = socksProxySettings,
                    strings = strings,
                    routingModeTitle = routingModeOptions
                        .firstOrNull { it.id == selectedRoutingModeId }
                        ?.title
                        ?: "Proxy",
                    onConnectionModeClick = { route = SharedSettingsRoute.ConnectionMode },
                    onSocksProxyClick = { route = SharedSettingsRoute.SocksProxy },
                    onBack = { route = SharedSettingsRoute.Hub }
                )

                SharedSettingsRoute.ConnectionMode -> SharedConnectionModeSettingsContent(
                    options = routingModeOptions,
                    selectedId = selectedRoutingModeId,
                    strings = strings,
                    onSelected = onRoutingModeSelected,
                    onBack = { route = SharedSettingsRoute.Connection }
                )

                SharedSettingsRoute.SocksProxy -> if (socksProxySettings != null) {
                    SharedSocksProxySettingsContent(
                        settings = socksProxySettings,
                        isConnectionActive = isConnectionActive,
                        strings = strings,
                        onBack = { route = SharedSettingsRoute.Connection },
                        onProxySettingsSaved = onSocksProxySettingsSaved,
                        onProxyPasswordRegenerated = onSocksProxyPasswordRegenerated
                    )
                }

                SharedSettingsRoute.Subscriptions -> SharedSubscriptionsSettingsContent(
                    subscriptions = subscriptions,
                    strings = strings,
                    onBack = { route = SharedSettingsRoute.Hub },
                    onCopyConfigClick = onCopyConfigClick,
                    onSubscriptionClick = { item ->
                        selectedSubscriptionUrl = item.url
                        route = SharedSettingsRoute.SubscriptionDetails
                    }
                )

                SharedSettingsRoute.SubscriptionDetails -> {
                    val item = subscriptions.firstOrNull { it.url == selectedSubscriptionUrl }
                    if (item == null) {
                        SharedSubscriptionsSettingsContent(
                            subscriptions = subscriptions,
                            strings = strings,
                            onBack = { route = SharedSettingsRoute.Hub },
                            onCopyConfigClick = onCopyConfigClick,
                            onSubscriptionClick = { selected ->
                                selectedSubscriptionUrl = selected.url
                                route = SharedSettingsRoute.SubscriptionDetails
                            }
                        )
                    } else {
                        SharedSubscriptionDetailsContent(
                            item = item,
                            isRefreshing = refreshingSubscriptionUrl == item.url,
                            strings = strings,
                            onBack = { route = SharedSettingsRoute.Subscriptions },
                            onShareClick = { onSubscriptionShareClick(item.url) },
                            onRefreshClick = {
                                refreshingSubscriptionUrl = item.url
                                onSubscriptionRefreshClick(item.url) {
                                    refreshingSubscriptionUrl = null
                                }
                            },
                            onRefreshIntervalChanged = { intervalMs ->
                                onSubscriptionRefreshIntervalChanged(item.url, intervalMs)
                            },
                            onDeleteClick = {
                                onSubscriptionDeleteClick(item.url)
                                selectedSubscriptionUrl = null
                                route = SharedSettingsRoute.Subscriptions
                            }
                        )
                    }
                }

                SharedSettingsRoute.Updates -> SharedUpdatesSettingsContent(
                    settings = updateSettings,
                    statusText = updateStatusText,
                    downloadProgress = updateDownloadProgress,
                    strings = strings,
                    onBack = { route = SharedSettingsRoute.Hub },
                    onIntervalSelected = onUpdateIntervalSelected,
                    onCheckUpdatesClick = onCheckUpdatesClick
                )

                SharedSettingsRoute.Logs -> SharedLogsSettingsContent(
                    logs = logs,
                    strings = strings,
                    onBack = { route = SharedSettingsRoute.Hub },
                    onSaveClick = onSaveLogsClick,
                    onShareClick = onShareLogsClick
                )
            }
        }
    }
}

@Composable
private fun SharedSettingsHubContent(
    updateSettings: AppUpdateSettings,
    subscriptionsCount: Int,
    strings: AppStrings,
    onConnectionClick: () -> Unit,
    onSubscriptionsClick: () -> Unit,
    onUpdatesClick: () -> Unit,
    onLogsClick: () -> Unit,
    onLanguageClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SharedSettingsHeader(
            icon = Icons.Outlined.Settings,
            title = strings.applicationSettingsTitle,
            subtitle = "SOCKS"
        )

        Spacer(Modifier.height(8.dp))

        SharedNavigationRow(
            title = strings.connectionSettings,
            value = strings.connectionSettingsSubtitle,
            icon = Icons.Rounded.Public,
            onClick = onConnectionClick
        )

        SharedNavigationRow(
            title = strings.subscriptionsAndSharing,
            value = strings.subscriptionsCount(subscriptionsCount),
            icon = Icons.Outlined.Share,
            onClick = onSubscriptionsClick
        )

        SharedNavigationRow(
            title = strings.updateSettings,
            value = strings.updateSettingsSubtitle(updateSettings.intervalHours),
            icon = Icons.Outlined.Refresh,
            onClick = onUpdatesClick
        )

        SharedNavigationRow(
            title = strings.languageSetting,
            value = AppLocalization.currentLanguage.displayName,
            icon = Icons.Outlined.Language,
            onClick = onLanguageClick
        )

        SharedNavigationRow(
            title = strings.applicationLogs,
            value = strings.applicationLogsSubtitle,
            icon = Icons.Outlined.History,
            onClick = onLogsClick
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${CurrentAppInfo.value.name} ${CurrentAppInfo.value.version} · " +
                    "olcrtc ${CurrentAppInfo.value.olcrtcSha.take(12)}",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun SharedLanguageSettingsContent(
    strings: AppStrings,
    onBack: () -> Unit
) {
    val current = AppLocalization.currentLanguage

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp)
    ) {
        SharedDetailHeader(
            title = strings.languageSetting,
            subtitle = current.displayName,
            onBack = onBack,
            backContentDescription = strings.back
        )

        Spacer(Modifier.height(20.dp))

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            AppLanguage.entries.forEach { lang ->
                SharedSelectableSettingsCard(
                    selected = lang == current,
                    icon = Icons.Outlined.Language,
                    title = lang.displayName,
                    subtitle = when (lang) {
                        AppLanguage.System -> strings.auto
                        AppLanguage.Russian -> "Русский язык"
                        AppLanguage.English -> "English language"
                    },
                    onClick = {
                        AppLocalization.userSelectedLanguage = lang
                    }
                )
            }
        }
    }
}

@Composable
private fun SharedConnectionSettingsContent(
    summary: String,
    details: List<Pair<String, String>>,
    socksProxySettings: ApplicationSocksProxySettings?,
    strings: AppStrings,
    routingModeTitle: String,
    onConnectionModeClick: () -> Unit,
    onSocksProxyClick: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 16.dp, bottom = 32.dp)
    ) {
        SharedDetailHeader(
            title = strings.connectionSettings,
            subtitle = summary,
            onBack = onBack,
            backContentDescription = strings.back
        )

        Spacer(Modifier.height(20.dp))

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            SharedNavigationRow(
                title = strings.connectionMode,
                value = routingModeTitle,
                icon = Icons.Rounded.Public,
                onClick = onConnectionModeClick
            )

            if (socksProxySettings != null) {
                SharedNavigationRow(
                    title = strings.socks5Proxy,
                    value = "${socksProxySettings.host}:${socksProxySettings.port}",
                    icon = Icons.Rounded.Public,
                    onClick = onSocksProxyClick
                )
            }

            details
                .filterNot { (title, _) -> title.equals("Mode", ignoreCase = true) }
                .forEach { (title, value) ->
                    SharedInfoRow(title = title, value = value)
                }
        }
    }
}

@Composable
private fun SharedConnectionModeSettingsContent(
    options: List<ApplicationRoutingModeOption>,
    selectedId: String,
    strings: AppStrings,
    onSelected: (String) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp)
    ) {
        SharedDetailHeader(
            title = strings.connectionMode,
            subtitle = options.firstOrNull { it.id == selectedId }?.title
                ?: strings.socks5Proxy,
            onBack = onBack,
            backContentDescription = strings.back
        )

        Spacer(Modifier.height(20.dp))

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            options.forEach { option ->
                SharedSelectableSettingsCard(
                    selected = option.id == selectedId,
                    icon = Icons.Rounded.Public,
                    title = option.title,
                    subtitle = option.subtitle,
                    onClick = { onSelected(option.id) }
                )
            }
        }
    }
}

@Composable
private fun SharedSocksProxySettingsContent(
    settings: ApplicationSocksProxySettings,
    isConnectionActive: Boolean,
    strings: AppStrings,
    onBack: () -> Unit,
    onProxySettingsSaved: (String, String, Int) -> Unit,
    onProxyPasswordRegenerated: () -> Unit
) {
    var editedHost by remember(settings.host) { mutableStateOf(settings.host) }
    var editedPort by remember(settings.port) { mutableStateOf(settings.port.toString()) }
    var editedUsername by remember(settings.username) { mutableStateOf(settings.username) }
    var editedPassword by remember(settings.password) { mutableStateOf(settings.password) }
    val parsedPort = editedPort.toIntOrNull()
    val hostValid = editedHost.isNotBlank()
    val portValid = parsedPort != null && ApplicationSocksProxySettings.isValidPort(parsedPort)
    val portChanged = parsedPort != null && parsedPort != settings.port
    val usernameChanged = editedUsername != settings.username
    val passwordChanged = editedPassword != settings.password
    val settingsChanged = portChanged || usernameChanged || passwordChanged
    val canSave = hostValid &&
            portValid &&
            editedUsername.isNotBlank() &&
            editedPassword.isNotBlank() &&
            settingsChanged

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp)
    ) {
        SharedDetailHeader(
            title = strings.socks5Proxy,
            subtitle = settings.host,
            onBack = onBack,
            backContentDescription = strings.back
        )

        Spacer(Modifier.height(20.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                SharedSectionLabel(strings.endpoint)

                SharedSocksProxyTextField(
                    value = editedHost,
                    onValueChange = { value ->
                        editedHost = value
                            .replace("\r", "")
                            .replace("\n", "")
                            .trim()
                    },
                    label = strings.listenAddress,
                    placeholder = "127.0.0.1",
                    enabled = false,
                    isError = !hostValid,
                    leadingIcon = Icons.Rounded.Public,
                    supportingText = if (!hostValid) strings.listenAddressRequired else null,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                SharedSocksProxyTextField(
                    value = editedPort,
                    onValueChange = { value ->
                        editedPort = value.filter { it.isDigit() }.take(5)
                    },
                    label = strings.port,
                    placeholder = ApplicationSocksProxySettings.DEFAULT_PORT.toString(),
                    enabled = true,
                    isError = editedPort.isBlank() || !portValid,
                    leadingIcon = Icons.Rounded.Public,
                    supportingText = when {
                        editedPort.isBlank() -> strings.portRequired
                        !portValid -> strings.portRange(ApplicationSocksProxySettings.MIN_PORT, ApplicationSocksProxySettings.MAX_PORT)
                        portChanged && isConnectionActive -> strings.savingRestartsActiveConnection
                        portChanged -> strings.unsavedChange
                        else -> null
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                SharedSectionLabel(strings.credentials)

                SharedSocksProxyTextField(
                    value = editedUsername,
                    onValueChange = { editedUsername = it.take(ApplicationSocksProxySettings.MAX_CREDENTIAL_LENGTH) },
                    label = strings.username,
                    placeholder = "olcbox...",
                    enabled = true,
                    isError = editedUsername.isBlank(),
                    leadingIcon = Icons.Rounded.Person,
                    supportingText = when {
                        editedUsername.isBlank() -> strings.usernameRequired
                        usernameChanged && isConnectionActive -> strings.savingRestartsActiveConnection
                        usernameChanged -> strings.unsavedChange
                        else -> null
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
                SharedSocksProxyTextField(
                    value = editedPassword,
                    onValueChange = { editedPassword = it.take(ApplicationSocksProxySettings.MAX_CREDENTIAL_LENGTH) },
                    label = strings.password,
                    placeholder = strings.generatedPassword,
                    enabled = true,
                    isError = editedPassword.isBlank(),
                    leadingIcon = Icons.Rounded.Key,
                    supportingText = when {
                        editedPassword.isBlank() -> strings.passwordRequired
                        passwordChanged && isConnectionActive -> strings.savingRestartsActiveConnection
                        passwordChanged -> strings.unsavedChange
                        else -> null
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onProxyPasswordRegenerated) {
                    Text(strings.regeneratePassword)
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    enabled = canSave,
                    onClick = {
                        onProxySettingsSaved(
                            editedUsername,
                            editedPassword,
                            parsedPort ?: settings.port
                        )
                    }
                ) {
                    Icon(Icons.Rounded.Check, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(strings.save)
                }
            }
        }
    }
}

@Composable
private fun SharedSocksProxyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    enabled: Boolean,
    isError: Boolean,
    leadingIcon: ImageVector,
    supportingText: String?,
    keyboardOptions: KeyboardOptions
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        singleLine = true,
        isError = isError,
        leadingIcon = { Icon(leadingIcon, contentDescription = null) },
        supportingText = supportingText?.let { { Text(it) } },
        keyboardOptions = keyboardOptions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SharedUpdatesSettingsContent(
    settings: AppUpdateSettings,
    statusText: String?,
    downloadProgress: Float?,
    strings: AppStrings,
    onBack: () -> Unit,
    onIntervalSelected: (Int) -> Unit,
    onCheckUpdatesClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 520.dp)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
            .padding(top = 16.dp, bottom = 12.dp)
    ) {
        SharedDetailHeader(
            title = strings.updatesTitle,
            subtitle = strings.currentVersion(CurrentAppInfo.value.version),
            onBack = onBack,
            backContentDescription = strings.back
        )

        Spacer(Modifier.height(18.dp))

        SharedSectionLabel(strings.checkInterval)
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AppUpdateSettings.INTERVAL_PRESETS.forEach { hours ->
                FilterChip(
                    selected = settings.intervalHours == hours,
                    onClick = { onIntervalSelected(hours) },
                    label = { Text("${hours}h") }
                )
            }
        }

        Spacer(Modifier.height(18.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            color = MaterialTheme.colorScheme.surfaceContainer,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = strings.lastCheck,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = settings.lastCheckAtEpochMs?.let { strings.relativeTime(it, Clock.System.now().toEpochMilliseconds()) } ?: strings.notCheckedYet,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (!statusText.isNullOrBlank()) {
                    Text(
                        text = statusText,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                if (downloadProgress != null) {
                    LinearProgressIndicator(
                        progress = { downloadProgress.coerceIn(0f, 1f) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Spacer(Modifier.height(18.dp))

        Button(
            onClick = onCheckUpdatesClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(strings.checkNow)
        }
    }
}

@Composable
private fun SharedSubscriptionsSettingsContent(
    subscriptions: List<SubscriptionShareItem>,
    strings: AppStrings,
    onBack: () -> Unit,
    onCopyConfigClick: () -> Unit,
    onSubscriptionClick: (SubscriptionShareItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 620.dp)
            .padding(horizontal = 24.dp)
            .padding(top = 16.dp, bottom = 12.dp)
    ) {
        SharedDetailHeader(
            title = strings.subscriptions,
            subtitle = strings.subscriptionsCount(subscriptions.size),
            onBack = onBack,
            backContentDescription = strings.back
        )

        Spacer(Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 500.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (subscriptions.isEmpty()) {
                SharedEmptyState(
                    title = strings.noSubscriptions,
                    subtitle = strings.noSubscriptionsSubtitle
                )
            } else {
                subscriptions.forEach { item ->
                    SharedSubscriptionRow(
                        item = item,
                        strings = strings,
                        onClick = { onSubscriptionClick(item) }
                    )
                }
            }

            Spacer(Modifier.height(6.dp))
            SharedSectionLabel(strings.backupAndExport)
            SharedNavigationRow(
                title = strings.exportFullConfig,
                value = strings.exportFullConfigSubtitle,
                icon = Icons.Outlined.ContentPaste,
                showChevron = false,
                onClick = onCopyConfigClick
            )
        }
    }
}

@Composable
private fun SharedSubscriptionDetailsContent(
    item: SubscriptionShareItem,
    isRefreshing: Boolean,
    strings: AppStrings,
    onBack: () -> Unit,
    onShareClick: () -> Unit,
    onRefreshClick: () -> Unit,
    onRefreshIntervalChanged: (Long?) -> Unit,
    onDeleteClick: () -> Unit
) {
    var showRefreshDialog by remember(item.url) { mutableStateOf(false) }
    var refreshIntervalInput by remember(item.url) {
        mutableStateOf(
            item.manualUpdateIntervalMs
                ?.let(::formatSubscriptionRefreshInterval)
                .orEmpty()
        )
    }
    var showDeleteDialog by remember(item.url) { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 680.dp)
            .padding(horizontal = 24.dp)
            .padding(top = 16.dp, bottom = 12.dp)
    ) {
        SharedDetailHeader(
            title = strings.subscriptionDetailsTitle,
            subtitle = item.name,
            onBack = onBack,
            backContentDescription = strings.back
        )

        Spacer(Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 560.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SharedSectionLabel(strings.overview)
            SharedSubscriptionStatusCard(item, strings)

            SharedSectionLabel(strings.sourceLabel)
            SharedSubscriptionSourceCard(
                url = item.url,
                strings = strings,
                onShareClick = onShareClick
            )

            SharedSectionLabel(strings.updatesTitle)
            SharedSubscriptionUpdateCard(
                item = item,
                isRefreshing = isRefreshing,
                strings = strings,
                onScheduleClick = {
                    refreshIntervalInput = item.manualUpdateIntervalMs
                        ?.let(::formatSubscriptionRefreshInterval)
                        .orEmpty()
                    showRefreshDialog = true
                },
                onRefreshClick = onRefreshClick
            )

            Spacer(Modifier.height(6.dp))
            SharedDangerAction(
                locationCount = item.locationCount,
                strings = strings,
                onClick = { showDeleteDialog = true }
            )
            Spacer(Modifier.height(18.dp))
        }
    }

    if (showRefreshDialog) {
        val parsedInterval = refreshIntervalInput
            .takeIf { it.isNotBlank() }
            ?.let(::parseSubscriptionRefreshIntervalMs)
        val hasError = refreshIntervalInput.isNotBlank() && parsedInterval == null

        AlertDialog(
            onDismissRequest = { showRefreshDialog = false },
            title = { Text(strings.refreshSchedule) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = strings.refreshScheduleSubtitle,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf(
                            strings.auto to "",
                            "1h" to "1h",
                            "6h" to "6h",
                            "1d" to "1d"
                        ).forEach { (label, value) ->
                            FilterChip(
                                selected = refreshIntervalInput == value,
                                onClick = { refreshIntervalInput = value },
                                label = { Text(label) }
                            )
                        }
                    }
                    OutlinedTextField(
                        value = refreshIntervalInput,
                        onValueChange = { value ->
                            refreshIntervalInput = value
                                .lowercase()
                                .filter { it.isDigit() || it in "smhd" }
                                .take(8)
                        },
                        label = { Text(strings.customInterval) },
                        placeholder = { Text(strings.auto) },
                        supportingText = {
                            Text(
                                if (hasError) {
                                    strings.subscriptionRefreshRateError
                                } else if (refreshIntervalInput.isBlank()) {
                                    item.sourceScheduleDescription(strings)
                                } else {
                                    strings.customIntervalHint
                                }
                            )
                        },
                        isError = hasError,
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    enabled = !hasError,
                    onClick = {
                        onRefreshIntervalChanged(parsedInterval)
                        showRefreshDialog = false
                    }
                ) {
                    Text(strings.save)
                }
            },
            dismissButton = {
                TextButton(onClick = { showRefreshDialog = false }) {
                    Text(strings.cancel)
                }
            }
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(strings.deleteSubscriptionTitle) },
            text = {
                Text(strings.deleteSubscriptionMessage(item.name, strings.locationsCount(item.locationCount)))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDeleteClick()
                    }
                ) {
                    Text(strings.delete, color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text(strings.cancel)
                }
            }
        )
    }
}

@Composable
private fun SharedLogsSettingsContent(
    logs: List<String>,
    strings: AppStrings,
    onBack: () -> Unit,
    onSaveClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
            .padding(horizontal = 24.dp)
            .padding(top = 16.dp, bottom = 24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SharedDetailHeader(
                title = strings.applicationLogs,
                subtitle = strings.logsEntriesCount(logs.size),
                onBack = onBack,
                backContentDescription = strings.back,
                modifier = Modifier.weight(1f)
            )

            TextButton(
                enabled = logs.isNotEmpty(),
                onClick = onSaveClick
            ) {
                Text(strings.save)
            }
            TextButton(
                enabled = logs.isNotEmpty(),
                onClick = onShareClick
            ) {
                Text(strings.share)
            }
        }

        Spacer(Modifier.height(16.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surfaceContainer,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            LogLines(
                logs = logs,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(14.dp)
            )
        }
    }
}

@Composable
private fun SharedSubscriptionRow(
    item: SubscriptionShareItem,
    strings: AppStrings,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = item.name,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = strings.locationsCount(item.locationCount),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp,
                    maxLines = 1
                )
                Text(
                    text = item.listScheduleDescription(strings),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SharedSubscriptionStatusCard(
    item: SubscriptionShareItem,
    strings: AppStrings
) {
    val now = Clock.System.now().toEpochMilliseconds()
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SharedStatusMetric(
                label = strings.locationsLabel,
                value = item.locationCount.toString(),
                modifier = Modifier.weight(1f)
            )
            SharedStatusDivider()
            SharedStatusMetric(
                label = strings.updatedLabel,
                value = item.lastRefreshAtEpochMs?.let { strings.relativeTime(it, now) } ?: strings.notYet,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 14.dp)
            )
            SharedStatusDivider()
            SharedStatusMetric(
                label = strings.nextLabel,
                value = item.nextRefreshAtEpochMs?.let { strings.relativeTime(it, now) } ?: strings.onAppStart,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 14.dp)
            )
        }
    }
}

@Composable
private fun SharedStatusMetric(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 11.sp,
            maxLines = 1
        )
    }
}

@Composable
private fun SharedStatusDivider() {
    Surface(
        modifier = Modifier
            .width(1.dp)
            .height(34.dp),
        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.65f)
    ) {}
}

@Composable
private fun SharedSubscriptionUpdateCard(
    item: SubscriptionShareItem,
    isRefreshing: Boolean,
    strings: AppStrings,
    onScheduleClick: () -> Unit,
    onRefreshClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .clickable(onClick = onScheduleClick)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Refresh,
                        contentDescription = null,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Spacer(Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.scheduleDescription(strings),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1
                    )
                    Text(
                        text = item.scheduleTitle(strings),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }
                Icon(
                    imageVector = Icons.Rounded.ChevronRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp),
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.55f)
            ) {}

            TextButton(
                onClick = onRefreshClick,
                enabled = !isRefreshing,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                if (isRefreshing) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(17.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(if (isRefreshing) strings.refreshingEllipsis else strings.refreshNow)
            }
        }
    }
}

@Composable
private fun SharedSubscriptionSourceCard(
    url: String,
    strings: AppStrings,
    onShareClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier.padding(start = 14.dp, top = 10.dp, bottom = 10.dp, end = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(38.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ) {
                Icon(
                    imageVector = Icons.Rounded.Public,
                    contentDescription = null,
                    modifier = Modifier.padding(9.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = url.subscriptionHost(),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = strings.subscriptionLink,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.sp,
                    maxLines = 1
                )
            }
            IconButton(onClick = onShareClick) {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = strings.shareSubscriptionContentDescription
                )
            }
        }
    }
}

@Composable
private fun SharedDangerAction(
    locationCount: Int,
    strings: AppStrings,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.55f),
                contentColor = MaterialTheme.colorScheme.error
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = null,
                    modifier = Modifier.padding(10.dp)
                )
            }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = strings.deleteSubscriptionAction,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = strings.deleteSubscriptionSubtitle(strings.locationsCount(locationCount)),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )
            }
            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
private fun SharedNavigationRow(
    title: String,
    value: String,
    icon: ImageVector,
    enabled: Boolean = true,
    showChevron: Boolean = true,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(18.dp))
            .clickable(enabled = enabled, onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.padding(10.dp)
                )
            }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = value,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (showChevron) {
                Icon(
                    imageVector = Icons.Rounded.ChevronRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
private fun SharedInfoRow(
    title: String,
    value: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = value,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun SharedSelectableSettingsCard(
    selected: Boolean,
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(82.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        color = if (selected) {
            MaterialTheme.colorScheme.secondaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceContainer
        },
        border = BorderStroke(
            1.dp,
            if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                },
                contentColor = if (selected) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.padding(10.dp)
                )
            }

            Spacer(Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (selected) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
private fun SharedSettingsHeader(
    icon: ImageVector,
    title: String,
    subtitle: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(46.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.padding(11.dp)
            )
        }
        Spacer(Modifier.width(14.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SharedDetailHeader(
    title: String,
    subtitle: String,
    onBack: () -> Unit,
    backContentDescription: String = "Back",
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(46.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surfaceContainerHighest,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = backContentDescription
                )
            }
        }

        Spacer(Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun SharedSectionLabel(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(start = 2.dp),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
private fun SharedEmptyState(
    title: String,
    subtitle: String
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = subtitle,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 13.sp
            )
        }
    }
}

private enum class SharedSettingsRoute {
    Hub,
    Connection,
    ConnectionMode,
    Subscriptions,
    SubscriptionDetails,
    Updates,
    Logs,
    SocksProxy,
    Language
}

private fun SubscriptionShareItem.scheduleTitle(strings: AppStrings): String {
    return if (manualUpdateIntervalMs == null) strings.scheduleAutomatic else strings.scheduleCustom
}

private fun SubscriptionShareItem.scheduleDescription(strings: AppStrings): String {
    val interval = updateIntervalMs
        ?: updateIntervalHours?.times(60L * 60L * 1_000L)
    return interval?.let { strings.friendlySchedule(it) } ?: strings.scheduleUsesSubscription
}

private fun SubscriptionShareItem.sourceScheduleDescription(strings: AppStrings): String {
    val interval = sourceUpdateIntervalMs
        ?: updateIntervalHours?.times(60L * 60L * 1_000L)
    return interval?.let { strings.autoScheduleWithPresetHint(strings.friendlySchedule(it).lowercase()) }
        ?: strings.autoScheduleDefaultHint
}

private fun SubscriptionShareItem.listScheduleDescription(strings: AppStrings): String {
    val schedule = if (manualUpdateIntervalMs == null) {
        strings.scheduleAutomatic
    } else {
        updateIntervalMs?.let { strings.friendlySchedule(it) } ?: strings.scheduleCustom
    }
    val now = Clock.System.now().toEpochMilliseconds()
    val refreshed = lastRefreshAtEpochMs?.let { strings.relativeTime(it, now) }?.let { "${strings.updatedLabel} $it" } ?: strings.notYet
    return "$schedule · $refreshed"
}

private fun String.subscriptionHost(): String {
    return substringAfter("://", this)
        .substringBefore('/')
        .substringBefore('?')
        .ifBlank { "Subscription source" }
}
