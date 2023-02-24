package com.bonnamy.antsconquest.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bonnamy.antsconquest.R
import com.bonnamy.antsconquest.model.ResourceType
import com.bonnamy.antsconquest.ui.component.GameBottomBar
import com.bonnamy.antsconquest.ui.component.GameButton
import com.bonnamy.antsconquest.ui.component.GameTopBar
import com.bonnamy.antsconquest.ui.theme.*
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
        resources = gameData?.resources ?: persistentListOf(),
        onUpgradeClick = { }, // TODO
        requiredDirt = 10, // TODO
        requiredRock = 10 // TODO
    )
}

@Composable
fun AntHillContent(
    level: Int,
    applePercent: Float,
    onBottomBarItemClick: (Int) -> Unit,
    resources: ImmutableList<ResourceUiState>,
    onUpgradeClick: () -> Unit,
    requiredDirt: Int,
    requiredRock: Int
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
            Column(
                verticalArrangement = Arrangement.Bottom
            ) {
                LazyColumn {
                    items(resources) { resource ->
                        ResourceItem(
                            resourceImage = resource.image,
                            resourceCount = resource.count,
                            resourceMax = resource.storage
                        )
                    }
                }
                UpgradeContent(
                    level = level,
                    onUpgradeClick = onUpgradeClick,
                    dirtCount = requiredDirt,
                    dirtLocked = requiredDirt > (resources.firstOrNull { it.type == ResourceType.DIRT }?.count
                        ?: 0),
                    rockCount = requiredRock,
                    rockLocked = requiredRock > (resources.firstOrNull { it.type == ResourceType.ROCK }?.count
                        ?: 0)
                )
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
    level: Int,
    onUpgradeClick: () -> Unit,
    dirtCount: Int,
    dirtLocked: Boolean,
    rockCount: Int,
    rockLocked: Boolean
) {
    Surface(
        modifier = Modifier.padding(top = 64.dp),
        color = Green5,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            LevelUpgradeText(
                level = level
            )
            UpgradeResourcesRequiredRow(
                dirtCount = dirtCount,
                dirtLocked = dirtLocked,
                rockCount = rockCount,
                rockLocked = rockLocked
            )
            GameButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = onUpgradeClick,
                backgroundColor = Brown1,
                text = "Améliorer",
                textColor = White
            )
        }
    }
}

@Composable
fun LevelUpgradeText(
    level: Int
) {
    val nextLevel = level + 1
    Row(
        modifier = Modifier.padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Niv. $level ",
            color = White,
            fontSize = 28.sp
        )
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_arrow_right_alt_24),
            contentDescription = null,
            tint = White
        )
        Text(
            text = " Niv. $nextLevel",
            color = White,
            fontSize = 28.sp
        )
    }

}

@Composable
fun UpgradeResourcesRequiredRow(
    dirtCount: Int,
    dirtLocked: Boolean,
    rockCount: Int,
    rockLocked: Boolean
) {
    Row(
        modifier = Modifier.padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(dirtCount > 0) {
            UpgradeResourceRequiredItem(
                modifier = Modifier.padding(horizontal = 4.dp),
                image = painterResource(id = R.drawable.dirt_without_bg),
                count = dirtCount,
                locked = dirtLocked
            )
        }
        if(rockCount > 0) {
            UpgradeResourceRequiredItem(
                modifier = Modifier.padding(horizontal = 4.dp),
                image = painterResource(id = R.drawable.rock_without_bg),
                count = rockCount,
                locked = rockLocked
            )
        }
    }
}

@Composable
fun UpgradeResourceRequiredItem(
    modifier: Modifier = Modifier,
    image: Painter,
    count: Int,
    locked: Boolean
) {
    Row(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier.height(24.dp),
            painter = image,
            contentDescription = null
        )
        Text(
            text = "x$count",
            fontSize = 18.sp,
            color = if(locked) Red1 else White
        )
    }
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
                ResourceUiState("Terre", ResourceType.DIRT, 50, 100, R.drawable.dirt_without_bg),
                ResourceUiState("Feuille", ResourceType.LEAF, 0, 100, R.drawable.leaf_without_bg),
                ResourceUiState("Champignon", ResourceType.MUSHROOM, 0, 100, R.drawable.mushroom_without_bg),
                ResourceUiState("Pierre", ResourceType.ROCK, 0, 100, R.drawable.rock_without_bg),
                ResourceUiState("Métal", ResourceType.METAL, 0, 100, R.drawable.metal_without_bg)
            ),
            onUpgradeClick = { },
            requiredDirt = 10,
            requiredRock = 10
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
        UpgradeContent(
            level = 1,
            onUpgradeClick = { },
            dirtCount = 100,
            dirtLocked = false,
            rockCount = 50,
            rockLocked = true
        )
    }
}

//endregion