package org.olcbox.app.ui.localization

interface AppStrings {
    // Actions & Common
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

    // Main / Status
    val start: String
    val stop: String
    val setup: String
    val relayActive: String
    val relayInactive: String
    val statusConnected: String
    val statusConnecting: String
    val statusDisconnected: String
    val statusNoLocationSelected: String

    // App Bar Tooltips
    val appSettingsContentDescription: String
    val historyContentDescription: String
    val splitTunnelingContentDescription: String
    val addConfigurationContentDescription: String

    // Add Sheet
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

    // Location Selection & Groups
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

    // Location Settings
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

    // Validation Errors
    val errorNameEmpty: String
    val errorNameTooLong: String
    fun errorRoomEmpty(roomLabel: String): String
    fun errorRoomTooLong(roomLabel: String): String
    val errorKeyEmpty: String
    val errorKeyInvalid: String
    val errorDnsInvalid: String

    // Manual Import Dialog
    val importLinkOrUriTitle: String
    val httpHttpsOlcrtcUriLabel: String
    val subscriptionRefreshRateLabel: String
    val subscriptionRefreshRateHint: String
    val subscriptionRefreshRateError: String
    val allowInsecureRequests: String
    val pasteClipboard: String
    val importAction: String

    // Notifications & Toasts
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
    val qrImported: String

    // Application Settings Sheet
    val applicationSettingsTitle: String
    val connectionSettings: String
    val connectionSettingsSubtitle: String
    val subscriptionsAndSharing: String
    val updateSettings: String
    fun updateSettingsSubtitle(hours: Int): String
    val applicationLogs: String
    val applicationLogsSubtitle: String
    val languageSetting: String
    val connectionMode: String
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
    val deleteSubscriptionAction: String
    fun deleteSubscriptionSubtitle(countLabel: String): String
    val refreshingEllipsis: String
    val refreshNow: String
    val scheduleAutomatic: String
    val scheduleCustom: String
    val scheduleUsesSubscription: String

    // Plurals & formatting
    fun subscriptionsCount(count: Int): String
    fun locationsCount(count: Int): String
    fun logsEntriesCount(count: Int): String
    fun friendlySchedule(ms: Long): String
    fun relativeTime(ms: Long, nowMs: Long): String
    fun formatBytes(bytes: Long): String

    // Split Tunneling & Routing
    val splitTunneling: String
    val splitTunnelingModeAll: String
    val splitTunnelingModeProxySelected: String
    val splitTunnelingModeBypassSelected: String
    val splitTunnelingApps: String
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
    override val relayActive = "Relay Active"
    override val relayInactive = "Relay Inactive"
    override val statusConnected = "Connected"
    override val statusConnecting = "Connecting..."
    override val statusDisconnected = "Disconnected"
    override val statusNoLocationSelected = "No location selected"

    override val appSettingsContentDescription = "Application settings"
    override val historyContentDescription = "History"
    override val splitTunnelingContentDescription = "Split tunneling"
    override val addConfigurationContentDescription = "Add configuration"

    override val addConnection = "Add connection"
    override val addConnectionSubtitle = "Subscription or custom location"
    override val scanQrCode = "Scan QR code"
    override val scanQrCodeSubtitle = "Subscription or olcrtc URI"
    override val enterLinkOrUri = "Enter link or URI"
    override val enterLinkOrUriSubtitle = "Type, edit, or import from clipboard"
    override val importFromFile = "Import from file"
    override val importFromFileSubtitle = "Read subscription or config file"
    override val updateSubscriptions = "Update subscriptions"
    override val updateSubscriptionsSubtitle = "Refresh imported subscription locations"
    override val createCustomLocation = "Create custom location"
    override val createCustomLocationSubtitle = "Enter room, key, provider, and transport"

    override val subscriptions = "Subscriptions"
    override val customLocations = "Custom locations"
    override val addCustomLocation = "Add custom location"
    override val addSubscription = "Add subscription"
    override val addRelaySetup = "Add relay setup"
    override val addRelaySetupSubtitle = "Scan QR, paste URI, or import file"
    override val pingButtonLabel = "Ping"
    override val updateButtonLabel = "Update"
    override val remaining = "remaining"
    override val used = "used"
    override fun trafficQuota(used: String, available: String) = "$used used · $available available"

    override val locationSettingsTitle = "Location settings"
    override val shareLocationContentDescription = "Share location"
    override val nameLabel = "Name"
    override val locationNamePlaceholder = "Location name"
    override val connectionType = "Connection type"
    override val connectionTypeService = "Service"
    override val connectionTypeJitsi = "Jitsi"
    override val serviceProviderLabel = "Service"
    override val transportLabel = "Transport"
    override val vp8Options = "VP8 options"
    override val vp8OptionsSubtitle = "Fine-tune stream performance"
    override val fpsLabel = "FPS"
    override val batchLabel = "Batch"
    override val tracksLabel = "Tracks (MIMO)"
    override val roomIdLabel = "Room ID"
    override val roomUrlLabel = "Room URL"
    override val encryptionKeyLabel = "Encryption key"
    override val encryptionKeyPlaceholder = "64 hex characters"
    override val dnsServerLabel = "DNS server (optional)"
    override val dnsServerPlaceholder = "Auto, or 1.1.1.1:53"
    override val clickToVerifyReachability = "Click To Verify Reachability"
    override fun connectedLatency(latency: Long) = "Connected ${latency}ms"

