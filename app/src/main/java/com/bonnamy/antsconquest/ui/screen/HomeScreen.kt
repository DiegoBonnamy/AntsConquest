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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bonnamy.antsconquest.R
import com.bonnamy.antsconquest.model.AntType
import com.bonnamy.antsconquest.ui.component.GameButton
import com.bonnamy.antsconquest.ui.component.GameTopBar
import com.bonnamy.antsconquest.ui.theme.AntsConquestTheme
import com.bonnamy.antsconquest.ui.theme.Brown1
import com.bonnamy.antsconquest.ui.theme.Green4
import com.bonnamy.antsconquest.ui.uistate.AntUiState
import com.bonnamy.antsconquest.viewmodel.HomeViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun HomeScreen(

) {
    val viewModel: HomeViewModel = viewModel()

    val antsData by viewModel.antsData.observeAsState(persistentListOf())

    HomeContent(
        ants = antsData,
        antCreatingClick = {
            viewModel.antCreatingClick(it)
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContent(
    ants: ImmutableList<AntUiState>,
    antCreatingClick: (AntUiState) -> Unit
) {
    val bottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        topBar = {
            GameTopBar()
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
                antName = ant.name,
                antCreatingClick = { antCreatingClick(ant) }
            )
        }
    }
}

@Composable
fun AntCreatingItem(
    antPainter: Painter,
    antName: String,
    antCreatingClick: () -> Unit
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
        Text(
            text = antName
        )
        GameButton(
            onClick = antCreatingClick,
            backgroundColor = Brown1,
            text = "Create",
            textColor = Color.White
        )
    }
}

//region Previews

@Preview
@Composable
fun HomeContentPreview() {
    AntsConquestTheme {
        HomeContent(
            ants = listOf(
                AntUiState(0, 0, 0, 0, 0, 0, true, AntType.OUVRIERE, "Ouvrière", painterResource(id = R.drawable.ant_worker), 5)
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
                AntUiState(0, 0, 0, 0, 0, 0, true, AntType.OUVRIERE, "Ouvrière", painterResource(id = R.drawable.ant_worker), 5)
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
            antName = "Ouvrière",
            antCreatingClick = {}
        )
    }
}

//endregion