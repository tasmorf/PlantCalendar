package com.tasmorf.plantcalendar.feature.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.tasmorf.plantcalendar.core.model.PlantTask
import com.tasmorf.plantcalendar.core.model.TaskType
import com.tasmorf.plantcalendar.core.ui.TaskFeed
import com.tasmorf.plantcalendar.core.ui.TaskMove
import com.tasmorf.plantcalendar.core.ui.TaskPrune
import com.tasmorf.plantcalendar.core.ui.TaskWater
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarRoute(
    viewModel: CalendarViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    CalendarScreen(
        uiState = uiState,
        onDateSelected = viewModel::onDateSelected,
        onCompleteTask = viewModel::completeTask
    )
}

@Composable
fun CalendarScreen(
    uiState: CalendarUiState,
    onDateSelected: (LocalDate?) -> Unit,
    onCompleteTask: (PlantTask) -> Unit
) {
    var showPendingSheet by remember { mutableStateOf(false) }
    
    val state = rememberCalendarState(
        startMonth = uiState.minMonth,
        endMonth = uiState.maxMonth,
        firstVisibleMonth = uiState.currentMonth,
        firstDayOfWeek = daysOfWeek().first()
    )

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            HorizontalCalendar(
                state = state,
                dayContent = { day ->
                    Day(
                        day = day,
                        isSelected = uiState.selectedDate == day.date.toKotlinLocalDate(),
                        tasks = uiState.tasks[day.date.toKotlinLocalDate()] ?: emptyList(),
                        onClick = { onDateSelected(it.toKotlinLocalDate()) }
                    )
                },
                monthHeader = { month ->
                    MonthHeader(month.yearMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault()))
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { showPendingSheet = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("See pending tasks (${uiState.pendingTasks.size})")
            }
        }
    }

    if (uiState.selectedDate != null) {
        TaskChecklistBottomSheet(
            title = "Tasks for ${uiState.selectedDate}",
            tasks = uiState.selectedDateTasks,
            onTaskClick = onCompleteTask,
            onDismiss = { onDateSelected(null) }
        )
    }

    if (showPendingSheet) {
        TaskChecklistBottomSheet(
            title = "Pending Tasks",
            tasks = uiState.pendingTasks,
            onTaskClick = onCompleteTask,
            onDismiss = { showPendingSheet = false }
        )
    }
}

@Composable
fun MonthHeader(monthName: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        text = monthName,
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    tasks: List<PlantTask>,
    onClick: (java.time.LocalDate) -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day.date) }
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = day.date.dayOfMonth.toString(),
                color = if (day.position == DayPosition.MonthDate) Color.Unspecified else Color.LightGray,
                style = MaterialTheme.typography.bodyMedium
            )
            if (tasks.isNotEmpty()) {
                Row {
                    tasks.take(3).forEach { task ->
                        val color = when (task.type) {
                            TaskType.WATER -> TaskWater
                            TaskType.FEED -> TaskFeed
                            TaskType.PRUNE -> TaskPrune
                            TaskType.MOVE -> TaskMove
                        }
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .background(if (task.isCompleted) Color.Gray else color, CircleShape)
                        )
                        Spacer(modifier = Modifier.size(1.dp))
                    }
                }
            }
        }
    }
}
