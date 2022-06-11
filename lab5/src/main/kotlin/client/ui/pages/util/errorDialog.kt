package client.ui.pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import client.ui.theme.contentFont
import client.ui.theme.labelFont
import client.ui.theme.onBgPrimary

@Composable
@Preview
fun msgDialog(isOpen: Boolean, onCloseRequest: () -> Unit, msg: String){
    if(isOpen){
        Dialog(
            onCloseRequest = onCloseRequest,
            state = rememberDialogState(position = WindowPosition(Alignment.Center),
                width = 300.dp, height = 200.dp),
            resizable = false,
            title = "Error"
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    style = contentFont,
                    color = onBgPrimary,
                    text = msg,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.weight(0.05f))
                OutlinedButton(
                    onClick = onCloseRequest,
                    modifier = Modifier.weight(1f).fillMaxWidth(0.33f)
                ){
                    Text(
                        style = labelFont,
                        color = onBgPrimary,
                        text = "OK"
                    )
                }
                Spacer(modifier = Modifier.weight(0.05f))
            }
        }
    }
}