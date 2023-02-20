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
    level: Int,
    applePercent: Float
) {
    Surface(
        modifier = Modifier
            .height(88.dp),
        color = Green1,
        elevation = 16.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.height(40.dp),
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
                    progress = applePercent,
                    color = Yellow1,
                    backgroundColor = Green4
                )
                Text(
                    text = (applePercent*100).toString() + "%",
                    textAlign = TextAlign.Center,
                    color = Black
                )
            }
            Text(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .fillMaxWidth(),
                text = "Niv. $level",
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
        GameTopBar(
            level = 1,
            applePercent = 0.5F
        )
    }
}

//endregion