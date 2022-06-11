package client.ui.pages.main.map

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import client.ui.pages.main.editItemCursorMenu
import client.ui.theme.*
import share.data.movie.Movie
import share.data.user.User

fun getColorByUser(user: User): Color {
    return ballsColors[user.login.hashCode() % ballsColors.size]
}

@Composable
@Preview
fun tooltipMenu(movie: Movie){
    Surface(
        modifier = Modifier.shadow(4.dp),
        color = tooltipColor,
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            style = hintFont,
            color = onBgPrimary,
            text = """
                               Name: ${movie.name}
                               Genre: ${movie.genre.name}
                               Screenwriter: ${movie.screenwriter.name}
                               Owner: ${movie.user.login}
                               """.trimIndent(),
            modifier = Modifier.padding(5.dp)
        )
    }
}

val ballSize = 50.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun movieBall(movie: Movie, modifier: Modifier = Modifier){
    val isCursorMenuOpen = remember{ mutableStateOf(false)}

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(ballSize + 10.dp)
        ) {
            TooltipArea(
                tooltip = {
                    tooltipMenu(movie)
                },
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(ballSize)
                    .background(
                        color = getColorByUser(movie.user),
                        shape = CircleShape
                    )
                    .mouseClickable {
                        if(buttons.isSecondaryPressed){
                            isCursorMenuOpen.value = true
                        }
                    }
            ) {
                Image(
                    painter = painterResource("clapper.png"),
                    contentDescription = "clapper",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(30.dp)
                )
            }
        }
        Text(
            style = labelFont,
            color = onBgPrimary,
            text = movie.name
        )
    }

    editItemCursorMenu(
        isOpen = isCursorMenuOpen.value,
        currentId = movie.id,
        closeEditMenu = {
            isCursorMenuOpen.value = false
        },
        onDismissRequest = {
            isCursorMenuOpen.value = false
        }
    )
}