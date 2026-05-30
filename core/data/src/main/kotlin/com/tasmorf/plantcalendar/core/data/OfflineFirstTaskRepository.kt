package com.tasmorf.plantcalendar.core.data

import com.tasmorf.plantcalendar.core.database.TaskDao
import com.tasmorf.plantcalendar.core.database.asEntity
import com.tasmorf.plantcalendar.core.database.asExternalModel
import com.tasmorf.plantcalendar.core.model.PlantTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

class OfflineFirstTaskRepository @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getTasksInRange(startDate: LocalDate, endDate: LocalDate): Flow<List<PlantTask>> {
        return taskDao.getTasksInRange(startDate, endDate).map { entities ->
            entities.map { it.asExternalModel() }
        }
    }

    override fun getPendingTasks(today: LocalDate): Flow<List<PlantTask>> {
        return taskDao.getPendingTasks(today).map { entities ->
            entities.map { it.asExternalModel() }
        }
    }

    override suspend fun completeTask(task: PlantTask, completedAt: LocalDateTime): Result<Unit> {
        return try {
            // Mark current task as completed
            taskDao.updateTask(task.copy(completedAt = completedAt).asEntity())
            
            // Mark all previous tasks of the same type for the same plant as completed
            taskDao.markPreviousTasksAsCompleted(
                plantId = task.plantId,
                type = task.type.name,
                onOrBeforeDate = task.scheduledDate,
                completedAt = completedAt
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insertTasks(tasks: List<PlantTask>): Result<Unit> {
        return try {
            taskDao.insertTasks(tasks.map { it.asEntity() })
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
