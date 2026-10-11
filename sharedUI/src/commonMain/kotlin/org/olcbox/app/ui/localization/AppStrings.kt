package org.olcbox.app.ui.localization

interface AppStrings {
    // Brand & Common Actions
    val appName: String
    val appSubtitle: String
    val save: String
    val cancel: String
    val close: String
    val copy: String
    val copied: String
    val copiedToClipboard: String
    val delete: String
    val share: String
    val back: String
    val clear: String
    val auto: String
    val offline: String
    val checking: String

    // Home Screen & Hero Controls
    val start: String
    val stop: String
    val setup: String
    val startIconDescription: String
    val relayActive: String
    val relayInactive: String
    val activeDescription: String
    val inactiveDescription: String
    val connectivityCheck: String
    val statusConnected: String
    val statusConnecting: String
    val statusDisconnected: String
    val statusNoLocationSelected: String

    // App Bar & Main Screen
    val appSettingsContentDescription: String
    val historyContentDescription: String
    val splitTunnelingContentDescription: String
    val addConfigurationContentDescription: String
    val settings: String
    val selectedLocationDescription: String

    // Add Configuration Sheet
    val addConnection: String
    val addConnectionSubtitle: String
    val scanQrCode: String
    val scanQrCodeSubtitle: String
    val enterLinkOrUri: String
    val enterLinkOrUriSubtitle: String
    val importFromFile: String
    val importFromFileSubtitle: String
    val updateSubscriptions: String
    val updateSubscriptionsSubtitle: String
    val createCustomLocation: String
    val createCustomLocationSubtitle: String

    // Locations & Relay Setup
    val subscriptions: String
    val customLocations: String
    val addCustomLocation: String
    val addSubscription: String
    val addRelaySetup: String
    val addRelaySetupSubtitle: String
    val pingButtonLabel: String
    val updateButtonLabel: String
    val remaining: String
    val used: String
    fun trafficQuota(used: String, available: String): String

    // Location Settings Screen & Fields
    val locationSettingsTitle: String
    val shareLocationContentDescription: String
    val nameLabel: String
    val locationNamePlaceholder: String
    val connectionType: String
    val connectionTypeService: String
    val connectionTypeJitsi: String
    val serviceProviderLabel: String
    val transportLabel: String
    val vp8Options: String
    val vp8OptionsSubtitle: String
    val fpsLabel: String
    val batchLabel: String
    val tracksLabel: String
    val roomIdLabel: String
    val roomUrlLabel: String
    val encryptionKeyLabel: String
    val encryptionKeyPlaceholder: String
    val dnsServerLabel: String
    val dnsServerPlaceholder: String
    val clickToVerifyReachability: String
    fun connectedLatency(latency: Long): String

    // Errors & Validation
    val errorNameEmpty: String
    val errorNameTooLong: String
    fun errorRoomEmpty(roomLabel: String): String
    fun errorRoomTooLong(roomLabel: String): String
    val errorKeyEmpty: String
    val errorKeyInvalid: String
    val errorDnsInvalid: String

    // Import & Subscriptions Actions
    val importLinkOrUriTitle: String
    val httpHttpsOlcrtcUriLabel: String
    val subscriptionRefreshRateLabel: String
    val subscriptionRefreshRateHint: String
    val subscriptionRefreshRateError: String
    val allowInsecureRequests: String
    val pasteClipboard: String
    val importAction: String
    val configImported: String
    fun subscriptionsUpdatedCount(count: Int): String
    val noSubscriptionsToUpdate: String
    val subscriptionUpdated: String
    val subscriptionAlreadyUpToDate: String
    fun couldNotUpdateSubscription(message: String): String
    val invalidImportLink: String
    val noClipboardData: String
    val couldNotReadConfigFile: String
    val logsSaved: String
    fun logsSavedTo(path: String): String
    val failedToSaveLogs: String
    val failedToShareLogs: String
    val subscriptionRefreshSetToAuto: String
    val subscriptionRefreshRateSaved: String
    fun subscriptionDeletedSummary(removedLocations: Int): String
    val socks5ProxySaved: String
    val passwordRegenerated: String
    val connectionModeSaved: String
    val qrImported: String

    // Application Settings Sheet
    val applicationSettingsTitle: String
    val dynamicThemeTitle: String
    val dynamicThemeAndroidColors: String
    val dynamicThemeOlcboxColors: String
    val showSpeedInNotification: String
    val showSpeedInNotificationSubtitle: String
    val connectionSettings: String
    val connectionSettingsSubtitle: String
    val connectionSettingsAndroidSubtitle: String
    val subscriptionsAndSharing: String
    val updateSettings: String
    fun updateSettingsSubtitle(hours: Int): String
    val applicationLogs: String
    val applicationLogsSubtitle: String
    val languageSetting: String
    val languageSettingSubtitle: String
    val systemLanguage: String
    val systemLanguageSubtitle: String
    val russianLanguage: String
    val russianLanguageSubtitle: String
    val englishLanguage: String
    val englishLanguageSubtitle: String
    val connectionMode: String
    val connectionModeSubtitle: String
    val modeTunSubtitle: String
    val modeProxySubtitle: String
    val modeTunSummary: String
    val modeProxySummary: String
    val modeTunDesc: String
    val modeProxyDesc: String
    val socks5Proxy: String
    val endpoint: String
    val listenAddress: String
    val port: String
    val credentials: String
    val username: String
    val password: String
    val generatedPassword: String
    val regeneratePassword: String
    val listenAddressRequired: String
    val portRequired: String
    fun portRange(min: Int, max: Int): String
    val usernameRequired: String
    val passwordRequired: String
    val savingRestartsActiveConnection: String
    val unsavedChange: String

    // Updates
    val updatesTitle: String
    val updateAvailable: String
    fun currentVersion(version: String): String
    val checkInterval: String
    val lastCheck: String
    val notCheckedYet: String
    val checkNow: String
    val later: String
    val download: String
    val install: String
    val sizeUnknown: String
    val updateOfferTitle: String
    val updateServiceUnavailable: String
    val appIsUpToDate: String
    fun checkingUpdates(channel: String): String
    val updateCheckFailed: String
    val allowInstallUpdatesHint: String
    fun downloadingUpdate(name: String): String
    fun installingUpdate(name: String): String

