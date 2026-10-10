package org.olcbox.app.ui.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf

val LocalAppStrings = staticCompositionLocalOf<AppStrings> { RuAppStrings }
val LocalAppLanguage = staticCompositionLocalOf<AppLanguage> { AppLanguage.System }

object AppLocalization {
    var userSelectedLanguage: AppLanguage by mutableStateOf(AppLanguage.System)

    val currentLanguage: AppLanguage
        get() = userSelectedLanguage

    val strings: AppStrings
        get() = appStringsFor(userSelectedLanguage)
}

@Composable
fun ProvideAppLocalization(
    language: AppLanguage = AppLocalization.currentLanguage,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalAppLanguage provides language,
        LocalAppStrings provides appStringsFor(language),
        content = content
    )
}
