package com.bonnamy.antsconquest.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bonnamy.antsconquest.R
import com.bonnamy.antsconquest.ui.component.GameButton
import com.bonnamy.antsconquest.ui.component.GameTopBar
import com.bonnamy.antsconquest.ui.theme.AntsConquestTheme
import com.bonnamy.antsconquest.ui.theme.Brown1
import com.bonnamy.antsconquest.ui.theme.Green4

@Composable
fun HomeScreen(

) {

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContent(

) {
    val bottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Expanded)
    )

    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        topBar = {
            GameTopBar()
        },
        sheetContent = {

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

) {
    LazyColumn {

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
        HomeContent()
    }
}

@Preview
@Composable
fun HomeBottomSheetContentPreview() {
    AntsConquestTheme {
        HomeBottomSheetContent()
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