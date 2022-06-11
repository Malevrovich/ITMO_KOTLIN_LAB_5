package client.ui.pages.auth

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import client.auth.LoginManager
import client.ui.localizationBlock
import client.ui.pages.msgDialog
import client.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.compose.LocalDI
import org.kodein.di.instance
import share.localization.Localization

@Composable
@Preview
fun logo(){
    Image(
        painter = painterResource("logo.png"),
        contentDescription = "logo",
        modifier = Modifier.fillMaxWidth(0.5f)
    )
}

@Composable
@Preview
fun loginField(
    login: String, onLoginChange: (String) -> Unit,
    password: String, onPasswordChange: (String) -> Unit,
    freeze: Boolean = false,
    modifier: Modifier = Modifier
){
    val di = LocalDI.current
    val localization: Localization by di.instance()

    Column (
        modifier = modifier.fillMaxWidth(0.75f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            label = {
                Text(
                    text = localization.getState("login").value,
                    style = textFieldFont,
                    color = onBgSecondary
                )
            },
            modifier = Modifier.background(color = bgLightGrey),
            onValueChange = onLoginChange,
            value = login,
            singleLine = true,
            enabled = !freeze
        )
        Spacer(
            modifier = Modifier.fillMaxHeight(0.05f)
        )
        TextField(
            label = {
                Text(
                    text = localization.getState("password").value,
                    style = textFieldFont,
                    color = onBgSecondary
                )
            },
            modifier = Modifier.background(color = bgLightGrey),
            onValueChange = onPasswordChange,
            value = password,
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            enabled = !freeze
        )
    }
}

@Composable
@Preview
fun signButton(text: String, onClick: () -> Unit, freeze: Boolean = false, modifier: Modifier = Modifier){
    Surface(
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke((0.5).dp, color = onBgPrimary),
        modifier = modifier,
        color = bgGrey
    ) {
        TextButton(
            onClick = onClick,
            enabled = !freeze
        ) {
            Text(
                style = smallButtonFont,
                color = onBgPrimary,
                text = text
            )
        }
    }

}

@Composable
@Preview
fun buttons(
    onSignInClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    freeze: Boolean = false,
    modifier: Modifier = Modifier
) {
    val di = LocalDI.current
    val localization: Localization by di.instance()

    Row(
        modifier = modifier
    ){
        signButton(localization.getState("sign_in").value, onSignInClick, freeze, Modifier.weight(2f))
        Spacer(modifier = Modifier.weight(1f))
        signButton(localization.getState("sign_up").value, onSignUpClick, freeze, Modifier.weight(3f))
    }
}

@Composable
@Preview
fun authPage(onSuccess: () -> Unit){
    val di = LocalDI.current
    val loginManager: LoginManager by di.instance()

    val freeze = remember{ mutableStateOf(false) }

    val (login, onLoginChange) = remember { mutableStateOf("") }
    val (password, onPasswordChange) = remember { mutableStateOf("") }

    val error = remember { mutableStateOf(false) }
    val msg = remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    msgDialog(
        isOpen = error.value,
        onCloseRequest = {
            error.value = false
        },
        msg = msg.value
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.33f)
                .background(color = bgGrey)
                .align(Alignment.Center)
        ) {
            Box(
                Modifier.align(Alignment.TopEnd).padding(10.dp).fillMaxWidth(0.4f).fillMaxHeight(0.075f)
            ) {
                localizationBlock()
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
            ) {
                Spacer(
                    modifier = Modifier.fillMaxHeight(0.1f)
                )

                logo()

                Spacer(
                    modifier = Modifier.fillMaxHeight(0.1f)
                )

                loginField(login, onLoginChange, password, onPasswordChange)

                Spacer(
                    modifier = Modifier.fillMaxHeight(0.2f)
                )

                buttons(
                    modifier = Modifier.fillMaxWidth(0.75f),
                    freeze = freeze.value,
                    onSignInClick = {
                        freeze.value = true
                        scope.launch(Dispatchers.Default) {
                            try {
                                loginManager.signIn(login, password)
                            } catch (e: IllegalArgumentException) {
                                msg.value = e.message.toString()
                                error.value = true
                                return@launch
                            }
                            onSuccess()
                        }.invokeOnCompletion {
                            freeze.value = false
                        }
                    },
                    onSignUpClick = {
                        freeze.value = true
                        scope.launch(Dispatchers.Default) {
                            try {
                                loginManager.signUp(login, password)
                            } catch (e: IllegalArgumentException) {
                                msg.value = e.message.toString()
                                error.value = true
                                return@launch
                            }
                            onSuccess()
                        }.invokeOnCompletion {
                            freeze.value = false
                        }
                    }
                )
            }
        }
    }
}