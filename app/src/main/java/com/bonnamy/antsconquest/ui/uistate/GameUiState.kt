package com.bonnamy.antsconquest.ui.uistate

import com.bonnamy.antsconquest.model.ResourceType
import kotlinx.collections.immutable.ImmutableList

data class GameUiState(
    val level: Int,
    val ants: ImmutableList<AntUiState>,
    val resources: ImmutableList<ResourceUiState>
) {
    fun energyCount(): Int {
        var result = 0
        for (ant in ants) {
            result += ant.energy
        }
        return result
    }

    fun appleCount(): Int = resources.firstOrNull { it.type == ResourceType.APPLE }?.count ?: 0
    fun leafCount(): Int = resources.firstOrNull { it.type == ResourceType.LEAF }?.count ?: 0
    fun mushroomCount(): Int = resources.firstOrNull { it.type == ResourceType.MUSHROOM }?.count ?: 0
    fun metalCount(): Int = resources.firstOrNull { it.type == ResourceType.METAL }?.count ?: 0

    fun applePercent(): Float = energyCount().toFloat()/appleCount()
}