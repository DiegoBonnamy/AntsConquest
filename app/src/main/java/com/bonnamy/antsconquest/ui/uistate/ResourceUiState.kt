package com.bonnamy.antsconquest.ui.uistate

import com.bonnamy.antsconquest.model.ResourceType

data class ResourceUiState(
    val name: String,
    val type: ResourceType,
    val count: Int,
    val storage: Int,
    val image: Int
)