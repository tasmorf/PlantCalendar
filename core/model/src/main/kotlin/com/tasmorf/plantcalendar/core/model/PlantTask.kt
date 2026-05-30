package com.tasmorf.plantcalendar.core.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import java.util.UUID

enum class TaskType {
    WATER, FEED, PRUNE, MOVE
}

data class PlantTask(
    val id: String = UUID.randomUUID().toString(),
    val plantId: String,
    val plantName: String, // Denormalized for convenience in list views
    val plantImageUrl: String? = null,
    val type: TaskType,
    val scheduledDate: LocalDate,
    val completedAt: LocalDateTime? = null
) {
    val isCompleted: Boolean get() = completedAt != null
}
