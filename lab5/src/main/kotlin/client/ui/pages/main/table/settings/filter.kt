package client.ui.pages.main.table.settings

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import client.storage.ItemField
import client.storage.StateFlowHolder
import client.ui.pages.edit_data.inputTextField
import client.ui.pages.edit_data.submitButton
import client.ui.pages.util.header
import client.ui.pages.util.labeledRadioButton
import org.kodein.di.compose.LocalDI
import org.kodein.di.instance

@Composable
@Preview
fun filter(){

    val filterValue = remember { mutableStateOf("") }

    val scrollState = rememberScrollState(0)

    val params = ItemField.values().map { it.name }
    val selectedOption = remember { mutableStateOf(params[0]) }

    val di = LocalDI.current
    val stateFlowHolder: StateFlowHolder by di.instance()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            header("Filter")
            Row(
                modifier = Modifier.fillMaxHeight(0.7f)
            ) {
                VerticalScrollbar(
                    adapter = rememberScrollbarAdapter(scrollState),
                    modifier = Modifier.fillMaxHeight()
                )
                Spacer(modifier = Modifier.weight(0.05f))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(scrollState)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
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
                }
                Spacer(modifier = Modifier.weight(0.05f))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    inputTextField(
                        label = "Filter",
                        value = filterValue.value,
                        onValueChange = {
                            filterValue.value = it
                        }
                    )
                }
            }
            submitButton(
                onClick = {
                    val filterType = ItemField.valueOf(selectedOption.value)
                    stateFlowHolder.setFilter(filterType, filterValue.value)
                }
            )
        }
    }
}

@Composable
@Preview
fun filterDialog(isOpen: Boolean, onCloseRequest: () -> Unit){
    if(isOpen){
        Dialog(
            onCloseRequest = onCloseRequest,
            state = rememberDialogState(position = WindowPosition(Alignment.Center),
                width = 600.dp, height = 600.dp),
            resizable = false,
            title = "Update"
        ) {
            filter()
        }
    }
}