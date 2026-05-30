package com.tasmorf.plantcalendar.feature.plants

import com.tasmorf.plantcalendar.core.model.Plant

data class MyPlantsUiState(
    val plants: List<Plant> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
