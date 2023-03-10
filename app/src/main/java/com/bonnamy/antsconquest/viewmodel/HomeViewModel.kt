package com.bonnamy.antsconquest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bonnamy.antsconquest.R
import com.bonnamy.antsconquest.model.AntType
import com.bonnamy.antsconquest.model.ResourceType
import com.bonnamy.antsconquest.ui.uistate.AntUiState
import com.bonnamy.antsconquest.ui.uistate.GameUiState
import com.bonnamy.antsconquest.ui.uistate.ResourceUiState
import com.bonnamy.antsconquest.ui.uistate.ResourcesRequiredUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class HomeViewModel(app: Application): AndroidViewModel(app) {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val game: GameUiState = GameUiState(
        level = 1,
        ants = listOf(
            AntUiState(40,20,10,10,20,0,true, 0, AntType.OUVRIERE, "Fourmi Ouvrière", R.drawable.ant_worker, 0, ResourcesRequiredUiState(5,0,0,0), lore = R.string.ant_worker_lore),
            AntUiState(80,40,25,30,50,30,true, 2, AntType.LANCIERE, "Fourmi Lancière des Cieux", R.drawable.ant_sky_spear, 0, ResourcesRequiredUiState(10,0,0,0), lore = R.string.ant_sky_spear),
            AntUiState(60,40,30,30,20,40,true, 3, AntType.CAVERNES, "Fourmi des cavernes", R.drawable.ant_cave, 0, ResourcesRequiredUiState(20,3,0,0), lore = R.string.ant_cave),
            AntUiState(60,60,30,60,90,40,true, 4, AntType.FURTIVE, "Lame Furtive", R.drawable.ant_stealth_blade, 0, ResourcesRequiredUiState(40,9,2,0), lore = R.string.ant_stealth_blade),
            AntUiState(100,40,70,30,50,50,true, 5, AntType.CHEVAUCHEUSE, "Fourmi Chevaucheuse de Scarabée", R.drawable.ant_beetle_rider, 0, ResourcesRequiredUiState(80,27,8,0), lore = R.string.ant_beetle_rider),
            AntUiState(80,70,60,60,60,30,true, 6, AntType.ARDENTE, "Fourmi ardente", R.drawable.ant_fiery, 0, ResourcesRequiredUiState(160,81,32,0), lore = R.string.ant_fiery),
            AntUiState(120,100,90,20,30,80,true, 7, AntType.ACIER, "Fourmi d'acier", R.drawable.ant_steel, 0, ResourcesRequiredUiState(320,243,128,10), lore = R.string.ant_steel),
            AntUiState(30,80,50,80,80,40,true, 8, AntType.SPECTRALE, "Fourmi Spectrale", R.drawable.ant_spectral, 0, ResourcesRequiredUiState(640,729,512,50), lore = R.string.ant_spectral),
        ).toImmutableList(),
        resources = listOf(
            ResourceUiState("Pomme", ResourceType.APPLE, 10, 100, R.drawable.apple_without_bg),
            ResourceUiState("Terre", ResourceType.DIRT, 0, 100, R.drawable.dirt_without_bg),
            ResourceUiState("Feuille", ResourceType.LEAF, 0, 100, R.drawable.leaf_without_bg),
            ResourceUiState("Champignon", ResourceType.MUSHROOM, 0, 100, R.drawable.mushroom_without_bg),
            ResourceUiState("Pierre", ResourceType.ROCK, 0, 100, R.drawable.rock_without_bg),
            ResourceUiState("Métal", ResourceType.METAL, 0, 100, R.drawable.metal_without_bg)
        ).toImmutableList()
    )

    // LivesData
    val gameData = MutableLiveData(game)

    fun loadData() {
//        val service = RetrofitSingleton.retrofit.create(WSInterface::class.java)
//
//        // Get cols
//        scope.launch {
//            val colsJob = service.getCols(1)
//            colsData.postValue(colsJob.map { it.toColUiState() }.toImmutableList())
//        }
    }

    fun antCreatingClick(ant: AntUiState) {
        var game = gameData.value ?: return
        // Check creation
        if(!canCreateAnt(ant)) return
        // Add ant
        val index = game.ants.indexOfFirst { it.type == ant.type }
        val antUpdated = game.ants[index].let {
            it.copy(number = it.number + 1)
        }
        val ants = game.ants.toMutableList()
        ants[index] = antUpdated
        game = game.copy(ants = ants.toImmutableList())
        // Remove resources
        game = game.copy(resources = game.resources.map {
            when(it.type) {
                ResourceType.APPLE -> it.copy(count = it.count - ant.resourcesRequired.apple)
                ResourceType.LEAF -> it.copy(count = it.count - ant.resourcesRequired.leaf)
                ResourceType.MUSHROOM -> it.copy(count = it.count - ant.resourcesRequired.mushroom)
                ResourceType.METAL -> it.copy(count = it.count - ant.resourcesRequired.metal)
                else -> it
            }
        }.toImmutableList())
        // refresh LiveData
        gameData.postValue(game)
    }

    private fun canCreateAnt(ant: AntUiState): Boolean {
        val game = gameData.value ?: return false
        if(ant.requiredLevel > game.level) return false
        if(ant.resourcesRequired.apple > game.appleCount()) return false
        if(ant.resourcesRequired.leaf > game.leafCount()) return false
        if(ant.resourcesRequired.mushroom > game.mushroomCount()) return false
        if(ant.resourcesRequired.metal > game.metalCount()) return false
        return true
    }
}