    override val errorNameEmpty = "Name cannot be empty"
    override val errorNameTooLong = "Name is too long (max 30 chars)"
    override fun errorRoomEmpty(roomLabel: String) = "$roomLabel cannot be empty"
    override fun errorRoomTooLong(roomLabel: String) = "$roomLabel is too long"
    override val errorKeyEmpty = "Key cannot be empty"
    override val errorKeyInvalid = "Key must be 64 hex characters"
    override val errorDnsInvalid = "Use host:port or [IPv6]:port; leave empty for Auto"

    override val importLinkOrUriTitle = "Import link or URI"
    override val httpHttpsOlcrtcUriLabel = "HTTP, HTTPS, or olcrtc URI"
    override val subscriptionRefreshRateLabel = "Subscription refresh rate"
    override val subscriptionRefreshRateHint = "Optional. Empty implies default."
    override val subscriptionRefreshRateError = "Use 5m–30d, for example 10m, 6h, or 1d"
    override val allowInsecureRequests = "Allow insecure requests"
    override val pasteClipboard = "Paste clipboard"
    override val importAction = "Import"

    override val configImported = "Configuration imported"
    override fun subscriptionsUpdatedCount(count: Int) = "Subscriptions updated: $count"
    override val noSubscriptionsToUpdate = "No subscriptions to update"
    override val subscriptionUpdated = "Subscription updated"
    override val subscriptionAlreadyUpToDate = "Subscription is already up to date"
    override fun couldNotUpdateSubscription(message: String) = "Could not update subscription: $message"
    override val invalidImportLink = "Invalid import link. Expected olcbox://add?url=<encoded HTTP(S) URL>"
    override val noClipboardData = "No clipboard data found"
    override val couldNotReadConfigFile = "Could not read config file"
    override val logsSaved = "Logs saved"
    override fun logsSavedTo(path: String) = "Logs saved to $path"
    override val failedToSaveLogs = "Failed to save logs"
    override val failedToShareLogs = "Failed to share logs"
    override val subscriptionRefreshSetToAuto = "Subscription refresh set to Auto"
    override val subscriptionRefreshRateSaved = "Subscription refresh rate saved"
    override fun subscriptionDeletedSummary(removedLocations: Int) = "Subscription deleted · $removedLocations locations removed"
    override val qrImported = "QR imported"

    override val applicationSettingsTitle = "Application Settings"
    override val connectionSettings = "Connection Settings"
    override val connectionSettingsSubtitle = "Mode and SOCKS5 proxy"
    override val subscriptionsAndSharing = "Subscriptions & Sharing"
    override val updateSettings = "Update Settings"
    override fun updateSettingsSubtitle(hours: Int) = "Nightly · every ${hours}h"
    override val applicationLogs = "Application Logs"
    override val applicationLogsSubtitle = "Diagnostics and export"
    override val languageSetting = "Language"
    override val connectionMode = "Connection Mode"
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
    override val savingRestartsActiveConnection = "Saving restarts the active connection"
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
    override val appIsUpToDate = "Olcbox is up to date"
    override fun checkingUpdates(channel: String) = "Checking ${channel.lowercase()}..."
    override val updateCheckFailed = "Update check failed"
    override val allowInstallUpdatesHint = "Allow Olcbox to install updates, then tap Download again"
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
    override val autoScheduleDefaultHint = "Auto uses the schedule supplied by the subscription."
    override fun autoScheduleWithPresetHint(preset: String) = "Auto uses $preset."
    override val deleteSubscriptionTitle = "Delete subscription?"
    override fun deleteSubscriptionMessage(name: String, countLabel: String) =
        "This will delete “$name” and $countLabel imported from it. This cannot be undone."
    override val deleteSubscriptionAction = "Delete subscription"
    override fun deleteSubscriptionSubtitle(countLabel: String) = "Remove it and $countLabel"
    override val refreshingEllipsis = "Refreshing…"
    override val refreshNow = "Refresh now"
    override val scheduleAutomatic = "Automatic"
    override val scheduleCustom = "Custom schedule"
    override val scheduleUsesSubscription = "Uses the subscription schedule"

    override fun subscriptionsCount(count: Int): String = when (count) {
        0 -> "No subscriptions"
        1 -> "1 subscription"
        else -> "$count subscriptions"
    }

    override fun locationsCount(count: Int): String = when (count) {
        1 -> "1 location"
        else -> "$count locations"
    }

