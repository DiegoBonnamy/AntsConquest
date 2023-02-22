package com.bonnamy.antsconquest.ui.uistate

import com.bonnamy.antsconquest.model.AntType

data class AntUiState(
    val health: Int,
    val attack: Int,
    val defense: Int,
    val dodge: Int,
    val speed: Int,
    val energy: Int,
    val unlocked: Boolean,
    val requiredLevel: Int,
    val type: AntType,
    val name: String,
    val image: Int,
    val number: Int,
    val resourcesRequired: ResourcesRequiredUiState
)