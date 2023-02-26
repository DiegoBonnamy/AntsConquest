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
    val resourcesRequired: ResourcesRequiredUiState,
    val lore: Int? = null
) {
    fun dodgeToString() = when(dodge) {
        in 0..20 -> "Très faible"
        in 20..40 -> "Faible"
        in 40..60 -> "Moyenne"
        in 60..80 -> "Elevée"
        in 80..100 -> "Très élevée"
        else -> "Indéterminée"
    }
}