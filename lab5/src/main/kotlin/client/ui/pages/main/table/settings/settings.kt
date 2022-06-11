package client.ui.pages.main.table

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import client.ui.theme.*

@Composable
fun settingsButton(text: String, onClick: () -> Unit){
    TextButton(
        onClick = onClick
    ) {
        Text(
            style = ButtonFont,
            color = onBgPrimary,
            text = text
        )
    }
}

@Composable
@Preview
fun Settings(
    onEditOrderClick: () -> Unit = {},
    onEditFiltersClick: () -> Unit = {},
    onAddClick: () -> Unit = {}
){
    Box(
        modifier = Modifier.clip(shape = RoundedCornerShape(30.dp))
            .background(color = bgLightGrey)
            .fillMaxSize()
    ){
        Text(
            style = secondaryFont,
            color = onBgSecondary,
            text = "Settings",
            modifier = Modifier.align(Alignment.TopStart).padding(start = 30.dp, top = 10.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().padding(bottom = 20.dp)
        ){
            settingsButton("Edit Order", onEditOrderClick)

            settingsButton("Edit Filters", onEditFiltersClick)

            settingsButton("Add", onAddClick)
        }
    }
}
