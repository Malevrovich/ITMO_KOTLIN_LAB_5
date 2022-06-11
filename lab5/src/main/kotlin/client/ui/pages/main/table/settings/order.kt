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
import client.ui.pages.edit_data.submitButton
import client.ui.pages.util.header
import client.ui.pages.util.labeledRadioButton
import org.kodein.di.compose.LocalDI
import org.kodein.di.instance


@Composable
@Preview
fun order(){
    val scrollState = rememberScrollState(0)

    val params = ItemField.values().map { it.name }

    val selectedOption = remember { mutableStateOf(params[0] + " ASC") }

    val di = LocalDI.current
    val stateFlowHolder: StateFlowHolder by di.instance()

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .align(Alignment.TopCenter)
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
            header("Order")

            Row(
                modifier = Modifier.fillMaxHeight(0.7f)
            ){
                Box(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .weight(40f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        params.forEach { text ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                labeledRadioButton(
                                    label = text + " ASC",
                                    selected = (selectedOption.value == text + " ASC"),
                                    onClick = {
                                        selectedOption.value = text + " ASC"
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                                labeledRadioButton(
                                    label = text + " DESC",
                                    selected = (selectedOption.value == text + " DESC"),
                                    onClick = {
                                        selectedOption.value = text + " DESC"
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                VerticalScrollbar(
                    adapter = rememberScrollbarAdapter(scrollState),
                    modifier = Modifier.weight(2f)
                )
            }
            submitButton(
                onClick = {
                    val (orderType, ascending) = selectedOption.value.split(" ")
                    stateFlowHolder.setOrder(ItemField.valueOf(orderType), ascending == "ASC")
                }
            )
        }
    }
}

@Composable
@Preview
fun orderDialog(isOpen: Boolean, onCloseRequest: () -> Unit){
    if(isOpen){
        Dialog(
            onCloseRequest = onCloseRequest,
            state = rememberDialogState(position = WindowPosition(Alignment.Center),
                width = 800.dp, height = 600.dp),
            resizable = false,
            title = "Edit Order"
        ) {
            order()
        }
    }
}