package com.tasmorf.plantcalendar.core.data

import com.tasmorf.plantcalendar.core.model.Plant
import kotlinx.coroutines.flow.Flow

interface PlantRepository {
    fun getAllPlants(): Flow<List<Plant>>
    suspend fun getPlantById(id: String): Result<Plant>
    suspend fun insertPlant(plant: Plant): Result<Unit>
    suspend fun updatePlant(plant: Plant): Result<Unit>
    suspend fun deletePlant(id: String): Result<Unit>
}