    // Subscriptions Details
    val noSubscriptions: String
    val noSubscriptionsSubtitle: String
    val backupAndExport: String
    val exportFullConfig: String
    val exportFullConfigSubtitle: String
    val subscriptionDetailsTitle: String
    val overview: String
    val locationsLabel: String
    val updatedLabel: String
    val nextLabel: String
    val notYet: String
    val onAppStart: String
    val sourceLabel: String
    val subscriptionLink: String
    val shareSubscriptionContentDescription: String
    val refreshSchedule: String
    val refreshScheduleSubtitle: String
    val customInterval: String
    val customIntervalHint: String
    val autoScheduleDefaultHint: String
    fun autoScheduleWithPresetHint(preset: String): String
    val deleteSubscriptionTitle: String
    fun deleteSubscriptionMessage(name: String, countLabel: String): String
    fun cannotBeUndone(name: String, locationsCount: String): String
    val deleteSubscriptionAction: String
    fun deleteSubscriptionSubtitle(countLabel: String): String
    val refreshingEllipsis: String
    val refreshNow: String
    val scheduleAutomatic: String
    val scheduleCustom: String
    val scheduleUsesSubscription: String
    val customSchedule: String
    val usesSubscriptionSchedule: String
    fun autoUsesSchedule(schedule: String): String
    val autoUsesDefaultSchedule: String
    val notUpdatedYet: String
    fun updatedRelative(time: String): String
    val subscriptionSource: String

    // Plurals & formatting
    fun subscriptionsCount(count: Int): String
    fun locationsCount(count: Int): String
    fun logsEntriesCount(count: Int): String
    fun friendlySchedule(ms: Long): String
    fun relativeTime(ms: Long, nowMs: Long): String
    fun formatBytes(bytes: Long): String
    fun formatSpeed(bytesPerSec: Long): String

    // Split Tunneling & Routing
    val splitTunneling: String
    val routingBehavior: String
    val splitTunnelingModeAll: String
    val splitTunnelingModeProxySelected: String
    val splitTunnelingModeBypassSelected: String
    val splitTunnelingApps: String
    val appsUsingOlcbox: String
    val bypassedApps: String
    val noSystemAppsFound: String
    fun appsIncluded(count: Int): String
    fun appsHiddenByDefault(count: Int): String
    val showSystemApps: String
    val noAppsFound: String
    val noMatchingApps: String
    val installAppsHint: String
    val tryAnotherAppName: String
    val noAppListNeeded: String
    val everyAppFollowsTun: String
    val appList: String
    val everyAppUsesOlcbox: String
    val chooseAppsUsingOlcbox: String
    val chooseAppsBypassingOlcbox: String
    val allAppsUseOlcbox: String
    val noAppsSelected: String
    val noAppsBypassOlcbox: String
    fun appsCount(count: Int): String
    fun appsCountUseOlcbox(count: Int): String
    fun appsCountBypassed(count: Int): String
    fun appsCountOnly(count: Int): String
    val autoDetectionMayBeInaccurate: String
    val savedForTunMode: String
    val appliesWhenSettingsCloses: String
    val tunModeRoutingRule: String
    val required: String
    val noBypassedApps: String
    val ruBypassNoMatchingApps: String
    fun ruBypassMatchedByPackage(count: Int): String
    val ruBypassNoAppsSelected: String
    fun ruBypassAlreadySelected(count: Int): String
    fun ruBypassAutoBypassed(count: Int): String
    fun ruBypassAutoAndManual(autoCount: Int, manualCount: Int): String
    val searchApps: String
    val bypassRuApps: String
    val ruBypassOn: String
    val routingModeTun: String
    val routingModeTunDesc: String
    val routingModeSystemProxy: String
    val routingModeSystemProxyDesc: String
    val routingModeLocalSocks: String
    val routingModeLocalSocksDesc: String
    val routingModeAutoDesc: String

    // Dialogs & Overlays
    val scanQrOrCopyLink: String
    val locationQrTitle: String
    val subscriptionQrTitle: String
    val connectAppsThroughOlcbox: String
    val iosSocksDescription: String
    val gotIt: String
    val copySettings: String

    // Notifications & Service
    val configCopied: String
    val allowOlcboxInstallUpdates: String
    fun downloadFailed(error: String): String
    fun couldNotOpenInstaller(error: String): String
    val waitingForNetwork: String
    val reconnecting: String
    val waitingForTransport: String
    val connectionFailed: String
    val tunnelFailed: String
    val vpnTunnelError: String
    val splitTunnelingError: String
    val addLocationFirst: String
    val cameraPermissionDenied: String
    val cameraUnavailable: String
    val readyToScan: String
    val closeScanner: String
    val subscriptionOrLocationUri: String
    val protectingYourConnection: String
    val startingProxy: String
}

object EnAppStrings : AppStrings {
    override val appName = "olcbox"
    override val appSubtitle = "multiplatform olcrtc configurator"
    override val save = "Save"
    override val cancel = "Cancel"
    override val close = "Close"
    override val copy = "Copy"
    override val copied = "Copied"
    override val copiedToClipboard = "Copied to clipboard"
    override val delete = "Delete"
    override val share = "Share"
    override val back = "Back"
    override val clear = "Clear"
    override val auto = "Auto"
    override val offline = "Offline"
    override val checking = "Checking..."

    override val start = "START"
    override val stop = "STOP"
    override val setup = "SETUP"
    override val startIconDescription = "Start Icon"
    override val relayActive = "Relay Active"
    override val relayInactive = "Relay Inactive"
    override val activeDescription = "Active"
    override val inactiveDescription = "Inactive"
    override val connectivityCheck = "Connectivity Check"
    override val statusConnected = "Connected"
    override val statusConnecting = "Connecting..."
    override val statusDisconnected = "Disconnected"
    override val statusNoLocationSelected = "No location selected"

    override val appSettingsContentDescription = "Application Settings"
    override val historyContentDescription = "Application Logs"
    override val splitTunnelingContentDescription = "Split Tunneling"
    override val addConfigurationContentDescription = "Add Configuration"
    override val settings = "Settings"
    override val selectedLocationDescription = "Selected location"

    override val addConnection = "Add Connection"
    override val addConnectionSubtitle = "Subscription or custom location"
    override val scanQrCode = "Scan QR Code"
    override val scanQrCodeSubtitle = "Subscription or olcrtc URI"
    override val enterLinkOrUri = "Enter Link or URI"
    override val enterLinkOrUriSubtitle = "Type, edit, or paste from clipboard"
    override val importFromFile = "Import from File"
    override val importFromFileSubtitle = "Subscription file or single config"
    override val updateSubscriptions = "Update Subscriptions"
    override val updateSubscriptionsSubtitle = "Refresh all subscribed locations"
    override val createCustomLocation = "Create Custom Location"
    override val createCustomLocationSubtitle = "Room, key, provider, and transport"

