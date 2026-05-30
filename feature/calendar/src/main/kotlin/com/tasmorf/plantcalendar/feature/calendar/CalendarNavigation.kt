package com.tasmorf.plantcalendar.feature.calendar

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tasmorf.plantcalendar.core.navigation.Screen

fun NavGraphBuilder.calendarGraph() {
    composable(Screen.Calendar.route) {
        CalendarRoute(viewModel = hiltViewModel())
    }
}
