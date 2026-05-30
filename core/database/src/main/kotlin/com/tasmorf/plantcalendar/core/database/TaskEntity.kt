package com.tasmorf.plantcalendar.core.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.tasmorf.plantcalendar.core.model.PlantTask
import com.tasmorf.plantcalendar.core.model.TaskType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = PlantEntity::class,
            parentColumns = ["id"],
            childColumns = ["plantId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["plantId"])]
)
data class TaskEntity(
    @PrimaryKey val id: String,
    val plantId: String,
    val plantName: String,
    val plantImageUrl: String?,
    val type: TaskType,
    val scheduledDate: LocalDate,
    val completedAt: LocalDateTime?
)

fun TaskEntity.asExternalModel() = PlantTask(
    id = id,
    plantId = plantId,
    plantName = plantName,
    plantImageUrl = plantImageUrl,
    type = type,
    scheduledDate = scheduledDate,
    completedAt = completedAt
)

fun PlantTask.asEntity() = TaskEntity(
    id = id,
    plantId = plantId,
    plantName = plantName,
    plantImageUrl = plantImageUrl,
    type = type,
    scheduledDate = scheduledDate,
    completedAt = completedAt
)
