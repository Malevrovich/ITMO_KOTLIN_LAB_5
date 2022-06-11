package client.ui.pages.edit_data.remove

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import client.commands.dto.CommandDTOFactory
import client.ui.pages.edit_data.submitButton
import client.ui.pages.msgDialog
import client.ui.pages.util.header
import client.ui.theme.contentFont
import client.ui.theme.onBgPrimary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.compose.LocalDI
import org.kodein.di.instance
import share.executor.Executor

@Composable
@Preview
fun removeDialog(isOpen: Boolean, id: Int, onSuccess: () -> Unit = {}, onCloseRequest: () -> Unit){
    val isMsgOpen = remember{ mutableStateOf(false) }
    val msg = remember { mutableStateOf("") }

    msgDialog(
        isOpen = isMsgOpen.value,
        onCloseRequest = {
            isMsgOpen.value = false
        },
        msg = msg.value
    )

    val isFreezed = remember{ mutableStateOf(false) }

    if(isOpen){
        Dialog(
            onCloseRequest = onCloseRequest,
            state = rememberDialogState(position = WindowPosition(Alignment.Center),
                width = 400.dp, height = 600.dp),
            resizable = false,
            title = "Update"
        ) {
            val di = LocalDI.current

            val executor: Executor by di.instance()
            val commandDTOFactory: CommandDTOFactory by di.instance()

            val scope = rememberCoroutineScope()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxSize()
            ) {
                header("Remove")
                Text(
                    style = contentFont,
                    color = onBgPrimary,
                    text = "Do you want to delete item with id = $id?"
                )
                submitButton(
                    onClick = {
                        isFreezed.value = true
                        scope.launch(Dispatchers.Default) {
                            val cmd = commandDTOFactory.buildRemove(id)

                            val res = executor.execute(cmd)

                            msg.value = res.out
                        }.invokeOnCompletion {
                            isFreezed.value = false
                            isMsgOpen.value = true
                            onSuccess()
                        }
                    },
                    isFreezed.value
                )
            }
        }
    }
}