package com.tasmorf.plantcalendar.core.data

import com.tasmorf.plantcalendar.core.database.PlantDao
import com.tasmorf.plantcalendar.core.database.asEntity
import com.tasmorf.plantcalendar.core.database.asExternalModel
import com.tasmorf.plantcalendar.core.model.Plant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineFirstPlantRepository(
    private val plantDao: PlantDao
) : PlantRepository {

    override fun getAllPlants(): Flow<List<Plant>> {
        return plantDao.getAllPlants().map { entities ->
            entities.map { it.asExternalModel() }
        }
    }

    override suspend fun getPlantById(id: String): Result<Plant> {
        return try {
            val plant = plantDao.getPlantById(id)?.asExternalModel()
            if (plant != null) {
                Result.success(plant)
            } else {
                Result.failure(Exception("Plant not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insertPlant(plant: Plant): Result<Unit> {
        return try {
            plantDao.insertPlant(plant.asEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updatePlant(plant: Plant): Result<Unit> {
        return try {
            plantDao.updatePlant(plant.asEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deletePlant(id: String): Result<Unit> {
        return try {
            plantDao.deletePlant(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
