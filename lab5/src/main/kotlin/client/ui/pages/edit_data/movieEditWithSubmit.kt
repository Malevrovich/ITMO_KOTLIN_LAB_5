package client.ui.pages.edit_data

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import client.auth.LoginManager
import client.ui.pages.msgDialog
import client.ui.pages.util.header
import client.ui.theme.*
import org.kodein.di.compose.LocalDI
import org.kodein.di.instance
import share.data.movie.Movie
import share.data.movie.MovieBuilder
import share.data.movie.MovieGenre
import share.data.person.Country


@Composable
@Preview
fun inputTextField(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    isFreezed: Boolean = false,
    onValueChange: (String) -> Unit
){
    TextField(
        label = {
            Text(
                style = textFieldFont,
                color = onBgSecondary,
                text = label,
                modifier = modifier
            )
        },
        modifier = Modifier.background(color = mainBg),
        onValueChange = onValueChange,
        value = value,
        singleLine = true
    )
}

@Composable
@Preview
fun submitButton(onClick: () -> Unit, isFreezed: Boolean = false){
    OutlinedButton(
        onClick = onClick,
        border = BorderStroke((0.5).dp, color = onBgPrimary),
        enabled = !isFreezed
    ){
        Text(
            style = BigButtonFont,
            color = onBgPrimary,
            text = "SUBMIT"
        )
    }
}

@Composable
@Preview
fun addInputField(state: MutableState<String>, label: String, isFreezed: Boolean = false){
    Spacer(modifier = Modifier.heightIn(min=10.dp))
    inputTextField(
        label = label,
        value = state.value,
        onValueChange = {
            state.value = it
        }
    )
}

@Composable
@Preview
fun addGenreField(state: MutableState<String>, isFreezed: Boolean = false){
    Spacer(modifier = Modifier.heightIn(min = 10.dp))
    Text(
        style = labelFont,
        color = onBgPrimary,
        text = "Choose Genre from: ${MovieGenre.values().joinToString(separator = ", ") { it.name }}"
    )
    addInputField(state, "Genre")
}

@Composable
@Preview
fun addCountryField(state: MutableState<String>, isFreezed: Boolean = false){
    Spacer(modifier = Modifier.heightIn(min = 10.dp))
    Text(
        style = labelFont,
        color = onBgPrimary,
        text = "Choose Country from: ${Country.values().joinToString(separator = ", ") { it.name }}"
    )
    addInputField(state, "Country")
}

@Composable
@Preview
fun addBirthdayField(state: MutableState<String>, isFreezed: Boolean = false){
    Spacer(modifier = Modifier.heightIn(min = 10.dp))
    Text(
        style = labelFont,
        color = onBgPrimary,
        text = "Enter Birthday in format HH:mm dd.MM.yyyy"
    )
    addInputField(state, "Birthday")
}

@Composable
@Preview
fun movieEditWithSubmit(header: String, isFreezed: Boolean = false, onSubmit: (Movie) -> Unit){
    val di = LocalDI.current
    val movieBuilder: MovieBuilder by di.instance()
    val loginManager: LoginManager by di.instance()

    val name = remember { mutableStateOf("") }
    val screenwriter = remember { mutableStateOf("") }
    val oscarsCount = remember { mutableStateOf("") }
    val length = remember { mutableStateOf("") }
    val usaBoxOffice = remember { mutableStateOf("") }
    val genre = remember { mutableStateOf("") }
    val country = remember { mutableStateOf("") }
    val birthday = remember { mutableStateOf("") }
    val x = remember { mutableStateOf("") }
    val y = remember { mutableStateOf("") }

    val scrollState = rememberScrollState(0)

    val isMsgOpen = remember { mutableStateOf(false) }
    val msg = remember { mutableStateOf("") }

    msgDialog(
        isOpen = isMsgOpen.value,
        onCloseRequest = {
            isMsgOpen.value = false
        },
        msg = msg.value
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(0.5f).align(Alignment.TopCenter),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
            header(header)

            Row(
                modifier = Modifier.fillMaxHeight(0.8f)
            ) {
                Box(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .weight(40f)
                ) {
                    Column {
                        addInputField(name, "Name", isFreezed)
                        addInputField(screenwriter, "Screenwriter", isFreezed)
                        addInputField(oscarsCount, "Oscars Count", isFreezed)
                        addInputField(length, "Length", isFreezed)
                        addInputField(usaBoxOffice, "Usa Box Office", isFreezed)
                        addGenreField(genre, isFreezed)
                        addCountryField(country, isFreezed)
                        addBirthdayField(birthday, isFreezed)
                        addInputField(x, "X", isFreezed)
                        addInputField(y, "Y", isFreezed)
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                VerticalScrollbar(
                    modifier = Modifier.fillMaxHeight().weight(2f),
                    adapter = rememberScrollbarAdapter(scrollState)
                )
            }

            Spacer(modifier = Modifier.size(5.dp))
            submitButton(
                onClick = {
                    val movie = try {
                        movieBuilder.setStrings(
                            name.value,
                            screenwriter.value,
                            oscarsCount.value,
                            length.value,
                            usaBoxOffice.value,
                            genre.value,
                            country.value,
                            birthday.value,
                            x.value,
                            y.value
                        ).setUser(loginManager.getCurrentSession()).build()
                    } catch (e: IllegalArgumentException){
                        msg.value = e.message.toString()
                        isMsgOpen.value = true
                        return@submitButton
                    }

                    onSubmit(movie)
                }, isFreezed)
        }
    }
}