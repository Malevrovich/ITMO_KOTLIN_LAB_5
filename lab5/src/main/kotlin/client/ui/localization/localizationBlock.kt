package client.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import client.ui.localization.localizationDialog
import client.ui.theme.contentFont
import client.ui.theme.onBgSecondary

@Composable
fun localizationBlock(modifier: Modifier = Modifier){

    val isLocalizationDialogOpen = remember { mutableStateOf(false) }

    localizationDialog(
        isOpen = isLocalizationDialogOpen.value,
        onCloseRequest = {
            isLocalizationDialogOpen.value = false
        },
        onSubmit = {
            isLocalizationDialogOpen.value = false
        }
    )

    OutlinedButton(
        modifier = modifier.fillMaxSize(),
        onClick = {
            isLocalizationDialogOpen.value = true
        },
        border = BorderStroke(width = (0.5).dp, color = onBgSecondary)
    ){
        Text(
            style = contentFont,
            color = onBgSecondary,
            fontSize = 12.sp,
            text = "Language"
        )
    }
}