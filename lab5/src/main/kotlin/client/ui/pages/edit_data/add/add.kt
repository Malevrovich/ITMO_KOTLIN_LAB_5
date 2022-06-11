package client.ui.pages.edit_data.add

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import client.commands.dto.CommandDTOFactory
import client.ui.pages.edit_data.movieEditWithSubmit
import client.ui.pages.msgDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.compose.LocalDI
import org.kodein.di.instance
import share.executor.Executor


@Composable
@Preview
fun addDialog(isOpen: Boolean, onCloseRequest: () -> Unit){
    if(isOpen){
        Dialog(
            onCloseRequest = onCloseRequest,
            state = rememberDialogState(position = WindowPosition(Alignment.Center),
                                        width = 400.dp, height = 600.dp),
            resizable = false,
            title = "Add"
        ) {
            val di = LocalDI.current

            val executor: Executor by di.instance()
            val commandDTOFactory: CommandDTOFactory by di.instance()

            val scope = rememberCoroutineScope()

            val isFreezed = remember{ mutableStateOf(false)}

            val isMsgOpen = remember { mutableStateOf(false)}
            val msg = remember { mutableStateOf("") }

            msgDialog(
                isOpen = isMsgOpen.value,
                onCloseRequest = {
                    isMsgOpen.value = false
                },
                msg = msg.value
            )

            movieEditWithSubmit(
                "Add",
                onSubmit = { movie ->

                    isFreezed.value = true
                    scope.launch(Dispatchers.Default) {
                        val cmd = commandDTOFactory.buildAdd(movie)

                        val result = executor.execute(cmd)

                        msg.value = result.out
                    }.invokeOnCompletion {
                        isMsgOpen.value = true
                        isFreezed.value = false
                    }
                },
                isFreezed = isFreezed.value
            )
        }
    }
}