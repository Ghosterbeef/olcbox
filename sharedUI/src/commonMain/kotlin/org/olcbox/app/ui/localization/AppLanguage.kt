package org.olcbox.app.ui.localization

enum class AppLanguage(val code: String, val displayName: String) {
    System("system", "По умолчанию"),
    Russian("ru", "Русский"),
    English("en", "English");

    companion object {
        fun fromCode(code: String?): AppLanguage {
            return entries.firstOrNull { it.code.equals(code, ignoreCase = true) } ?: System
        }
    }
}