    override val subscriptions = "Subscriptions"
    override val customLocations = "Custom locations"
    override val addCustomLocation = "Add custom location"
    override val addSubscription = "Add subscription"
    override val addRelaySetup = "Relay Setup"
    override val addRelaySetupSubtitle = "QR code, link, or config file"
    override val pingButtonLabel = "Ping"
    override val updateButtonLabel = "Update"
    override val remaining = "remaining"
    override val used = "used"
    override fun trafficQuota(used: String, available: String) = "$used used · $available available"

    override val locationSettingsTitle = "Location Settings"
    override val shareLocationContentDescription = "Share Location"
    override val nameLabel = "Name"
    override val locationNamePlaceholder = "Location name"
    override val connectionType = "Connection Type"
    override val connectionTypeService = "Service"
    override val connectionTypeJitsi = "Jitsi"
    override val serviceProviderLabel = "Service Provider"
    override val transportLabel = "Transport"
    override val vp8Options = "VP8 Options"
    override val vp8OptionsSubtitle = "Video pipeline settings"
    override val fpsLabel = "FPS"
    override val batchLabel = "Batch"
    override val tracksLabel = "Tracks (MIMO)"
    override val roomIdLabel = "Room ID"
    override val roomUrlLabel = "Room URL"
    override val encryptionKeyLabel = "Encryption Key"
    override val encryptionKeyPlaceholder = "64-character hex key"
    override val dnsServerLabel = "DNS Server (Optional)"
    override val dnsServerPlaceholder = "Auto or 1.1.1.1:53"
    override val clickToVerifyReachability = "Click to verify reachability"
    override fun connectedLatency(latency: Long) = "Reachable in ${latency}ms"

    override val errorNameEmpty = "Name is required"
    override val errorNameTooLong = "Name is too long (max 30)"
    override fun errorRoomEmpty(roomLabel: String) = "$roomLabel is required"
    override fun errorRoomTooLong(roomLabel: String) = "$roomLabel is too long"
    override val errorKeyEmpty = "Encryption key is required"
    override val errorKeyInvalid = "Requires 64 hex characters"
    override val errorDnsInvalid = "Use host:port or leave blank for Auto"

    override val importLinkOrUriTitle = "Import Link or URI"
    override val httpHttpsOlcrtcUriLabel = "HTTP, HTTPS, or olcrtc URI"
    override val subscriptionRefreshRateLabel = "Subscription Refresh Rate"
    override val subscriptionRefreshRateHint = "Optional. Leave blank to use subscription default."
    override val subscriptionRefreshRateError = "Use 5m–30d, for example 10m, 6h, or 1d"
    override val allowInsecureRequests = "Allow insecure requests"
    override val pasteClipboard = "Paste clipboard"
    override val importAction = "Import"

    override val configImported = "Config imported"
    override fun subscriptionsUpdatedCount(count: Int) = "Updated $count subscriptions"
    override val noSubscriptionsToUpdate = "No subscriptions to update"
    override val subscriptionUpdated = "Subscription updated"
    override val subscriptionAlreadyUpToDate = "Subscription already up to date"
    override fun couldNotUpdateSubscription(message: String) = "Could not update subscription: $message"
    override val invalidImportLink = "Invalid link. Expected olcbox://add?url=<encoded URL>"
    override val noClipboardData = "No text found in clipboard"
    override val couldNotReadConfigFile = "Could not read configuration file"
    override val logsSaved = "Logs saved"
    override fun logsSavedTo(path: String) = "Logs saved to $path"
    override val failedToSaveLogs = "Failed to save logs"
    override val failedToShareLogs = "Failed to share logs"
    override val subscriptionRefreshSetToAuto = "Subscription refresh set to Auto"
    override val subscriptionRefreshRateSaved = "Subscription refresh rate saved"
    override fun subscriptionDeletedSummary(removedLocations: Int) = "Subscription deleted · $removedLocations locations removed"
    override val socks5ProxySaved = "SOCKS proxy saved"
    override val passwordRegenerated = "Password regenerated"
    override val connectionModeSaved = "Connection mode saved"
    override val qrImported = "QR imported"

    override val applicationSettingsTitle = "Application Settings"
    override val dynamicThemeTitle = "Dynamic Theme"
    override val dynamicThemeAndroidColors = "Using Android system colors"
    override val dynamicThemeOlcboxColors = "Using Olcbox colors"
    override val showSpeedInNotification = "Speed in notification"
    override val showSpeedInNotificationSubtitle = "Show current upload and download speed in status bar"
    override val connectionSettings = "Connection Settings"
    override val connectionSettingsSubtitle = "Mode and SOCKS5 proxy"
    override val connectionSettingsAndroidSubtitle = "Mode, SOCKS5 proxy, and app routing"
    override val subscriptionsAndSharing = "Subscriptions & Sharing"
    override val updateSettings = "Update Settings"
    override fun updateSettingsSubtitle(hours: Int) = "Nightly · every ${hours}h"
    override val applicationLogs = "Application Logs"
    override val applicationLogsSubtitle = "Diagnostics and export"
    override val languageSetting = "Language"
    override val languageSettingSubtitle = "Interface language"
    override val systemLanguage = "System default"
    override val systemLanguageSubtitle = "Follow system language"
    override val russianLanguage = "Русский"
    override val russianLanguageSubtitle = "Русский интерфейс"
    override val englishLanguage = "English"
    override val englishLanguageSubtitle = "English interface"
    override val connectionMode = "Connection Mode"
    override val connectionModeSubtitle = "TUN and proxy routing"
    override val modeTunSubtitle = "Full tunnel"
    override val modeProxySubtitle = "Local SOCKS5 proxy"
    override val modeTunSummary = "TUN · Full tunnel"
    override val modeProxySummary = "Proxy · Local SOCKS5"
    override val modeTunDesc = "System VPN interface"
    override val modeProxyDesc = "Local SOCKS endpoint"
    override val socks5Proxy = "SOCKS5 Proxy"
    override val endpoint = "Endpoint"
    override val listenAddress = "Listen address"
    override val port = "Port"
    override val credentials = "Credentials"
    override val username = "Username"
    override val password = "Password"
    override val generatedPassword = "Generated password"
    override val regeneratePassword = "Regenerate password"
    override val listenAddressRequired = "Listen address is required"
    override val portRequired = "Port is required"
    override fun portRange(min: Int, max: Int) = "Use $min-$max"
    override val usernameRequired = "Username is required"
    override val passwordRequired = "Password is required"
    override val savingRestartsActiveConnection = "Saving restarts active connection"
    override val unsavedChange = "Unsaved change"

