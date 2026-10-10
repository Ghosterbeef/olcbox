package org.olcbox.app.ui.localization

import java.util.Locale

actual fun detectSystemLanguage(): AppLanguage {
    val lang = Locale.getDefault().language.lowercase()
    return if (lang.startsWith("ru")) AppLanguage.Russian else AppLanguage.English
}
