package client.ui.pages.main.table

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import client.storage.StateFlowHolder
import client.ui.pages.edit_data.add.addDialog
import client.ui.pages.main.table.settings.filterDialog
import client.ui.pages.main.table.settings.orderDialog
import org.kodein.di.compose.LocalDI
import org.kodein.di.instance

@Composable
@Preview
fun tableContent(){
    val di = LocalDI.current

    val stateHolder: StateFlowHolder by di.instance()
    val data = stateHolder.getState().collectAsState()

    val isAddDialogOpen = remember { mutableStateOf(false) }
    val isOrderDialogOpen = remember { mutableStateOf(false) }
    val isFilterDialogOpen = remember { mutableStateOf(false) }

    addDialog(
        isOpen = isAddDialogOpen.value,
        onCloseRequest = {
            isAddDialogOpen.value = false
        }
    )

    orderDialog(
        isOpen = isOrderDialogOpen.value,
        onCloseRequest = {
            isOrderDialogOpen.value = false
        }
    )

    filterDialog(
        isOpen = isFilterDialogOpen.value,
        onCloseRequest = {
            isFilterDialogOpen.value = false
        }
    )

    Column(modifier = Modifier.padding(horizontal = 50.dp, vertical = 75.dp)){
        Box(modifier = Modifier.weight(4f)){
            Settings(
                onAddClick = {
                    isAddDialogOpen.value = true
                },
                onEditOrderClick = {
                    isOrderDialogOpen.value = true
                },
                onEditFiltersClick = {
                    isFilterDialogOpen.value = true
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(modifier = Modifier.weight(12f)) {
            table(data.value)
        }
    }
}