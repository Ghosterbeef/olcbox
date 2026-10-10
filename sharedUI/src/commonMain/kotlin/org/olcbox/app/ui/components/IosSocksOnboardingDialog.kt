package org.olcbox.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import org.olcbox.app.ui.localization.LocalAppStrings

@Composable
fun IosSocksOnboardingDialog(
    settings: ApplicationSocksProxySettings,
    onCopy: () -> Unit,
    onDismiss: () -> Unit
) {
    val strings = LocalAppStrings.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(strings.connectAppsThroughOlcbox) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(strings.iosSocksDescription)
                SelectionContainer {
                    Text(
                        text = socksSettingsText(settings),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(strings.gotIt)
            }
        },
        dismissButton = {
            TextButton(onClick = onCopy) {
                Text(strings.copySettings)
            }
        }
    )
}

fun socksSettingsText(settings: ApplicationSocksProxySettings): String = buildString {
    appendLine("Type: SOCKS5")
    appendLine("Server: ${settings.host}")
    appendLine("Port: ${settings.port}")
    appendLine("Username: ${settings.username}")
    append("Password: ${settings.password}")
}
