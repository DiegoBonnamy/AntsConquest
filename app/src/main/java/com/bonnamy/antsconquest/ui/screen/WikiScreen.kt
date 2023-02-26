package com.bonnamy.antsconquest.ui.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.bonnamy.antsconquest.ui.theme.*
import com.bonnamy.antsconquest.ui.uistate.AntUiState
import com.bonnamy.antsconquest.ui.uistate.ResourcesRequiredUiState
import com.bonnamy.antsconquest.viewmodel.HomeViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

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

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun WikiContent(
    level: Int,
    applePercent: Float,
    onBottomBarItemClick: (Int) -> Unit,
    ants: ImmutableList<AntUiState>
) {
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var clickedAnt: AntUiState? by remember { mutableStateOf(null) }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            Surface(
                modifier = Modifier
                    .defaultMinSize(minHeight = 1.dp)
                    .verticalScroll(rememberScrollState()),
                color = Green4
            ) {
                clickedAnt?.let { ant ->
                    AntWikiContent(
                        antImage = ant.image,
                        antName = ant.name,
                        health = ant.health,
                        attack = ant.attack,
                        defense = ant.defense,
                        speed = ant.speed,
                        dodge = ant.dodgeToString(),
                        lore = ant.lore ?: 0
                    )
                }
            }
        }
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
                            onAntClick = {
                                clickedAnt = ant
                                scope.launch {
                                    bottomSheetState.show()
                                }
                            }
                        )
                    }
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

@Composable
fun AntWikiContent(
    antImage: Int,
    antName: String,
    health: Int,
    attack: Int,
    defense: Int,
    speed: Int,
    dodge: String,
    lore: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 56.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .width(150.dp)
                .padding(top = 16.dp, bottom = 8.dp),
            painter = painterResource(id = antImage),
            contentDescription = null
        )
        Text(
            text = antName,
            fontSize = 18.sp,
            color = White
        )
        AntWikiStats(
            health = health,
            attack = attack,
            defense = defense,
            speed = speed,
            dodge = dodge
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = lore),
            color = White
        )
    }
}

@Composable
fun AntWikiStats(
    health: Int,
    attack: Int,
    defense: Int,
    speed: Int,
    dodge: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        AntWikiStatItem(statName = "Santée", statValue = health.toString())
        AntWikiStatItem(statName = "Attaque", statValue = attack.toString())
        AntWikiStatItem(statName = "Défense", statValue = defense.toString())
        AntWikiStatItem(statName = "Vitesse", statValue = speed.toString())
        AntWikiStatItem(statName = "Esquive", statValue = dodge)
    }
}

@Composable
fun AntWikiStatItem(
    statName: String,
    statValue: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "$statName : ",
            color = White
        )
        Text(
            text = statValue,
            color = White
        )
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

@Preview
@Composable
fun ANtWikiContentPreview() {
    AntsConquestTheme {
        AntWikiContent(
            antImage = R.drawable.ant_worker,
            antName = "Fourmi ouvrière",
            health = 100,
            attack = 50,
            defense = 50,
            speed = 50,
            dodge = "Moyenne",
            lore = R.string.ant_worker_lore
        )
    }
}

@Preview
@Composable
fun ANtWikiStatsPreview() {
    AntsConquestTheme {
        AntWikiStats(
            health = 100,
            attack = 50,
            defense = 50,
            speed = 50,
            dodge = "Moyenne"
        )
    }
}

//endregion