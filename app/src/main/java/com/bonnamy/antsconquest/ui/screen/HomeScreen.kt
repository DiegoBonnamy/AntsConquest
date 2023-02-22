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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bonnamy.antsconquest.R
import com.bonnamy.antsconquest.model.AntType
import com.bonnamy.antsconquest.ui.component.GameButton
import com.bonnamy.antsconquest.ui.component.GameTopBar
import com.bonnamy.antsconquest.ui.theme.*
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

    HomeContent(
        level = gameData?.level ?: 1,
        ants = gameData?.ants ?: persistentListOf(),
        antCreatingClick = {
            viewModel.antCreatingClick(it)
        },
        applePercent = gameData?.applePercent() ?: 0F,
        appleCount = gameData?.appleCount() ?: 0,
        leafCount = gameData?.leafCount() ?: 0,
        mushroomCount = gameData?.mushroomCount() ?: 0,
        metalCount = gameData?.metalCount() ?: 0
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContent(
    level: Int,
    ants: ImmutableList<AntUiState>,
    antCreatingClick: (AntUiState) -> Unit,
    applePercent: Float,
    appleCount: Int,
    leafCount: Int,
    mushroomCount: Int,
    metalCount: Int
) {
    val bottomSheetState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        topBar = {
            GameTopBar(
                level = level,
                applePercent = applePercent
            )
        },
        sheetContent = {
            HomeBottomSheetContent(
                ants = ants,
                antCreatingClick = antCreatingClick,
                level = level,
                appleCount = appleCount,
                leafCount = leafCount,
                mushroomCount = mushroomCount,
                metalCount = metalCount
            )
        },
        sheetPeekHeight = 150.dp,
        sheetElevation = 8.dp
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

@Composable
fun HomeBottomSheetContent(
    ants: ImmutableList<AntUiState>,
    antCreatingClick: (AntUiState) -> Unit,
    level: Int,
    appleCount: Int,
    leafCount: Int,
    mushroomCount: Int,
    metalCount: Int
) {
    Surface(
        color = Green5,
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 48.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(ants) { ant ->
                AntCreatingItem(
                    antPainter = painterResource(id = ant.image),
                    antCount = ant.number,
                    antCreatingClick = { antCreatingClick(ant) },
                    appleCount = ant.resourcesRequired.apple,
                    leafCount = ant.resourcesRequired.leaf,
                    mushroomCount = ant.resourcesRequired.mushroom,
                    metalCount = ant.resourcesRequired.metal,
                    unlocked = level >= ant.requiredLevel,
                    requiredLevel = ant.requiredLevel,
                    appleLocked = ant.resourcesRequired.apple > appleCount,
                    leafLocked = ant.resourcesRequired.leaf > leafCount,
                    mushroomLocked = ant.resourcesRequired.mushroom > mushroomCount,
                    metalLocked = ant.resourcesRequired.metal > metalCount
                )
            }
        }
    }
}

@Composable
fun AntCreatingItem(
    antPainter: Painter,
    antCount: Int,
    antCreatingClick: () -> Unit,
    appleCount: Int = 0,
    leafCount: Int = 0,
    mushroomCount: Int = 0,
    metalCount: Int = 0,
    unlocked: Boolean,
    requiredLevel: Int,
    appleLocked: Boolean,
    leafLocked: Boolean,
    mushroomLocked: Boolean,
    metalLocked: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AntImageWithCount(
            antPainter = antPainter,
            antCount = antCount,
            unlocked = unlocked
        )
        if(unlocked) {
            ResourcesRequiredRow(
                appleCount = appleCount,
                leafCount = leafCount,
                mushroomCount = mushroomCount,
                metalCount = metalCount,
                appleLocked = appleLocked,
                leafLocked = leafLocked,
                mushroomLocked = mushroomLocked,
                metalLocked = metalLocked
            )
            GameButton(
                onClick = antCreatingClick,
                backgroundColor = Brown1,
                text = "Create",
                textColor = Color.White
            )
        }
        else {
            Text(
                text = "Débloquée au niveau $requiredLevel",
                color = White
            )
            GameButton(
                onClick = antCreatingClick,
                backgroundColor = Gray1,
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_lock_24),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun AntImageWithCount(
    antPainter: Painter,
    antCount: Int,
    unlocked: Boolean
) {
    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        Image(
            modifier = Modifier.height(56.dp),
            painter = antPainter,
            contentDescription = null,
            colorFilter = if(unlocked) null else ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
        )
        Text(
            text = " x$antCount",
            fontSize = 12.sp,
            color = White
        )
    }
}

@Composable
fun ResourcesRequiredRow(
    appleCount: Int,
    appleLocked: Boolean,
    leafCount: Int,
    leafLocked: Boolean,
    mushroomCount: Int,
    mushroomLocked: Boolean,
    metalCount: Int,
    metalLocked: Boolean
) {
    Row {
        if(appleCount > 0) {
            ResourceRequiredItem(
                modifier = Modifier.padding(horizontal = 4.dp),
                image = painterResource(id = R.drawable.apple_without_bg),
                count = appleCount,
                locked = appleLocked
            )
        }
        if(leafCount > 0) {
            ResourceRequiredItem(
                modifier = Modifier.padding(horizontal = 4.dp),
                image = painterResource(id = R.drawable.leaf_without_bg),
                count = leafCount,
                locked = leafLocked
            )
        }
        if(mushroomCount > 0) {
            ResourceRequiredItem(
                modifier = Modifier.padding(horizontal = 4.dp),
                image = painterResource(id = R.drawable.mushroom_without_bg),
                count = mushroomCount,
                locked = mushroomLocked
            )
        }
        if(metalCount > 0) {
            ResourceRequiredItem(
                modifier = Modifier.padding(horizontal = 4.dp),
                image = painterResource(id = R.drawable.metal_without_bg),
                count = metalCount,
                locked = metalLocked
            )
        }
    }
}

@Composable
fun ResourceRequiredItem(
    modifier: Modifier = Modifier,
    image: Painter,
    count: Int,
    locked: Boolean
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
            fontSize = 11.sp,
            color = if(locked) Red1 else White
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
                AntUiState(0, 0, 0, 0, 0, 0, true, 0, AntType.OUVRIERE, "Ouvrière", R.drawable.ant_worker, 5, ResourcesRequiredUiState(10,8,6,0))
            ).toImmutableList(),
            antCreatingClick = {},
            applePercent = 0.5F,
            appleCount = 100,
            leafCount = 10,
            mushroomCount = 1,
            metalCount = 0
        )
    }
}