    override val updatesTitle = "Updates"
    override val updateAvailable = "Update available"
    override fun currentVersion(version: String) = "Current version $version"
    override val checkInterval = "Check Interval"
    override val lastCheck = "Last check"
    override val notCheckedYet = "Not checked yet"
    override val checkNow = "Check now"
    override val later = "Later"
    override val download = "Download"
    override val install = "Install"
    override val sizeUnknown = "Size unknown"
    override val updateOfferTitle = "Update available"
    override val updateServiceUnavailable = "Update service unavailable"
    override val appIsUpToDate = "Application is up to date"
    override fun checkingUpdates(channel: String) = "Checking for updates..."
    override val updateCheckFailed = "Update check failed"
    override val allowInstallUpdatesHint = "Allow installing updates and try again"
    override fun downloadingUpdate(name: String) = "Downloading $name..."
    override fun installingUpdate(name: String) = "Installing $name"

    override val noSubscriptions = "No subscriptions"
    override val noSubscriptionsSubtitle = "Import a subscription from the home screen to manage it here."
    override val backupAndExport = "Backup & export"
    override val exportFullConfig = "Export full configuration"
    override val exportFullConfigSubtitle = "Copy all locations to clipboard"
    override val subscriptionDetailsTitle = "Subscription"
    override val overview = "Overview"
    override val locationsLabel = "Locations"
    override val updatedLabel = "Updated"
    override val nextLabel = "Next"
    override val notYet = "Not yet"
    override val onAppStart = "On app start"
    override val sourceLabel = "Source"
    override val subscriptionLink = "Subscription link"
    override val shareSubscriptionContentDescription = "Share subscription"
    override val refreshSchedule = "Refresh schedule"
    override val refreshScheduleSubtitle = "Choose how often this subscription should be checked."
    override val customInterval = "Custom interval"
    override val customIntervalHint = "A custom interval overrides the subscription value."
    override val autoScheduleDefaultHint = "Auto uses default schedule."
    override fun autoScheduleWithPresetHint(preset: String) = "Auto uses $preset."
    override val deleteSubscriptionTitle = "Delete subscription?"
    override fun deleteSubscriptionMessage(name: String, countLabel: String) = "This will delete “$name” and $countLabel. This cannot be undone."
    override fun cannotBeUndone(name: String, locationsCount: String) = "This will delete “$name” and $locationsCount imported from it. This cannot be undone."
    override val deleteSubscriptionAction = "Delete subscription"
    override fun deleteSubscriptionSubtitle(countLabel: String) = "Remove it and $countLabel"
    override val refreshingEllipsis = "Refreshing…"
    override val refreshNow = "Refresh now"
    override val scheduleAutomatic = "Automatic"
    override val scheduleCustom = "Custom schedule"
    override val scheduleUsesSubscription = "Uses subscription schedule"
    override val customSchedule = "Custom schedule"
    override val usesSubscriptionSchedule = "Uses the subscription schedule"
    override fun autoUsesSchedule(schedule: String) = "Auto uses $schedule."
    override val autoUsesDefaultSchedule = "Auto uses default schedule."
    override val notUpdatedYet = "Not updated yet"
    override fun updatedRelative(time: String) = "Updated $time"
    override val subscriptionSource = "Subscription source"

    override fun subscriptionsCount(count: Int) = when (count) {
        0 -> "No subscriptions"
        1 -> "1 subscription"
        else -> "$count subscriptions"
    }

    override fun locationsCount(count: Int) = when (count) {
        1 -> "1 location"
        else -> "$count locations"
    }

    override fun logsEntriesCount(count: Int) = when (count) {
        0 -> "No entries"
        1 -> "1 entry"
        else -> "$count entries"
    }

    override fun friendlySchedule(ms: Long): String {
        val minuteMs = 60_000L
        val hourMs = 60L * minuteMs
        val dayMs = 24L * hourMs
        return when {
            ms == dayMs -> "Every day"
            ms % dayMs == 0L -> "Every ${ms / dayMs} days"
            ms == hourMs -> "Every hour"
            ms % hourMs == 0L -> "Every ${ms / hourMs} hours"
            ms == minuteMs -> "Every minute"
            else -> "Every ${(ms / minuteMs).coerceAtLeast(1L)} minutes"
        }
    }

    override fun relativeTime(ms: Long, nowMs: Long): String {
        val deltaMs = ms - nowMs
        val isFuture = deltaMs > 0L
        val absoluteMs = if (deltaMs < 0L) -deltaMs else deltaMs
        val minuteMs = 60_000L
        val hourMs = 60L * minuteMs
        val dayMs = 24L * hourMs
        val value = when {
            absoluteMs < minuteMs -> return "just now"
            absoluteMs < hourMs -> "${absoluteMs / minuteMs} min"
            absoluteMs < dayMs -> "${absoluteMs / hourMs} hr"
            else -> "${absoluteMs / dayMs} d"
        }
        return if (isFuture) "in $value" else "$value ago"
    }

    override fun formatBytes(bytes: Long): String {
        val mb = bytes.toDouble() / (1024.0 * 1024.0)
        return if (mb >= 1.0) {
            "${(mb * 10).toInt() / 10.0} MB"
        } else {
            "${bytes / 1024L} KB"
        }
    }

    override fun formatSpeed(bytesPerSec: Long): String {
        val mb = bytesPerSec.toDouble() / (1024.0 * 1024.0)
        return if (mb >= 1.0) {
            "${(mb * 10).toInt() / 10.0} MB/s"
        } else if (bytesPerSec >= 1024L) {
            "${bytesPerSec / 1024L} KB/s"
        } else {
            "$bytesPerSec B/s"
        }
    }

