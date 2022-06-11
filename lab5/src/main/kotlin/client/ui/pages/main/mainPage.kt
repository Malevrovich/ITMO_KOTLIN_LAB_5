package client.ui.pages.main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import client.ui.pages.main.map.mapContent
import client.ui.pages.main.table.tableContent

@Composable
@Preview
fun mainPage(onExitClick: () -> Unit = {}){
    val isTable = remember { mutableStateOf(true)}

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.75f)
                .align(Alignment.CenterEnd)
        ) {
            if (isTable.value) tableContent() else mapContent()
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.25f)
                .align(Alignment.CenterStart)
        ) {
            sideBar(
                onTableClick = {
                    isTable.value = true
                },
                onMapClick = {
                    isTable.value = false
                },
                onExitClick = onExitClick
            )
        }
    }
}