@Preview
@Composable
fun HomeBottomSheetContentPreview() {
    AntsConquestTheme {
        HomeBottomSheetContent(
            ants = listOf(
                AntUiState(0, 0, 0, 0, 0, 0, true, 0, AntType.OUVRIERE, "Ouvrière", R.drawable.ant_worker, 5, ResourcesRequiredUiState(100,100,100,100))
            ).toImmutableList(),
            antCreatingClick = {},
            level = 1,
            appleCount = 100,
            leafCount = 10,
            mushroomCount = 1,
            metalCount = 0
        )
    }
}

@Preview
@Composable
fun AntCreatingItemPreview() {
    AntsConquestTheme {
        AntCreatingItem(
            antPainter = painterResource(id = R.drawable.ant_worker),
            antCreatingClick = {},
            antCount = 1000,
            unlocked = true,
            requiredLevel = 0,
            appleLocked = false,
            leafLocked = false,
            mushroomLocked = true,
            metalLocked = true
        )
    }
}

@Preview
@Composable
fun AntCreatingItemLockedPreview() {
    AntsConquestTheme {
        AntCreatingItem(
            antPainter = painterResource(id = R.drawable.ant_sky_spear),
            antCreatingClick = {},
            antCount = 0,
            unlocked = false,
            requiredLevel = 2,
            appleLocked = false,
            leafLocked = false,
            mushroomLocked = true,
            metalLocked = true
        )
    }
}

@Preview
@Composable
fun ResourcesRequiredRowPreview() {
    AntsConquestTheme {
        ResourcesRequiredRow(
            appleCount = 100,
            appleLocked = false,
            leafCount = 100,
            leafLocked = false,
            mushroomCount = 100,
            mushroomLocked = true,
            metalCount = 100,
            metalLocked = true
        )
    }
}

@Preview
@Composable
fun ResourceRequiredItemPreview() {
    AntsConquestTheme {
        ResourceRequiredItem(
            image = painterResource(id = R.drawable.apple_without_bg),
            count = 100,
            locked = false
        )
    }
}

//endregion