package com.tasmorf.plantcalendar.feature.calendar

import com.tasmorf.plantcalendar.core.model.PlantTask
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import java.time.YearMonth

data class CalendarUiState(
    val tasks: Map<LocalDate, List<PlantTask>> = emptyMap(),
    val pendingTasks: List<PlantTask> = emptyList(),
    val selectedDate: LocalDate? = null,
    val selectedDateTasks: List<PlantTask> = emptyList(),
    val currentMonth: YearMonth = YearMonth.now(),
    val minMonth: YearMonth = YearMonth.now().minusMonths(1),
    val maxMonth: YearMonth = YearMonth.now().plusMonths(1),
    val isLoading: Boolean = false,
    val error: String? = null
)
