package com.bonnamy.antsconquest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bonnamy.antsconquest.ui.uistate.AntUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(app: Application): AndroidViewModel(app) {

    private val scope = CoroutineScope(Dispatchers.IO)

    // LivesData
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
}