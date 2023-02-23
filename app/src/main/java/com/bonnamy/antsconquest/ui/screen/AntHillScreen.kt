package com.bonnamy.antsconquest.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bonnamy.antsconquest.R
import com.bonnamy.antsconquest.model.ResourceType
import com.bonnamy.antsconquest.ui.component.GameBottomBar
import com.bonnamy.antsconquest.ui.component.GameTopBar
import com.bonnamy.antsconquest.ui.theme.AntsConquestTheme
import com.bonnamy.antsconquest.ui.theme.Black
import com.bonnamy.antsconquest.ui.theme.Green4
import com.bonnamy.antsconquest.ui.theme.Yellow1
import com.bonnamy.antsconquest.ui.uistate.ResourceUiState
import com.bonnamy.antsconquest.viewmodel.HomeViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun AntHillScreen(
    onBottomBarItemClick: (Int) -> Unit
) {
    val viewModel: HomeViewModel = viewModel()

    val gameData by viewModel.gameData.observeAsState()

    AntHillContent(
        level = gameData?.level ?: 1,
        applePercent = gameData?.applePercent() ?: 0F,
        onBottomBarItemClick = onBottomBarItemClick,
        resources = gameData?.resources ?: persistentListOf()
    )
}

@Composable
fun AntHillContent(
    level: Int,
    applePercent: Float,
    onBottomBarItemClick: (Int) -> Unit,
    resources: ImmutableList<ResourceUiState>
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
            Column {
                LazyColumn {
                    items(resources) { resource ->
                        ResourceItem(
                            resourceImage = resource.image,
                            resourceCount = resource.count,
                            resourceMax = resource.storage
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ResourceItem(
    resourceImage: Int,
    resourceCount: Int,
    resourceMax: Int,
) {
    val resourcePercent = resourceCount.toFloat()/resourceMax

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            modifier = Modifier.width(64.dp),
            painter = painterResource(id = resourceImage),
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
                progress = resourcePercent,
                color = Yellow1,
                backgroundColor = Green4
            )
            Text(
                text = (resourcePercent*100).toString() + "%",
                textAlign = TextAlign.Center,
                color = Black
            )
        }
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "$resourceCount / $resourceMax"
        )
    }
}

@Composable
fun UpgradeContent(

) {

}

//region Previews

@Preview
@Composable
fun AntHillContentPreview() {
    AntsConquestTheme {
        AntHillContent(
            level = 1,
            applePercent = 0.5F,
            onBottomBarItemClick = {},
            resources = persistentListOf(
                ResourceUiState("Pomme", ResourceType.APPLE, 10, 100, R.drawable.apple_without_bg),
                ResourceUiState("Terre", ResourceType.DIRT, 0, 100, R.drawable.dirt_without_bg),
                ResourceUiState("Feuille", ResourceType.LEAF, 0, 100, R.drawable.leaf_without_bg),
                ResourceUiState("Champignon", ResourceType.MUSHROOM, 0, 100, R.drawable.mushroom_without_bg),
                ResourceUiState("Pierre", ResourceType.ROCK, 0, 100, R.drawable.rock_without_bg),
                ResourceUiState("Métal", ResourceType.METAL, 0, 100, R.drawable.metal_without_bg)
            )
        )
    }
}

@Preview
@Composable
fun ResourceItemPreview() {
    AntsConquestTheme {
        ResourceItem(
            resourceImage = R.drawable.apple_without_bg,
            resourceCount = 5,
            resourceMax = 100
        )
    }
}

@Preview
@Composable
fun UpgradeContentPreview() {
    AntsConquestTheme {
        UpgradeContent()
    }
}

//endregion