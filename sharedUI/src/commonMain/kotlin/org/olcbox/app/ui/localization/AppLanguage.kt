package org.olcbox.app.ui.localization

enum class AppLanguage(val code: String, val displayName: String) {
    System("system", "По умолчанию"),
    Russian("ru", "Русский"),
    English("en", "English");

    fun title(strings: AppStrings): String = when (this) {
        System -> strings.systemLanguage
        Russian -> strings.russianLanguage
        English -> strings.englishLanguage
    }

    fun subtitle(strings: AppStrings): String = when (this) {
        System -> strings.systemLanguageSubtitle
        Russian -> strings.russianLanguageSubtitle
        English -> strings.englishLanguageSubtitle
    }

    companion object {
        fun fromCode(code: String?): AppLanguage {
            return entries.firstOrNull { it.code.equals(code, ignoreCase = true) } ?: System
        }
    }
}
