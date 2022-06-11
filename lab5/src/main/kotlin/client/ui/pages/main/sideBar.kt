package client.ui.pages.main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import client.auth.LoginManager
import client.ui.theme.*
import org.kodein.di.compose.LocalDI
import org.kodein.di.instance

@Composable
@Preview
fun userInfo(){
    val di = LocalDI.current
    val loginManager: LoginManager by di.instance()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(top=50.dp)
    ) {
        Image(
            painter = painterResource("avatar.png"),
            contentDescription = "avatar",
            modifier = Modifier.sizeIn(maxWidth = 128.dp).weight(5f)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            style = contentFont,
            text = loginManager.getCurrentSession().login,
            fontSize = 20.sp,
            modifier = Modifier.weight(5f)
        )
    }
}

@Composable
fun menuDivider(){
    Row{
        Spacer(modifier = Modifier.weight(2f))
        Divider(
            thickness = 1.dp,
            color = onBgSecondary,
            modifier = Modifier.weight(5f).padding(vertical = 10.dp)
        )
        Spacer(modifier = Modifier.weight(2f))
    }
}

@Composable
@Preview
fun showMenu(onTableClick: () -> Unit = {},
             onMapClick: () -> Unit = {},
             onExitClick: () -> Unit = {}){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally) {
        TextButton(onClick = onTableClick) {
            Text(
                style = BigButtonFont,
                text = "Table",
                color = onBgPrimary
            )
        }

        menuDivider()

        TextButton(onClick = onMapClick){
            Text(
                style = BigButtonFont,
                text = "Map",
                color = onBgPrimary
            )
        }

        menuDivider()

        TextButton(onClick = onExitClick){
            Text(
                style = BigButtonFont,
                text = "Exit",
                color = exitColor
            )
        }
    }
}

@Composable
@Preview
fun sideBar(onTableClick: () -> Unit = {},
            onMapClick: () -> Unit = {},
            onExitClick: () -> Unit = {}){
    Surface(
        color = bgGrey,
        modifier = Modifier.fillMaxWidth()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.weight(3f),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Box(modifier = Modifier.weight(3f)){
                    userInfo()
                }

                Box(modifier = Modifier.weight(2f)) {
                    Divider(
                        thickness = 1.dp,
                        color = onBgSecondary,
                        modifier = Modifier.padding(horizontal = 30.dp).align(Alignment.TopCenter)
                    )
                }
            }
            Box(modifier = Modifier.weight(3f)) {
                showMenu(onTableClick, onMapClick, onExitClick)
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}