    override val splitTunneling = "Split Tunneling"
    override val routingBehavior = "Routing Behavior"
    override val splitTunnelingModeAll = "All apps"
    override val splitTunnelingModeProxySelected = "Selected apps only"
    override val splitTunnelingModeBypassSelected = "Bypass selected apps"
    override val splitTunnelingApps = "Apps"
    override val appsUsingOlcbox = "Apps Using Olcbox"
    override val bypassedApps = "Bypassed Apps"
    override val noSystemAppsFound = "No system apps found"
    override fun appsIncluded(count: Int) = "${appsCount(count)} included"
    override fun appsHiddenByDefault(count: Int) = "${appsCount(count)} hidden by default"
    override val showSystemApps = "Show system apps"
    override val noAppsFound = "No apps found"
    override val noMatchingApps = "No matching apps"
    override val installAppsHint = "Install launchable apps to configure routing rules."
    override val tryAnotherAppName = "Try another app name or package."
    override val noAppListNeeded = "No app list needed"
    override val everyAppFollowsTun = "Every app follows the same TUN route"
    override val appList = "App List"
    override val everyAppUsesOlcbox = "Every app uses Olcbox"
    override val chooseAppsUsingOlcbox = "Choose apps that use Olcbox"
    override val chooseAppsBypassingOlcbox = "Choose apps that bypass Olcbox"
    override val allAppsUseOlcbox = "All apps use Olcbox"
    override val noAppsSelected = "No apps selected"
    override val noAppsBypassOlcbox = "No apps bypass Olcbox"
    override fun appsCount(count: Int) = if (count == 1) "1 app" else "$count apps"
    override fun appsCountUseOlcbox(count: Int) = "${appsCount(count)} use Olcbox"
    override fun appsCountBypassed(count: Int) = "${appsCount(count)} bypassed"
    override fun appsCountOnly(count: Int) = "Only ${appsCount(count)}"
    override val autoDetectionMayBeInaccurate = "Auto-detection may be inaccurate."
    override val savedForTunMode = "Saved for TUN mode"
    override val appliesWhenSettingsCloses = "Applies when settings closes"
    override val tunModeRoutingRule = "TUN mode routing rule"
    override val required = "Required"
    override val noBypassedApps = "No bypassed apps"
    override val ruBypassNoMatchingApps = "No matching installed apps"
    override fun ruBypassMatchedByPackage(count: Int) = "${appsCount(count)} matched by package"
    override val ruBypassNoAppsSelected = "No RU apps selected"
    override fun ruBypassAlreadySelected(count: Int) = "${appsCount(count)} already selected"
    override fun ruBypassAutoBypassed(count: Int) = "${appsCount(count)} auto-bypassed"
    override fun ruBypassAutoAndManual(autoCount: Int, manualCount: Int) = "$autoCount auto · $manualCount manual"
    override val searchApps = "Search apps"
    override val bypassRuApps = "Bypass RU apps"
    override val ruBypassOn = "RU bypass on"
    override val routingModeTun = "TUN"
    override val routingModeTunDesc = "Route system traffic through a virtual network adapter"
    override val routingModeSystemProxy = "System proxy"
    override val routingModeSystemProxyDesc = "Configure the operating system proxy automatically"
    override val routingModeLocalSocks = "Local SOCKS only"
    override val routingModeLocalSocksDesc = "Expose SOCKS5 without changing system routing"
    override val routingModeAutoDesc = "Use the recommended mode for this operating system"

    override val scanQrOrCopyLink = "Scan QR or copy the link"
    override val locationQrTitle = "Location QR"
    override val subscriptionQrTitle = "Subscription QR"
    override val connectAppsThroughOlcbox = "Connect apps through Olcbox"
    override val iosSocksDescription = "On iOS, Olcbox provides a local SOCKS5 proxy. Add these settings to a SOCKS5-capable client such as Karing or Shadowrocket, then start Olcbox."
    override val gotIt = "Got it"
    override val copySettings = "Copy settings"

    override val configCopied = "Config copied"
    override val allowOlcboxInstallUpdates = "Allow Olcbox to install updates, then tap Download again"
    override fun downloadFailed(error: String) = "Download failed: $error"
    override fun couldNotOpenInstaller(error: String) = "Could not open installer: $error"
    override val waitingForNetwork = "Waiting for network..."
    override val reconnecting = "Reconnecting..."
    override val waitingForTransport = "Waiting for transport..."
    override val connectionFailed = "Connection failed"
    override val tunnelFailed = "Tunnel failed"
    override val vpnTunnelError = "VPN tunnel error"
    override val splitTunnelingError = "Split tunneling error"
    override val addLocationFirst = "Add a location first"
    override val cameraPermissionDenied = "Camera permission denied"
    override val cameraUnavailable = "Camera unavailable"
    override val readyToScan = "Ready to scan"
    override val closeScanner = "Close scanner"
    override val subscriptionOrLocationUri = "Subscription or location URI"
    override val protectingYourConnection = "Protecting your connection"
    override val startingProxy = "Starting proxy..."
}

object RuAppStrings : AppStrings {
    override val appName = "olcbox"
    override val appSubtitle = "конфигуратор olcrtc"
    override val save = "Сохранить"
    override val cancel = "Отмена"
    override val close = "Закрыть"
    override val copy = "Копировать"
    override val copied = "Скопировано"
    override val copiedToClipboard = "Скопировано в буфер"
    override val delete = "Удалить"
    override val share = "Поделиться"
    override val back = "Назад"
    override val clear = "Очистить"
    override val auto = "Авто"
    override val offline = "Офлайн"
    override val checking = "Проверка..."

    override val start = "СТАРТ"
    override val stop = "СТОП"
    override val setup = "НАСТРОЙКА"
    override val startIconDescription = "Кнопка подключения"
    override val relayActive = "Подключение активно"
    override val relayInactive = "Подключение отключено"
    override val activeDescription = "Активно"
    override val inactiveDescription = "Неактивно"
    override val connectivityCheck = "Проверка доступности"
    override val statusConnected = "Подключено"
    override val statusConnecting = "Подключение..."
    override val statusDisconnected = "Отключено"
    override val statusNoLocationSelected = "Локация не выбрана"

    override val appSettingsContentDescription = "Настройки приложения"
    override val historyContentDescription = "Логи"
    override val splitTunnelingContentDescription = "Раздельное туннелирование"
    override val addConfigurationContentDescription = "Добавить"
    override val settings = "Настройки"
    override val selectedLocationDescription = "Выбранная локация"

    override val addConnection = "Новое подключение"
    override val addConnectionSubtitle = "Подписка или своя локация"
    override val scanQrCode = "Сканировать QR-код"
    override val scanQrCodeSubtitle = "Подписка или URI olcrtc"
    override val enterLinkOrUri = "Ввести ссылку или URI"
    override val enterLinkOrUriSubtitle = "Вручную или из буфера"
    override val importFromFile = "Импорт из файла"
    override val importFromFileSubtitle = "Файл подписки или конфиг"
    override val updateSubscriptions = "Обновить подписки"
    override val updateSubscriptionsSubtitle = "Обновить локации подписок"
    override val createCustomLocation = "Создать свою локацию"
    override val createCustomLocationSubtitle = "Комната, ключ, сервис и транспорт"

