package com.tasmorf.plantcalendar.feature.plants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasmorf.plantcalendar.core.data.PlantRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MyPlantsViewModel(
    private val repository: PlantRepository
) : ViewModel() {

    val uiState: StateFlow<MyPlantsUiState> = repository.getAllPlants()
        .map { plants -> MyPlantsUiState(plants = plants) }
        .catch { emit(MyPlantsUiState(error = it.message)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MyPlantsUiState(isLoading = true)
        )
}
