package org.olcbox.app.ui.localization

import platform.Foundation.NSLocale
import platform.Foundation.preferredLanguages

actual fun detectSystemLanguage(): AppLanguage {
    val languages = NSLocale.preferredLanguages
    val firstLang = (languages.firstOrNull() as? String)?.lowercase() ?: ""
    return if (firstLang.startsWith("ru")) AppLanguage.Russian else AppLanguage.English
}