    override fun logsEntriesCount(count: Int): String = when (count) {
        0 -> "No entries"
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

    override val splitTunneling = "Split tunneling"
    override val splitTunnelingModeAll = "All apps"
    override val splitTunnelingModeProxySelected = "Only selected apps"
    override val splitTunnelingModeBypassSelected = "Bypass selected apps"
    override val splitTunnelingApps = "Apps"
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
    override val relayActive = "Реле активно"
    override val relayInactive = "Реле неактивно"
    override val statusConnected = "Подключено"
    override val statusConnecting = "Подключение..."
    override val statusDisconnected = "Отключено"
    override val statusNoLocationSelected = "Локация не выбрана"

    override val appSettingsContentDescription = "Настройки приложения"
    override val historyContentDescription = "Логи"
    override val splitTunnelingContentDescription = "Раздельное туннелирование"
    override val addConfigurationContentDescription = "Добавить"

    override val addConnection = "Новое подключение"
    override val addConnectionSubtitle = "Подписка или своя локация"
    override val scanQrCode = "Сканировать QR-код"
    override val scanQrCodeSubtitle = "Подписка или URI olcrtc"
    override val enterLinkOrUri = "Ввести ссылку или URI"
    override val enterLinkOrUriSubtitle = "Ввод, правка или из буфера"
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
    override val pingButtonLabel = "Ping" // Preserved established term!
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
    override val clickToVerifyReachability = "Нажмите для проверки"
    override fun connectedLatency(latency: Long) = "Доступно ${latency} мс"

    override val errorNameEmpty = "Введите название"
    override val errorNameTooLong = "Слишком длинное (макс. 30)"
    override fun errorRoomEmpty(roomLabel: String) = "$roomLabel не может быть пустым"
    override fun errorRoomTooLong(roomLabel: String) = "$roomLabel слишком длинный"
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
    override val subscriptionAlreadyUpToDate = "Подписка уже обновлена"
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
    override val qrImported = "QR импортирован"

    override val applicationSettingsTitle = "Настройки приложения"
    override val connectionSettings = "Настройки подключения"
    override val connectionSettingsSubtitle = "Режим и SOCKS5-прокси"
    override val subscriptionsAndSharing = "Подписки и экспорт"
    override val updateSettings = "Обновления"
    override fun updateSettingsSubtitle(hours: Int) = "Nightly · каждые $hours ч"
    override val applicationLogs = "Логи приложения"
    override val applicationLogsSubtitle = "Диагностика и экспорт"
    override val languageSetting = "Язык"
    override val connectionMode = "Режим подключения"
    override val socks5Proxy = "SOCKS5-прокси"
    override val endpoint = "Адрес"
    override val listenAddress = "Адрес прослушивания"
    override val port = "Порт"
    override val credentials = "Авторизация"
    override val username = "Имя пользователя"
    override val password = "Пароль"
    override val generatedPassword = "Сгенерированный пароль"
    override val regeneratePassword = "Сгенерировать пароль"
    override val listenAddressRequired = "Укажите адрес"
    override val portRequired = "Укажите порт"
    override fun portRange(min: Int, max: Int) = "Диапазон: $min-$max"
    override val usernameRequired = "Укажите логин"
    override val passwordRequired = "Укажите пароль"
    override val savingRestartsActiveConnection = "Сохранение перезапустит связь"
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
    override val updateOfferTitle = "Обновите приложение"
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
    override val nextLabel = "Следующее"
    override val notYet = "Ещё нет"
    override val onAppStart = "При запуске"
    override val sourceLabel = "Источник"
    override val subscriptionLink = "Ссылка подписки"
    override val shareSubscriptionContentDescription = "Поделиться подпиской"
    override val refreshSchedule = "Расписание обновления"
    override val refreshScheduleSubtitle = "Выберите частоту проверки подписки."
    override val customInterval = "Свой интервал"
    override val customIntervalHint = "Свой интервал переопределяет значение подписки."
    override val autoScheduleDefaultHint = "Авто использует расписание из подписки."
    override fun autoScheduleWithPresetHint(preset: String) = "Авто использует $preset."
    override val deleteSubscriptionTitle = "Удалить подписку?"
    override fun deleteSubscriptionMessage(name: String, countLabel: String) =
        "Это удалит «$name» и $countLabel. Действие необратимо."
    override val deleteSubscriptionAction = "Удалить подписку"
    override fun deleteSubscriptionSubtitle(countLabel: String) = "Удалить её и $countLabel"
    override val refreshingEllipsis = "Обновление…"
    override val refreshNow = "Обновить сейчас"
    override val scheduleAutomatic = "Автоматически"
    override val scheduleCustom = "Своё расписание"
    override val scheduleUsesSubscription = "По расписанию подписки"

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

    override val splitTunneling = "Раздельное туннелирование"
    override val splitTunnelingModeAll = "Все приложения"
    override val splitTunnelingModeProxySelected = "Только выбранные"
    override val splitTunnelingModeBypassSelected = "В обход VPN"
    override val splitTunnelingApps = "Приложения"
    override val searchApps = "Поиск приложений"
    override val bypassRuApps = "В обход RU-приложений"
    override val ruBypassOn = "Обход RU включён"
    override val routingModeTun = "TUN"
    override val routingModeTunDesc = "Трафик системы через виртуальный сетевой адаптер"
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
