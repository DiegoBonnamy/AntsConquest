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
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(app: Application): AndroidViewModel(app) {

    private val scope = CoroutineScope(Dispatchers.IO)

    // LivesData
    val gameData = MutableLiveData<GameUiState>(GameUiState(
        level = 1,
        ants = listOf(
            AntUiState(0,0,0,0,0,0,true, AntType.OUVRIERE, "Fourmi Ouvrière", R.drawable.ant_worker, 0, ResourcesRequiredUiState(0,0,0,0)),
            AntUiState(0,0,0,0,0,0,true, AntType.LANCIERE, "Fourmi Lancière des Cieux", R.drawable.ant_sky_spear, 0, ResourcesRequiredUiState(0,0,0,0)),
            AntUiState(0,0,0,0,0,0,true, AntType.CAVERNES, "Fourmi des cavernes", R.drawable.ant_cave, 0, ResourcesRequiredUiState(0,0,0,0)),
            AntUiState(0,0,0,0,0,0,true, AntType.FURTIVE, "Lame Furtive", R.drawable.ant_stealth_blade, 0, ResourcesRequiredUiState(0,0,0,0)),
            AntUiState(0,0,0,0,0,0,true, AntType.CHEVAUCHEUSE, "Fourmi Chevaucheuse de Scarabée", R.drawable.ant_beetle_rider, 0, ResourcesRequiredUiState(0,0,0,0)),
            AntUiState(0,0,0,0,0,0,true, AntType.ARDENTE, "Fourmi ardente", R.drawable.ant_fiery, 0, ResourcesRequiredUiState(0,0,0,0)),
            AntUiState(0,0,0,0,0,0,true, AntType.ACIER, "Fourmi d'acier", R.drawable.ant_steel, 0, ResourcesRequiredUiState(0,0,0,0)),
            AntUiState(0,0,0,0,0,0,true, AntType.SPECTRALE, "Fourmi Spectrale", R.drawable.ant_spectral, 0, ResourcesRequiredUiState(0,0,0,0)),
        ).toImmutableList(),
        resources = listOf<ResourceUiState>().toImmutableList()
    ))
    val antsData = MutableLiveData<ImmutableList<AntUiState>>()

    fun loadData() {
//        val service = RetrofitSingleton.retrofit.create(WSInterface::class.java)
//
//        // Get cols
//        scope.launch {
//            val colsJob = service.getCols(1)
//            colsData.postValue(colsJob.map { it.toColUiState() }.toImmutableList())
//        }
    }

    fun antCreatingClick(antUiState: AntUiState) {

    }

    //region temporary objects
    val gameUiState = GameUiState(
        level = 1,
        ants = listOf(
            AntUiState(0,0,0,0,0,0,true, AntType.OUVRIERE, "Fourmi Ouvrière", R.drawable.ant_worker, 0, ResourcesRequiredUiState(0,0,0,0)),
            AntUiState(0,0,0,0,0,0,true, AntType.LANCIERE, "Fourmi Lancière des Cieux", R.drawable.ant_sky_spear, 0, ResourcesRequiredUiState(0,0,0,0)),
            AntUiState(0,0,0,0,0,0,true, AntType.CAVERNES, "Fourmi des cavernes", R.drawable.ant_cave, 0, ResourcesRequiredUiState(0,0,0,0)),
            AntUiState(0,0,0,0,0,0,true, AntType.FURTIVE, "Lame Furtive", R.drawable.ant_stealth_blade, 0, ResourcesRequiredUiState(0,0,0,0)),
            AntUiState(0,0,0,0,0,0,true, AntType.CHEVAUCHEUSE, "Fourmi Chevaucheuse de Scarabée", R.drawable.ant_beetle_rider, 0, ResourcesRequiredUiState(0,0,0,0)),
            AntUiState(0,0,0,0,0,0,true, AntType.ARDENTE, "Fourmi ardente", R.drawable.ant_fiery, 0, ResourcesRequiredUiState(0,0,0,0)),
            AntUiState(0,0,0,0,0,0,true, AntType.ACIER, "Fourmi d'acier", R.drawable.ant_steel, 0, ResourcesRequiredUiState(0,0,0,0)),
            AntUiState(0,0,0,0,0,0,true, AntType.SPECTRALE, "Fourmi Spectrale", R.drawable.ant_spectral, 0, ResourcesRequiredUiState(0,0,0,0)),
        ).toImmutableList(),
        resources = listOf<ResourceUiState>().toImmutableList()
    )
    //endregion
}