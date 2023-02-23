package com.bonnamy.antsconquest.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bonnamy.antsconquest.R
import com.bonnamy.antsconquest.ui.theme.AntsConquestTheme
import com.bonnamy.antsconquest.ui.theme.Green1

@Composable
fun GameBottomBar(
    onItemClick: (Int) -> Unit
) {
    Surface(
        color = Green1,
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 48.dp)
                .height(48.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable {
                         onItemClick(1)
                    },
                painter = painterResource(id = R.drawable.ic_compass),
                contentDescription = null
            )
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable {
                        onItemClick(2)
                    },
                painter = painterResource(id = R.drawable.ic_anthill),
                contentDescription = null
            )
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable {
                        onItemClick(3)
                    },
                painter = painterResource(id = R.drawable.ic_ant),
                contentDescription = null
            )
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable {
                        onItemClick(4)
                    },
                painter = painterResource(id = R.drawable.ic_attack),
                contentDescription = null
            )
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable {
                        onItemClick(5)
                    },
                painter = painterResource(id = R.drawable.ic_book),
                contentDescription = null
            )
        }
    }
}

//region Previews

@Preview
@Composable
fun GameBottomBarPreview() {
    AntsConquestTheme {
        GameBottomBar(
            onItemClick = {}
        )
    }
}

//endregion