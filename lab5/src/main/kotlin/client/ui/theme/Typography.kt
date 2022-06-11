package client.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp

private val Inter = FontFamily(
    Font("fonts/regular_inter.ttf", FontWeight.Normal),
    Font("fonts/thin_inter.ttf", FontWeight.Thin),
    Font("fonts/bold_inter.ttf", FontWeight.Bold),
    Font("fonts/extra_light_inter.ttf", FontWeight.ExtraLight),
    Font("fonts/extra_light_italic_inter.ttf", FontWeight.ExtraLight, FontStyle.Italic)
)

private val BadScript = FontFamily(
    Font("fonts/bad_script.ttf")
)

val logoFont = TextStyle(
    fontFamily = BadScript,
    fontSize = 56.sp
)

val BigButtonFont = TextStyle(
    fontFamily = Inter,
    fontWeight = FontWeight.Thin,
    fontSize = 30.sp
)

val ButtonFont = TextStyle(
    fontFamily = Inter,
    fontWeight = FontWeight.Thin,
    fontSize = 24.sp
)

val headerFont = TextStyle(
    fontFamily = Inter,
    fontWeight = FontWeight.ExtraLight,
    fontSize = 36.sp
)

val contentFont = TextStyle(
    fontFamily = Inter,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
)

val secondaryFont = TextStyle(
    fontFamily = Inter,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
)

val labelFont = TextStyle(
    fontFamily = Inter,
    fontWeight = FontWeight.ExtraLight,
    fontSize = 16.sp
)

val hintFont = TextStyle(
    fontFamily = Inter,
    fontWeight = FontWeight.ExtraLight,
    fontStyle = FontStyle.Italic,
    fontSize = 14.sp
)

val textFieldFont = TextStyle(
    fontFamily = Inter,
    fontWeight = FontWeight.ExtraLight,
    fontStyle = FontStyle.Italic
)

val smallButtonFont = TextStyle(
    fontFamily = Inter,
    fontWeight = FontWeight.ExtraLight,
    fontSize = 16.sp
)