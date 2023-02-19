package com.bonnamy.antsconquest.ui.uistate

import kotlinx.collections.immutable.ImmutableList

data class GameUiState(
    val level: Int,
    val ants: ImmutableList<AntUiState>
)