    override val subscriptions = "Подписки"
    override val customLocations = "Свои локации"
    override val addCustomLocation = "Добавить свою локацию"
    override val addSubscription = "Добавить подписку"
    override val addRelaySetup = "Настройка реле"
    override val addRelaySetupSubtitle = "QR-код, ссылка или файл"
    override val pingButtonLabel = "Ping"
    override val updateButtonLabel = "Обновить"
    override val remaining = "осталось"
    override val used = "исп."
    override fun trafficQuota(used: String, available: String) = "$used исп. · $available доступно"

    override val locationSettingsTitle = "Настройки локации"
    override val shareLocationContentDescription = "Поделиться"
    override val nameLabel = "Название"
    override val locationNamePlaceholder = "Название локации"
    override val connectionType = "Тип подключения"
    override val connectionTypeService = "Сервис"
    override val connectionTypeJitsi = "Jitsi"
    override val serviceProviderLabel = "Сервис"
    override val transportLabel = "Транспорт"
    override val vp8Options = "Параметры VP8"
    override val vp8OptionsSubtitle = "Тонкая настройка видеопотока"
    override val fpsLabel = "FPS"
    override val batchLabel = "Batch"
    override val tracksLabel = "Потоки (MIMO)"
    override val roomIdLabel = "ID комнаты"
    override val roomUrlLabel = "URL комнаты"
    override val encryptionKeyLabel = "Ключ шифрования"
    override val encryptionKeyPlaceholder = "64 hex-символа"
    override val dnsServerLabel = "DNS-сервер (опционально)"
    override val dnsServerPlaceholder = "Авто или 1.1.1.1:53"
    override val clickToVerifyReachability = "Проверить доступность"
    override fun connectedLatency(latency: Long) = "$latency мс"

    override val errorNameEmpty = "Введите название"
    override val errorNameTooLong = "Слишком длинное (макс. 30)"
    override fun errorRoomEmpty(roomLabel: String) = "$roomLabel не может быть пустым"
    override fun errorRoomTooLong(roomLabel: String) = "$roomLabel: слишком длинное значение"
    override val errorKeyEmpty = "Введите ключ"
    override val errorKeyInvalid = "Требуется 64 hex-символа"
    override val errorDnsInvalid = "Формат host:port; пусто для Авто"

    override val importLinkOrUriTitle = "Импорт ссылки или URI"
    override val httpHttpsOlcrtcUriLabel = "HTTP, HTTPS или URI olcrtc"
    override val subscriptionRefreshRateLabel = "Частота обновления"
    override val subscriptionRefreshRateHint = "Необязательно. Пусто — по умолчанию."
    override val subscriptionRefreshRateError = "От 5m до 30d, например 10m, 6h, 1d"
    override val allowInsecureRequests = "Разрешить небезопасные запросы"
    override val pasteClipboard = "Вставить из буфера"
    override val importAction = "Импорт"

    override val configImported = "Конфигурация импортирована"
    override fun subscriptionsUpdatedCount(count: Int) = "Подписки обновлены: $count"
    override val noSubscriptionsToUpdate = "Нет подписок для обновления"
    override val subscriptionUpdated = "Подписка обновлена"
    override val subscriptionAlreadyUpToDate = "Подписка актуальна"
    override fun couldNotUpdateSubscription(message: String) = "Не удалось обновить: $message"
    override val invalidImportLink = "Неверная ссылка. Ожидается olcbox://add?url=<encoded URL>"
    override val noClipboardData = "Буфер обмена пуст"
    override val couldNotReadConfigFile = "Не удалось прочитать файл"
    override val logsSaved = "Логи сохранены"
    override fun logsSavedTo(path: String) = "Логи сохранены в $path"
    override val failedToSaveLogs = "Не удалось сохранить логи"
    override val failedToShareLogs = "Не удалось отправить логи"
    override val subscriptionRefreshSetToAuto = "Обновление подписки: Авто"
    override val subscriptionRefreshRateSaved = "Частота обновления сохранена"
    override fun subscriptionDeletedSummary(removedLocations: Int) =
        "Подписка удалена · удалено $removedLocations ${ruPlural(removedLocations, "локация", "локации", "локаций")}"
    override val socks5ProxySaved = "SOCKS-прокси сохранён"
    override val passwordRegenerated = "Пароль обновлён"
    override val connectionModeSaved = "Режим подключения сохранён"
    override val qrImported = "QR импортирован"

    override val applicationSettingsTitle = "Настройки приложения"
    override val dynamicThemeTitle = "Динамическая тема"
    override val dynamicThemeAndroidColors = "Цвета системы Android"
    override val dynamicThemeOlcboxColors = "Цвета темы Olcbox"
    override val showSpeedInNotification = "Скорость в уведомлении"
    override val showSpeedInNotificationSubtitle = "Отображать текущую скорость передачи данных в шторке"
    override val connectionSettings = "Настройки подключения"
    override val connectionSettingsSubtitle = "Режим и SOCKS5-прокси"
    override val connectionSettingsAndroidSubtitle = "Режим, SOCKS5-прокси и маршрутизация"
    override val subscriptionsAndSharing = "Подписки и обмен"
    override val updateSettings = "Обновления"
    override fun updateSettingsSubtitle(hours: Int) = "Nightly · каждые $hours ч"
    override val applicationLogs = "Логи приложения"
    override val applicationLogsSubtitle = "Диагностика и экспорт"
    override val languageSetting = "Язык"
    override val languageSettingSubtitle = "Язык интерфейса приложения"
    override val systemLanguage = "По умолчанию"
    override val systemLanguageSubtitle = "Использовать язык системы"
    override val russianLanguage = "Русский"
    override val russianLanguageSubtitle = "Русский интерфейс"
    override val englishLanguage = "English"
    override val englishLanguageSubtitle = "English interface"
    override val connectionMode = "Режим подключения"
    override val connectionModeSubtitle = "Маршрутизация TUN и прокси"
    override val modeTunSubtitle = "Полный туннель"
    override val modeProxySubtitle = "Локальный SOCKS5-прокси"
    override val modeTunSummary = "TUN · Полный туннель"
    override val modeProxySummary = "Прокси · Локальный SOCKS5"
    override val modeTunDesc = "Системный VPN-интерфейс"
    override val modeProxyDesc = "Локальный SOCKS-порт"
    override val socks5Proxy = "SOCKS5-прокси"
    override val endpoint = "Адрес"
    override val listenAddress = "Адрес прослушивания"
    override val port = "Порт"
    override val credentials = "Учётные данные"
    override val username = "Имя пользователя"
    override val password = "Пароль"
    override val generatedPassword = "Сгенерированный пароль"
    override val regeneratePassword = "Обновить пароль"
    override val listenAddressRequired = "Укажите адрес"
    override val portRequired = "Укажите порт"
    override fun portRange(min: Int, max: Int) = "Диапазон: $min-$max"
    override val usernameRequired = "Укажите логин"
    override val passwordRequired = "Укажите пароль"
    override val savingRestartsActiveConnection = "Перезапустит активное подключение"
    override val unsavedChange = "Не сохранено"

