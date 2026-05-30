package com.tasmorf.plantcalendar.feature.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasmorf.plantcalendar.core.data.TaskRepository
import com.tasmorf.plantcalendar.core.model.PlantTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toLocalDateTime
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val selectedDate = MutableStateFlow<LocalDate?>(null)
    
    private val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    private val minDate = today.toJavaLocalDate().minusMonths(1).withDayOfMonth(1).toKotlinLocalDate()
    private val maxDate = today.toJavaLocalDate().plusMonths(2).withDayOfMonth(1).minusDays(1).toKotlinLocalDate()

    val uiState: StateFlow<CalendarUiState> = combine(
        taskRepository.getTasksInRange(minDate, maxDate),
        taskRepository.getPendingTasks(today),
        selectedDate
    ) { tasks, pending, selected ->
        val tasksByDate = tasks.groupBy { it.scheduledDate }
        CalendarUiState(
            tasks = tasksByDate,
            pendingTasks = pending,
            selectedDate = selected,
            selectedDateTasks = selected?.let { tasksByDate[it] } ?: emptyList(),
            currentMonth = YearMonth.from(today.toJavaLocalDate()),
            minMonth = YearMonth.from(today.toJavaLocalDate()).minusMonths(1),
            maxMonth = YearMonth.from(today.toJavaLocalDate()).plusMonths(1)
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CalendarUiState(isLoading = true)
    )

    fun onDateSelected(date: LocalDate?) {
        selectedDate.value = date
    }

    fun completeTask(task: PlantTask) {
        viewModelScope.launch {
            val completedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            taskRepository.completeTask(task, completedAt)
        }
    }
}
