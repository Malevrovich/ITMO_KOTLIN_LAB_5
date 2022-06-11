package client.ui.pages.main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.material.CursorDropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import client.ui.pages.edit_data.add.addDialog
import client.ui.pages.edit_data.remove.removeDialog
import client.ui.pages.edit_data.update.updateDialog
import client.ui.theme.hintFont
import client.ui.theme.tooltipColor

@Composable
@Preview
fun cursorMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onUpdateClick: () -> Unit = {},
    onRemoveClick: () -> Unit = {}
){
    CursorDropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier.background(color = tooltipColor)
    ) {
        DropdownMenuItem(onAddClick){
            Text(
                style = hintFont,
                text = "Add"
            )
        }
        DropdownMenuItem(onUpdateClick){
            Text(
                style = hintFont,
                text = "Update"
            )
        }
        DropdownMenuItem(onRemoveClick){
            Text(
                style = hintFont,
                text = "Remove"
            )
        }
    }
}


@Composable
@Preview
fun editItemCursorMenu(isOpen: Boolean, currentId: Int, closeEditMenu: () -> Unit, onDismissRequest: () -> Unit){
    val isUpdateDialogOpen = remember { mutableStateOf(false) }
    val isAddDialogOpen = remember { mutableStateOf(false) }
    val isRemoveDialogOpen = remember { mutableStateOf(false) }

    cursorMenu(
        expanded = isOpen,
        onDismissRequest = onDismissRequest,
        onUpdateClick = {
            isUpdateDialogOpen.value = true
        },
        onAddClick = {
            isAddDialogOpen.value = true
        },
        onRemoveClick = {
            isRemoveDialogOpen.value = true
        }
    )

    updateDialog(
        isOpen = isUpdateDialogOpen.value,
        id = currentId,
        onCloseRequest = {
            isUpdateDialogOpen.value = false
        }
    )

    addDialog(
        isOpen = isAddDialogOpen.value,
        onCloseRequest = {
            isAddDialogOpen.value = false
        }
    )

    removeDialog(
        isOpen = isRemoveDialogOpen.value,
        id = currentId,
        onCloseRequest = {
            isRemoveDialogOpen.value = false
        },
        onSuccess = {
            isRemoveDialogOpen.value = false
            closeEditMenu()
        }
    )
}