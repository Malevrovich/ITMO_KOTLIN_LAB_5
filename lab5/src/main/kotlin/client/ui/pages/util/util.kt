package client.ui.pages.util

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import client.ui.theme.headerFont
import client.ui.theme.labelFont
import client.ui.theme.onBgPrimary


@Composable
@Preview
fun header(name: String){
    Text(
        style = headerFont,
        color = onBgPrimary,
        text = name
    )
}


@Composable
@Preview
fun labeledRadioButton(label: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .selectable(
                selected = selected,
                onClick = onClick
            )
    ) {
        Text(
            style = labelFont,
            color = onBgPrimary,
            text = label,
            modifier = Modifier.align(Alignment.CenterStart)
        )
        RadioButton(
            selected = selected,
            onClick = onClick,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}