    override val updatesTitle = "Обновления"
    override val updateAvailable = "Доступно обновление"
    override fun currentVersion(version: String) = "Текущая версия $version"
    override val checkInterval = "Интервал проверки"
    override val lastCheck = "Последняя проверка"
    override val notCheckedYet = "Ещё не проверялось"
    override val checkNow = "Проверить сейчас"
    override val later = "Позже"
    override val download = "Скачать"
    override val install = "Установить"
    override val sizeUnknown = "Размер неизвестен"
    override val updateOfferTitle = "Доступно обновление"
    override val updateServiceUnavailable = "Служба обновлений недоступна"
    override val appIsUpToDate = "Установлена актуальная версия"
    override fun checkingUpdates(channel: String) = "Проверка обновлений..."
    override val updateCheckFailed = "Ошибка проверки обновлений"
    override val allowInstallUpdatesHint = "Разрешите установку обновлений и повторите попытку"
    override fun downloadingUpdate(name: String) = "Загрузка $name..."
    override fun installingUpdate(name: String) = "Установка $name"

    override val noSubscriptions = "Нет подписок"
    override val noSubscriptionsSubtitle = "Импортируйте подписку на главном экране."
    override val backupAndExport = "Резервная копия и экспорт"
    override val exportFullConfig = "Экспорт всей конфигурации"
    override val exportFullConfigSubtitle = "Скопировать все локации в буфер"
    override val subscriptionDetailsTitle = "Подписка"
    override val overview = "Обзор"
    override val locationsLabel = "Локации"
    override val updatedLabel = "Обновлено"
    override val nextLabel = "След. обновление"
    override val notYet = "Не обновлялось"
    override val onAppStart = "При запуске"
    override val sourceLabel = "Источник"
    override val subscriptionLink = "Ссылка на подписку"
    override val shareSubscriptionContentDescription = "Поделиться подпиской"
    override val refreshSchedule = "Расписание обновления"
    override val refreshScheduleSubtitle = "Выберите частоту проверки подписки."
    override val customInterval = "Свой интервал"
    override val customIntervalHint = "Свой интервал заменяет расписание подписки."
    override val autoScheduleDefaultHint = "Использует расписание из подписки."
    override fun autoScheduleWithPresetHint(preset: String) = "Использует $preset."
    override val deleteSubscriptionTitle = "Удалить подписку?"
    override fun deleteSubscriptionMessage(name: String, countLabel: String) =
        "Это удалит «$name» и $countLabel. Действие необратимо."
    override fun cannotBeUndone(name: String, locationsCount: String) =
        "Это удалит «$name» и $locationsCount. Действие необратимо."
    override val deleteSubscriptionAction = "Удалить подписку"
    override fun deleteSubscriptionSubtitle(countLabel: String) = "Удалить подписку и $countLabel"
    override val refreshingEllipsis = "Обновление…"
    override val refreshNow = "Обновить сейчас"
    override val scheduleAutomatic = "Автоматически"
    override val scheduleCustom = "Своё расписание"
    override val scheduleUsesSubscription = "По расписанию подписки"
    override val customSchedule = "Своё расписание"
    override val usesSubscriptionSchedule = "По расписанию подписки"
    override fun autoUsesSchedule(schedule: String) = "Авто использует $schedule."
    override val autoUsesDefaultSchedule = "Авто использует стандартное расписание."
    override val notUpdatedYet = "Ещё не обновлялось"
    override fun updatedRelative(time: String) = "Обновлено $time"
    override val subscriptionSource = "Источник подписки"

    override fun subscriptionsCount(count: Int): String = when (count) {
        0 -> "Нет подписок"
        else -> "$count ${ruPlural(count, "подписка", "подписки", "подписок")}"
    }

    override fun locationsCount(count: Int): String =
        "$count ${ruPlural(count, "локация", "локации", "локаций")}"

    override fun logsEntriesCount(count: Int): String = when (count) {
        0 -> "Нет записей"
        else -> "$count ${ruPlural(count, "запись", "записи", "записей")}"
    }

    override fun friendlySchedule(ms: Long): String {
        val minuteMs = 60_000L
        val hourMs = 60L * minuteMs
        val dayMs = 24L * hourMs
        return when {
            ms == dayMs -> "Каждый день"
            ms % dayMs == 0L -> {
                val days = (ms / dayMs).toInt()
                "Каждые $days ${ruPlural(days, "день", "дня", "дней")}"
            }
            ms == hourMs -> "Каждый час"
            ms % hourMs == 0L -> {
                val hours = (ms / hourMs).toInt()
                "Каждые $hours ${ruPlural(hours, "час", "часа", "часов")}"
            }
            ms == minuteMs -> "Каждую минуту"
            else -> {
                val mins = (ms / minuteMs).toInt().coerceAtLeast(1)
                "Каждые $mins ${ruPlural(mins, "минуту", "минуты", "минут")}"
            }
        }
    }

    override fun relativeTime(ms: Long, nowMs: Long): String {
        val deltaMs = ms - nowMs
        val isFuture = deltaMs > 0L
        val absoluteMs = if (deltaMs < 0L) -deltaMs else deltaMs
        val minuteMs = 60_000L
        val hourMs = 60L * minuteMs
        val dayMs = 24L * hourMs
        val value = when {
            absoluteMs < minuteMs -> return "только что"
            absoluteMs < hourMs -> "${absoluteMs / minuteMs} мин"
            absoluteMs < dayMs -> "${absoluteMs / hourMs} ч"
            else -> "${absoluteMs / dayMs} дн."
        }
        return if (isFuture) "через $value" else "$value назад"
    }

    override fun formatBytes(bytes: Long): String {
        val mb = bytes.toDouble() / (1024.0 * 1024.0)
        return if (mb >= 1.0) {
            "${(mb * 10).toInt() / 10.0} МБ"
        } else {
            "${bytes / 1024L} КБ"
        }
    }

