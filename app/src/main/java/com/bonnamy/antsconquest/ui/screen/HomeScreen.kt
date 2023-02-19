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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bonnamy.antsconquest.R
import com.bonnamy.antsconquest.model.AntType
import com.bonnamy.antsconquest.model.ResourcesRequired
import com.bonnamy.antsconquest.ui.component.GameButton
import com.bonnamy.antsconquest.ui.component.GameTopBar
import com.bonnamy.antsconquest.ui.theme.AntsConquestTheme
import com.bonnamy.antsconquest.ui.theme.Brown1
import com.bonnamy.antsconquest.ui.theme.Green4
import com.bonnamy.antsconquest.ui.uistate.AntUiState
import com.bonnamy.antsconquest.ui.uistate.ResourcesRequiredUiState
import com.bonnamy.antsconquest.viewmodel.HomeViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun HomeScreen(

) {
    val viewModel: HomeViewModel = viewModel()

    val gameData by viewModel.gameData.observeAsState()
    val antsData by viewModel.antsData.observeAsState(persistentListOf())

    HomeContent(
        level = gameData?.level ?: 1,
        ants = antsData,
        antCreatingClick = {
            viewModel.antCreatingClick(it)
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContent(
    level: Int,
    ants: ImmutableList<AntUiState>,
    antCreatingClick: (AntUiState) -> Unit
) {
    val bottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        topBar = {
            GameTopBar(
                level = level,
                applePercent = 0.5F // TODO
            )
        },
        sheetContent = {
            HomeBottomSheetContent(
                ants = ants,
                antCreatingClick = antCreatingClick
            )
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Green4
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(id = R.drawable.anthill),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun HomeBottomSheetContent(
    ants: ImmutableList<AntUiState>,
    antCreatingClick: (AntUiState) -> Unit
) {
    LazyColumn(
        modifier = Modifier.heightIn(min = 100.dp, max = 500.dp)
    ) {
        items(ants) { ant ->
            AntCreatingItem(
                antPainter = ant.image,
                antCreatingClick = { antCreatingClick(ant) },
                appleCount = ant.resourcesRequired.apple,
                leafCount = ant.resourcesRequired.leaf,
                mushroomCount = ant.resourcesRequired.mushroom,
                metalCount = ant.resourcesRequired.metal
            )
        }
    }
}

@Composable
fun AntCreatingItem(
    antPainter: Painter,
    antCreatingClick: () -> Unit,
    appleCount: Int = 0,
    leafCount: Int = 0,
    mushroomCount: Int = 0,
    metalCount: Int = 0
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            modifier = Modifier.height(32.dp),
            painter = antPainter,
            contentDescription = null
        )
        ResourcesRequiredRow(
            appleCount = appleCount,
            leafCount = leafCount,
            mushroomCount = mushroomCount,
            metalCount = metalCount
        )
        GameButton(
            onClick = antCreatingClick,
            backgroundColor = Brown1,
            text = "Create",
            textColor = Color.White
        )
    }
}

@Composable
fun ResourcesRequiredRow(
    appleCount: Int,
    leafCount: Int,
    mushroomCount: Int,
    metalCount: Int
) {
    Row {
        if(appleCount > 0) {
            ResourceRequiredItem(
                modifier = Modifier.padding(horizontal = 4.dp),
                image = painterResource(id = R.drawable.apple_without_bg),
                count = appleCount
            )
        }
        if(leafCount > 0) {
            ResourceRequiredItem(
                modifier = Modifier.padding(horizontal = 4.dp),
                image = painterResource(id = R.drawable.leaf_without_bg),
                count = leafCount
            )
        }
        if(mushroomCount > 0) {
            ResourceRequiredItem(
                modifier = Modifier.padding(horizontal = 4.dp),
                image = painterResource(id = R.drawable.mushroom_without_bg),
                count = mushroomCount
            )
        }
        if(metalCount > 0) {
            ResourceRequiredItem(
                modifier = Modifier.padding(horizontal = 4.dp),
                image = painterResource(id = R.drawable.metal_without_bg),
                count = metalCount
            )
        }
    }
}

@Composable
fun ResourceRequiredItem(
    modifier: Modifier = Modifier,
    image: Painter,
    count: Int
) {
    Row(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier.height(16.dp),
            painter = image,
            contentDescription = null
        )
        Text(
            text = "x$count",
            fontSize = 11.sp
        )
    }
}

//region Previews

@Preview
@Composable
fun HomeContentPreview() {
    AntsConquestTheme {
        HomeContent(
            level = 1,
            ants = listOf(
                AntUiState(0, 0, 0, 0, 0, 0, true, AntType.OUVRIERE, "Ouvrière", painterResource(id = R.drawable.ant_worker), 5, ResourcesRequiredUiState(10,8,6,0))
            ).toImmutableList(),
            antCreatingClick = {}
        )
    }
}

@Preview
@Composable
fun HomeBottomSheetContentPreview() {
    AntsConquestTheme {
        HomeBottomSheetContent(
            ants = listOf(
                AntUiState(0, 0, 0, 0, 0, 0, true, AntType.OUVRIERE, "Ouvrière", painterResource(id = R.drawable.ant_worker), 5, ResourcesRequiredUiState(100,100,100,100))
            ).toImmutableList(),
            antCreatingClick = {}
        )
    }
}

@Preview
@Composable
fun AntCreatingItemPreview() {
    AntsConquestTheme {
        AntCreatingItem(
            antPainter = painterResource(id = R.drawable.ant_worker),
            antCreatingClick = {}
        )
    }
}

@Preview
@Composable
fun ResourcesRequiredRowPreview() {
    AntsConquestTheme {
        ResourcesRequiredRow(
            appleCount = 100,
            leafCount = 100,
            mushroomCount = 100,
            metalCount = 100
        )
    }
}

@Preview
@Composable
fun ResourceRequiredItemPreview() {
    AntsConquestTheme {
        ResourceRequiredItem(
            image = painterResource(id = R.drawable.apple_without_bg),
            count = 100
        )
    }
}

//endregion