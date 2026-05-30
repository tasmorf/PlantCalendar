package com.tasmorf.plantcalendar.core.data

import com.tasmorf.plantcalendar.core.model.PlantTask
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

interface TaskRepository {
    fun getTasksInRange(startDate: LocalDate, endDate: LocalDate): Flow<List<PlantTask>>
    fun getPendingTasks(today: LocalDate): Flow<List<PlantTask>>
    suspend fun completeTask(task: PlantTask, completedAt: LocalDateTime): Result<Unit>
    suspend fun insertTasks(tasks: List<PlantTask>): Result<Unit>
}
