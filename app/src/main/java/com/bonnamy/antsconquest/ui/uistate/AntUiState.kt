package com.bonnamy.antsconquest.ui.uistate

import androidx.compose.ui.graphics.painter.Painter
import com.bonnamy.antsconquest.model.AntType

data class AntUiState(
    val health: Int,
    val attack: Int,
    val defense: Int,
    val dodge: Int,
    val speed: Int,
    val energy: Int,
    val unlocked: Boolean,
    val type: AntType,
    val name: String,
    val image: Painter,
    val number: Int
)