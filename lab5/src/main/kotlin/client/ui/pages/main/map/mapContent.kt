package client.ui.pages.main.map

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import client.storage.StateFlowHolder
import client.ui.pages.main.map
import client.ui.theme.headerFont
import client.ui.theme.mainBg
import client.ui.theme.onBgPrimary
import org.kodein.di.compose.LocalDI
import org.kodein.di.instance


@Composable
@Preview
fun mapContent(){
    val di = LocalDI.current
    val stateHolder: StateFlowHolder by di.instance()

    val data = stateHolder.getState().collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth(0.86f)
                .fillMaxHeight(0.80f)
                .align(Alignment.Center)
        ){
            Box(
                modifier = Modifier.fillMaxHeight(0.875f).fillMaxWidth().align(Alignment.BottomCenter)
            ){
                map(data.value)
            }
            Text(
                style = headerFont,
                color = onBgPrimary,
                text = "Map",
                modifier = Modifier
                    .fillMaxHeight(0.125f)
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .background(color = mainBg)
            )
        }
        Surface(
            color = mainBg,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.07f)
                .align(Alignment.CenterEnd)
        ) {}
        Surface(
            color = mainBg,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.07f)
                .align(Alignment.CenterStart)
        ) {}
        Surface(
            color = mainBg,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.10f)
                .align(Alignment.BottomCenter)
        ) {}
        Surface(
            color = mainBg,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.10f)
                .align(Alignment.TopCenter)
        ) {}
    }
}