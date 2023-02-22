package com.bonnamy.antsconquest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bonnamy.antsconquest.R
import com.bonnamy.antsconquest.model.AntType
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
            AntUiState(0,0,0,0,0,0,true, AntType.OUVRIERE, "Fourmi Ouvrière", R.drawable.ant_worker, 0, ResourcesRequiredUiState(5,0,0,0)),
            AntUiState(0,0,0,0,0,0,true, AntType.LANCIERE, "Fourmi Lancière des Cieux", R.drawable.ant_sky_spear, 0, ResourcesRequiredUiState(10,0,0,0)),
            AntUiState(0,0,0,0,0,0,true, AntType.CAVERNES, "Fourmi des cavernes", R.drawable.ant_cave, 0, ResourcesRequiredUiState(20,3,0,0)),
            AntUiState(0,0,0,0,0,0,true, AntType.FURTIVE, "Lame Furtive", R.drawable.ant_stealth_blade, 0, ResourcesRequiredUiState(40,9,2,0)),
            AntUiState(0,0,0,0,0,0,true, AntType.CHEVAUCHEUSE, "Fourmi Chevaucheuse de Scarabée", R.drawable.ant_beetle_rider, 0, ResourcesRequiredUiState(80,27,8,0)),
            AntUiState(0,0,0,0,0,0,true, AntType.ARDENTE, "Fourmi ardente", R.drawable.ant_fiery, 0, ResourcesRequiredUiState(160,81,32,0)),
            AntUiState(0,0,0,0,0,0,true, AntType.ACIER, "Fourmi d'acier", R.drawable.ant_steel, 0, ResourcesRequiredUiState(320,243,128,10)),
            AntUiState(0,0,0,0,0,0,true, AntType.SPECTRALE, "Fourmi Spectrale", R.drawable.ant_spectral, 0, ResourcesRequiredUiState(640,729,512,50)),
        ).toImmutableList(),
        resources = listOf<ResourceUiState>().toImmutableList()
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
        var game = gameData.value
        val index = game?.ants?.indexOfFirst { it.type == ant.type } ?: return
        val antUpdated = game.ants[index].let {
            it.copy(number = it.number + 1)
        }
        val ants = game.ants.toMutableList()
        ants[index] = antUpdated
        game = game.copy(ants = ants.toImmutableList())
        gameData.postValue(game)
    }
}