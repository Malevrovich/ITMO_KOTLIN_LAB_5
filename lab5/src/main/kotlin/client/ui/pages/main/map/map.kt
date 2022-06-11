package client.ui.pages.main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import client.ui.pages.main.map.ballSize
import client.ui.pages.main.map.movieBall
import client.ui.theme.bgLightGrey
import client.ui.theme.onBgPrimary
import client.ui.theme.onBgSecondary
import client.ui.theme.secondaryFont
import share.data.coordinates.CoordinatesBuilderImpl
import share.data.movie.Movie
import share.data.movie.MovieBuilderImpl
import share.data.person.PersonBuilderImpl
import share.data.user.UserBuilderImpl


@Composable
@Preview
fun Axes(startX: Float, startY: Float){
    Column(
        modifier = Modifier.offset(y = 100.dp)
    ) {
        Divider(
            thickness = 2.dp,
            color = onBgPrimary
        )
        Text(
            style = secondaryFont,
            fontSize = 12.sp,
            color = onBgSecondary,
            text = "y = $startY"
        )
    }

    Row(
        modifier = Modifier.offset(x = 100.dp)
    ) {
        Divider(
            color = onBgPrimary,
            modifier = Modifier.width(2.dp).fillMaxHeight()
        )
        Text(
            style = secondaryFont,
            fontSize = 12.sp,
            color = onBgSecondary,
            text = "x = $startX"
        )
    }
}

fun movieIn(x: Float, y: Int): Movie{
    val movieBuilder = MovieBuilderImpl(
        PersonBuilderImpl(),
        CoordinatesBuilderImpl(),
        UserBuilderImpl()
    )
    val coordinatesBuilder = CoordinatesBuilderImpl()

    return movieBuilder.setDefault().setCoordinates(
        coordinatesBuilder.setX(x).setY(y).build()
    ).build()
}

@Composable
@Preview
fun map(data: List<Movie>){
    val startX = remember { mutableStateOf( 0f) }
    val startY = remember { mutableStateOf( 0f) }

    val leftBorder = startX.value.dp - 100.dp
    val rightBorder = startX.value.dp + 1000.dp

    val topBorder = startY.value.dp + 100.dp
    val bottomBorder = startY.value.dp - 1000.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .widthIn(max = rightBorder - leftBorder)
            .heightIn(max = topBorder - bottomBorder)
            .background(color = bgLightGrey)
            .pointerInput(Unit){
                detectDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
                    startX.value -= dragAmount.x * 0.5f
                    startY.value += dragAmount.y * 0.5f
                }
            }
    ) {
        for(movie in data.filter {
            (leftBorder - 100.dp <= it.coordinates.x.dp) and (it.coordinates.x.dp <= rightBorder) and
                    (bottomBorder <= it.coordinates.y.dp) and (it.coordinates.y.dp <= topBorder + 100.dp)
        }){
            movieBall(
                movie, Modifier
                    .offset(
                        x = movie.coordinates.x.dp - ballSize / 2 - leftBorder,
                        y = topBorder - ballSize / 2 - movie.coordinates.y.dp
                    )
            )
        }

        Axes(startX.value, startY.value)
    }
}
