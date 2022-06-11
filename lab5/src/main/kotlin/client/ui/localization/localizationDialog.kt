package client.ui.localization

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import client.ui.pages.util.labeledRadioButton
import client.ui.theme.contentFont
import client.ui.theme.onBgPrimary
import org.kodein.di.compose.LocalDI
import org.kodein.di.instance
import share.localization.Language
import share.localization.Localization

@Composable
fun localizationDialog(isOpen: Boolean, onCloseRequest: () -> Unit, onSubmit: () -> Unit){

    val di = LocalDI.current
    val localization: Localization by di.instance()

    val params = Language.values().map { it.name }
    val selectedOption = remember { mutableStateOf(params[0]) }

    if(isOpen) {
        Dialog(
            onCloseRequest = onCloseRequest,
            state = rememberDialogState(
                position = WindowPosition(Alignment.Center),
                width = 400.dp, height = 600.dp
            ),
            resizable = false,
            title = "Add"
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center).fillMaxHeight(0.75f)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(0.80f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        params.forEach { text ->
                            labeledRadioButton(
                                label = text,
                                selected = (selectedOption.value == text),
                                onClick = {
                                    selectedOption.value = text
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(0.33f),
                        border = BorderStroke((0.5).dp, color = onBgPrimary),
                        onClick = {
                            localization.setLanguage(Language.valueOf(selectedOption.value))
                            onSubmit()
                        }
                    ) {
                        Text(
                            style = contentFont,
                            color = onBgPrimary,
                            text = "Apply"
                        )
                    }
                }
            }
        }
    }
}