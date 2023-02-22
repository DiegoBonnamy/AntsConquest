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
            AntUiState(0,0,0,0,0,0,true, 0, AntType.OUVRIERE, "Fourmi Ouvrière", R.drawable.ant_worker, 0, ResourcesRequiredUiState(5,0,0,0)),
            AntUiState(0,0,0,0,0,0,true, 2, AntType.LANCIERE, "Fourmi Lancière des Cieux", R.drawable.ant_sky_spear, 0, ResourcesRequiredUiState(10,0,0,0)),
            AntUiState(0,0,0,0,0,0,true, 3, AntType.CAVERNES, "Fourmi des cavernes", R.drawable.ant_cave, 0, ResourcesRequiredUiState(20,3,0,0)),
            AntUiState(0,0,0,0,0,0,true, 4, AntType.FURTIVE, "Lame Furtive", R.drawable.ant_stealth_blade, 0, ResourcesRequiredUiState(40,9,2,0)),
            AntUiState(0,0,0,0,0,0,true, 5, AntType.CHEVAUCHEUSE, "Fourmi Chevaucheuse de Scarabée", R.drawable.ant_beetle_rider, 0, ResourcesRequiredUiState(80,27,8,0)),
            AntUiState(0,0,0,0,0,0,true, 6, AntType.ARDENTE, "Fourmi ardente", R.drawable.ant_fiery, 0, ResourcesRequiredUiState(160,81,32,0)),
            AntUiState(0,0,0,0,0,0,true, 7, AntType.ACIER, "Fourmi d'acier", R.drawable.ant_steel, 0, ResourcesRequiredUiState(320,243,128,10)),
            AntUiState(0,0,0,0,0,0,true, 8, AntType.SPECTRALE, "Fourmi Spectrale", R.drawable.ant_spectral, 0, ResourcesRequiredUiState(640,729,512,50)),
        ).toImmutableList(),
        resources = listOf(
            ResourceUiState("Pomme", ResourceType.APPLE, 0),
            ResourceUiState("Feuille", ResourceType.LEAF, 0),
            ResourceUiState("Champignon", ResourceType.MUSHROOM, 0),
            ResourceUiState("Métal", ResourceType.METAL, 0)
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
        if(!canCreateAnt(ant)) return
        val index = game.ants.indexOfFirst { it.type == ant.type }
        val antUpdated = game.ants[index].let {
            it.copy(number = it.number + 1)
        }
        val ants = game.ants.toMutableList()
        ants[index] = antUpdated
        game = game.copy(ants = ants.toImmutableList())
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