    override fun formatSpeed(bytesPerSec: Long): String {
        val mb = bytesPerSec.toDouble() / (1024.0 * 1024.0)
        return if (mb >= 1.0) {
            "${(mb * 10).toInt() / 10.0} МБ/с"
        } else if (bytesPerSec >= 1024L) {
            "${bytesPerSec / 1024L} КБ/с"
        } else {
            "$bytesPerSec Б/с"
        }
    }

    override val splitTunneling = "Раздельное туннелирование"
    override val routingBehavior = "Маршрутизация трафика"
    override val splitTunnelingModeAll = "Все приложения"
    override val splitTunnelingModeProxySelected = "Только выбранные"
    override val splitTunnelingModeBypassSelected = "В обход VPN"
    override val splitTunnelingApps = "Приложения"
    override val appsUsingOlcbox = "Приложения через Olcbox"
    override val bypassedApps = "Приложения в обход Olcbox"
    override val noSystemAppsFound = "Системные приложения не найдены"
    override fun appsIncluded(count: Int) = "${appsCount(count)} включено"
    override fun appsHiddenByDefault(count: Int) = "${appsCount(count)} скрыто по умолчанию"
    override val showSystemApps = "Системные приложения"
    override val noAppsFound = "Приложения не найдены"
    override val noMatchingApps = "Ничего не найдено"
    override val installAppsHint = "Установите приложения для настройки правил маршрутизации."
    override val tryAnotherAppName = "Попробуйте другое название или имя пакета."
    override val noAppListNeeded = "Список приложений не требуется"
    override val everyAppFollowsTun = "Весь трафик направляется через TUN"
    override val appList = "Список приложений"
    override val everyAppUsesOlcbox = "Все приложения используют Olcbox"
    override val chooseAppsUsingOlcbox = "Выберите приложения для работы через Olcbox"
    override val chooseAppsBypassingOlcbox = "Выберите приложения для работы в обход Olcbox"
    override val allAppsUseOlcbox = "Все приложения работают через Olcbox"
    override val noAppsSelected = "Приложения не выбраны"
    override val noAppsBypassOlcbox = "Нет приложений в обход"
    override fun appsCount(count: Int) = "$count ${ruPlural(count, "приложение", "приложения", "приложений")}"
    override fun appsCountUseOlcbox(count: Int) = "${appsCount(count)} через Olcbox"
    override fun appsCountBypassed(count: Int) = "${appsCount(count)} в обход"
    override fun appsCountOnly(count: Int) = "Только ${appsCount(count)}"
    override val autoDetectionMayBeInaccurate = "Автоопределение может быть неточным."
    override val savedForTunMode = "Сохранено для режима TUN"
    override val appliesWhenSettingsCloses = "Применится при закрытии настроек"
    override val tunModeRoutingRule = "Правило маршрутизации в режиме TUN"
    override val required = "Обязательно"
    override val noBypassedApps = "Нет исключённых приложений"
    override val ruBypassNoMatchingApps = "Нет подходящих приложений"
    override fun ruBypassMatchedByPackage(count: Int) = "${appsCount(count)} найдено по пакету"
    override val ruBypassNoAppsSelected = "RU-приложения не выбраны"
    override fun ruBypassAlreadySelected(count: Int) = "${appsCount(count)} уже выбрано"
    override fun ruBypassAutoBypassed(count: Int) = "${appsCount(count)} в обход автоматически"
    override fun ruBypassAutoAndManual(autoCount: Int, manualCount: Int) = "$autoCount авто · $manualCount вручную"
    override val searchApps = "Поиск приложений"
    override val bypassRuApps = "В обход RU-приложений"
    override val ruBypassOn = "Обход RU включён"
    override val routingModeTun = "TUN"
    override val routingModeTunDesc = "Маршрутизация трафика через виртуальный адаптер"
    override val routingModeSystemProxy = "Системный прокси"
    override val routingModeSystemProxyDesc = "Автоматическая настройка системного прокси"
    override val routingModeLocalSocks = "Только локальный SOCKS"
    override val routingModeLocalSocksDesc = "SOCKS5-порт без изменения системной маршрутизации"
    override val routingModeAutoDesc = "Рекомендуемый режим для этой системы"

    override val scanQrOrCopyLink = "Отсканируйте QR или скопируйте ссылку"
    override val locationQrTitle = "QR локации"
    override val subscriptionQrTitle = "QR подписки"
    override val connectAppsThroughOlcbox = "Подключение приложений через Olcbox"
    override val iosSocksDescription = "На iOS Olcbox запускает локальный SOCKS5-прокси. Добавьте эти настройки в клиент (Karing, Shadowrocket и др.) и запустите Olcbox."
    override val gotIt = "Понятно"
    override val copySettings = "Скопировать настройки"

    override val configCopied = "Конфигурация скопирована"
    override val allowOlcboxInstallUpdates = "Разрешите установку обновлений и повторите попытку"
    override fun downloadFailed(error: String) = "Ошибка загрузки: $error"
    override fun couldNotOpenInstaller(error: String) = "Не удалось запустить установщик: $error"
    override val waitingForNetwork = "Ожидание сети..."
    override val reconnecting = "Переподключение..."
    override val waitingForTransport = "Ожидание транспорта..."
    override val connectionFailed = "Ошибка подключения"
    override val tunnelFailed = "Ошибка туннеля"
    override val vpnTunnelError = "Ошибка VPN-туннеля"
    override val splitTunnelingError = "Ошибка раздельного туннелирования"
    override val addLocationFirst = "Сначала добавьте локацию"
    override val cameraPermissionDenied = "В доступе к камере отказано"
    override val cameraUnavailable = "Камера недоступна"
    override val readyToScan = "Готово к сканированию"
    override val closeScanner = "Закрыть сканер"
    override val subscriptionOrLocationUri = "URI подписки или локации"
    override val protectingYourConnection = "Защита подключения"
    override val startingProxy = "Запуск прокси..."
}

private fun ruPlural(count: Int, one: String, few: String, many: String): String {
    val mod10 = count % 10
    val mod100 = count % 100
    return when {
        mod100 in 11..19 -> many
        mod10 == 1 -> one
        mod10 in 2..4 -> few
        else -> many
    }
}

fun appStringsFor(language: AppLanguage): AppStrings = when (language) {
    AppLanguage.Russian -> RuAppStrings
    AppLanguage.English -> EnAppStrings
    AppLanguage.System -> if (detectSystemLanguage() == AppLanguage.Russian) RuAppStrings else EnAppStrings
}
