package com.tasmorf.plantcalendar.core.database

import androidx.room.TypeConverter
import com.tasmorf.plantcalendar.core.model.TaskType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

class Converters {
    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? = value?.toString()

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it) }

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? = value?.toString()

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? = value?.let { LocalDateTime.parse(it) }

    @TypeConverter
    fun fromTaskType(value: TaskType?): String? = value?.name

    @TypeConverter
    fun toTaskType(value: String?): TaskType? = value?.let { TaskType.valueOf(it) }
}
