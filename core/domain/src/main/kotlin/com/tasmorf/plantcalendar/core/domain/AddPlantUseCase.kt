package com.tasmorf.plantcalendar.core.domain

import com.tasmorf.plantcalendar.core.data.PlantRepository
import com.tasmorf.plantcalendar.core.data.TaskRepository
import com.tasmorf.plantcalendar.core.model.Plant
import com.tasmorf.plantcalendar.core.model.PlantTask
import com.tasmorf.plantcalendar.core.model.TaskType
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toLocalDateTime
import java.time.YearMonth
import javax.inject.Inject
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class AddPlantUseCase @Inject constructor(
    private val plantRepository: PlantRepository,
    private val taskRepository: TaskRepository
) {
    @OptIn(ExperimentalTime::class)
    suspend operator fun invoke(plant: Plant): Result<Unit> {
        return try {
            // 1. Insert the plant
            plantRepository.insertPlant(plant).getOrThrow()

            // 2. Generate initial watering tasks
            val now = Clock.System.now()
            val today = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
            val tasks = mutableListOf<PlantTask>()
            
            // Calculate end date (end of next month)
            val currentMonth = YearMonth.from(today.toJavaLocalDate())
            val nextMonth = currentMonth.plusMonths(1)
            val endDate = nextMonth.atEndOfMonth().toKotlinLocalDate()
            
            var taskDate = today
            while (taskDate <= endDate) {
                tasks.add(
                    PlantTask(
                        plantId = plant.id,
                        plantName = plant.name,
                        plantImageUrl = plant.imageUrl,
                        type = TaskType.WATER,
                        scheduledDate = taskDate
                    )
                )
                taskDate = taskDate.plus(1, DateTimeUnit.WEEK)
            }

            // 3. Insert generated tasks
            taskRepository.insertTasks(tasks).getOrThrow()
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
