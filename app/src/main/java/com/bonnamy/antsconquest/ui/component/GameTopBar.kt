package com.bonnamy.antsconquest.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bonnamy.antsconquest.R
import com.bonnamy.antsconquest.ui.theme.*

@Composable
fun GameTopBar(

) {
    Surface(
        modifier = Modifier.height(48.dp),
        color = Green1
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.height(32.dp),
                painter = painterResource(id = R.drawable.apple_without_bg),
                contentDescription = null
            )
            Box(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth(0.5F),
                contentAlignment = Alignment.Center
            ) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .height(16.dp),
                    progress = 0.5F,
                    color = Yellow1,
                    backgroundColor = Green4
                )
                Text(
                    text = "50%",
                    textAlign = TextAlign.Center,
                    color = Black
                )
            }
            Text(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .fillMaxWidth(),
                text = "Niv. 1",
                color = Black,
                textAlign = TextAlign.End
            )
        }
    }
}

//region Previews

@Preview
@Composable
fun GameTopBarPreview() {
    AntsConquestTheme {
        GameTopBar()
    }
}

//endregion