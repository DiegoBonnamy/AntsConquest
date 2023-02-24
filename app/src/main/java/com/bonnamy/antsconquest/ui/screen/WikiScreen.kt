package com.bonnamy.antsconquest.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bonnamy.antsconquest.R
import com.bonnamy.antsconquest.model.AntType
import com.bonnamy.antsconquest.ui.component.GameBottomBar
import com.bonnamy.antsconquest.ui.component.GameTopBar
import com.bonnamy.antsconquest.ui.theme.AntsConquestTheme
import com.bonnamy.antsconquest.ui.theme.Green3
import com.bonnamy.antsconquest.ui.theme.Green4
import com.bonnamy.antsconquest.ui.uistate.AntUiState
import com.bonnamy.antsconquest.ui.uistate.ResourcesRequiredUiState
import com.bonnamy.antsconquest.viewmodel.HomeViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun WikiScreen(
    onBottomBarItemClick: (Int) -> Unit
) {
    val viewModel: HomeViewModel = viewModel()

    val gameData by viewModel.gameData.observeAsState()

    WikiContent(
        level = gameData?.level ?: 1,
        applePercent = gameData?.applePercent() ?: 0F,
        onBottomBarItemClick = onBottomBarItemClick,
        ants = gameData?.ants ?: persistentListOf()
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WikiContent(
    level: Int,
    applePercent: Float,
    onBottomBarItemClick: (Int) -> Unit,
    ants: ImmutableList<AntUiState>
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .blur(
                            radiusX = 10.dp,
                            radiusY = 10.dp
                        ),
                    painter = painterResource(id = R.drawable.app_anthill_background),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
            }
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                contentPadding = PaddingValues(32.dp)
            ) {
                items(ants) { ant ->
                    AntWikiItem(
                        antImage = ant.image,
                        antName = ant.name,
                        onAntClick = {} // TODO
                    )
                }
            }
        }
    }
}

@Composable
fun AntWikiItem(
    antImage: Int,
    antName: String,
    onAntClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(16.dp)
            .clickable {
                onAntClick()
            },
        color = Green3
    ) {
        Column(
            modifier = Modifier
                .width(100.dp)
                .height(200.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = antImage),
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(4.dp),
                    text = antName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
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
            onBottomBarItemClick = {},
            ants = persistentListOf(
                AntUiState(40,20,10,10,20,0,true, 0, AntType.OUVRIERE, "Fourmi Ouvrière", R.drawable.ant_worker, 0, ResourcesRequiredUiState(5,0,0,0), lore = null),
                AntUiState(80,40,25,30,50,30,true, 2, AntType.LANCIERE, "Fourmi Lancière des Cieux", R.drawable.ant_sky_spear, 0, ResourcesRequiredUiState(10,0,0,0), lore = null),
                AntUiState(60,40,30,30,20,40,true, 3, AntType.CAVERNES, "Fourmi des cavernes", R.drawable.ant_cave, 0, ResourcesRequiredUiState(20,3,0,0), lore = null),
                AntUiState(60,60,30,60,90,40,true, 4, AntType.FURTIVE, "Lame Furtive", R.drawable.ant_stealth_blade, 0, ResourcesRequiredUiState(40,9,2,0), lore = null)
            )
        )
    }
}

@Preview
@Composable
fun AntWikiPreview() {
    AntsConquestTheme {
        AntWikiItem(
            antName = "Fourmi Ouvrière",
            antImage = R.drawable.ant_worker,
            onAntClick = {}
        )
    }
}

//endregion