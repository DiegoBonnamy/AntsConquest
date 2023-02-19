package com.bonnamy.antsconquest.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.bonnamy.antsconquest.ui.theme.AntsConquestTheme
import com.bonnamy.antsconquest.ui.theme.Black
import com.bonnamy.antsconquest.ui.theme.Brown1

@Composable
fun GameButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    backgroundColor: Color,
    text: String? = null,
    textColor: Color? = null,
    content: @Composable (RowScope.() -> Unit) = {
        Text(
            text = text ?: "",
            color = textColor ?: Black
        )
    }
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        content = content
    )
}

//region Previews

@Preview
@Composable
fun GameButtonPreview() {
    AntsConquestTheme {
        GameButton(
            onClick = { },
            backgroundColor = Brown1,
            text = "Create",
            textColor = Color.White
        )
    }
}

//endregion