package client.ui.pages.main.table

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import client.ui.pages.main.editItemCursorMenu
import client.ui.theme.contentFont
import client.ui.theme.onBgPrimary
import share.data.movie.Movie

@Composable
fun headerCell(text: String, modifier: Modifier = Modifier){
    Box(
        modifier = modifier.border(width = 1.dp, color = onBgPrimary).heightIn(min=60.dp)
    ) {
        Text(
            style = contentFont,
            color = onBgPrimary,
            fontWeight = FontWeight.Bold,
            text = text,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun header(){
    Row {
        headerCell("Id", Modifier.weight(2f))
        headerCell("Name", Modifier.weight(6f))
        headerCell("Genre", Modifier.weight(4f))
        headerCell("Oscars", Modifier.weight(3f))
        headerCell("Box Office", Modifier.weight(3.3f))
        headerCell("Length", Modifier.weight(3f))
        headerCell("X", Modifier.weight(3f))
        headerCell("Y", Modifier.weight(3f))
        headerCell("Screenwriter", Modifier.weight(6f))
        headerCell("Country", Modifier.weight(4f))
    }
}

@Composable
fun cell(text: Any, modifier: Modifier){
    Box(
        modifier = modifier
            .border(width = 1.dp, color = onBgPrimary)
            .heightIn(min = 60.dp)
    ) {
        Text(
            style = contentFont,
            color = onBgPrimary,
            text = text.toString(),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun movieRow(movie: Movie, onSecondaryClick: () -> Unit = {}){
    Row(
        modifier = Modifier
            .mouseClickable{
                if(buttons.isSecondaryPressed){
                    onSecondaryClick()
                }
            }
    ){
        with(movie){
            cell(id, Modifier.weight(2f))
            cell(name, Modifier.weight(6f))
            cell(genre, Modifier.weight(4f))
            cell(oscarsCount, Modifier.weight(3f))
            cell(usaBoxOffice, Modifier.weight(3.3f))
            cell(length, Modifier.weight(3f))
            cell(coordinates.x, Modifier.weight(3f))
            cell(coordinates.y, Modifier.weight(3f))
            cell(screenwriter.name, Modifier.weight(6f))
            cell(screenwriter.nationality?.name ?: "-", Modifier.weight(4f))
        }
    }
}

@Composable
@Preview
fun table(data: List<Movie>) {

    val isCursorMenuOpen = remember { mutableStateOf(false) }
    val currentId = remember { mutableStateOf(0) }

    val scrollingState = rememberLazyListState()
    Row {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            header()
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = scrollingState
            ) {
                items(data) {
                    movieRow(
                        it,
                        onSecondaryClick = {
                            isCursorMenuOpen.value = true
                            currentId.value = it.id
                        }
                    )
                }
            }
        }
        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(
                scrollState = scrollingState
            )
        )
    }

    editItemCursorMenu(
        isOpen = isCursorMenuOpen.value,
        currentId = currentId.value,
        closeEditMenu = {
            isCursorMenuOpen.value = false
        },
        onDismissRequest = {
            isCursorMenuOpen.value = false
        }
    )
}
