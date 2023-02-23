package com.bonnamy.antsconquest.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bonnamy.antsconquest.R
import com.bonnamy.antsconquest.ui.component.GameBottomBar
import com.bonnamy.antsconquest.ui.component.GameTopBar
import com.bonnamy.antsconquest.ui.theme.AntsConquestTheme
import com.bonnamy.antsconquest.ui.theme.Green4
import com.bonnamy.antsconquest.viewmodel.HomeViewModel

@Composable
fun WikiScreen(
    onBottomBarItemClick: (Int) -> Unit
) {
    val viewModel: HomeViewModel = viewModel()

    val gameData by viewModel.gameData.observeAsState()

    WikiContent(
        level = gameData?.level ?: 1,
        applePercent = gameData?.applePercent() ?: 0F,
        onBottomBarItemClick = onBottomBarItemClick
    )
}

@Composable
fun WikiContent(
    level: Int,
    applePercent: Float,
    onBottomBarItemClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            GameTopBar(
                level = level,
                applePercent = applePercent
            )
        },
        bottomBar = {
            GameBottomBar(
                onItemClick = onBottomBarItemClick
            )
        }
    ) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            color = Green4
        ) {
            Column(
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(id = R.drawable.app_anthill_background),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}

//region Previews

@Preview
@Composable
fun WikiContentPreview() {
    AntsConquestTheme {
        WikiContent(
            level = 1,
            applePercent = 0.5F,
            onBottomBarItemClick = {}
        )
    }
}

//endregion