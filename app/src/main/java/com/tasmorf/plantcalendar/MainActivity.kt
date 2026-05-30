package com.tasmorf.plantcalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tasmorf.plantcalendar.core.navigation.Screen
import com.tasmorf.plantcalendar.core.ui.PlantCalendarTheme
import com.tasmorf.plantcalendar.feature.plants.plantsGraph
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlantCalendarTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                val topLevelDestinations = listOf(
                    Screen.Calendar,
                    Screen.MyPlants,
                    Screen.Settings
                )

                NavigationSuiteScaffold(
                    navigationSuiteItems = {
                        topLevelDestinations.forEach { screen ->
                            item(
                                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                onClick = {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    val icon = when (screen) {
                                        Screen.Calendar -> Icons.Default.DateRange
                                        Screen.MyPlants -> Icons.Default.LocalFlorist
                                        Screen.Settings -> Icons.Default.Settings
                                        else -> Icons.Default.Settings
                                    }
                                    Icon(icon, contentDescription = screen.route)
                                },
                                label = { 
                                    val label = screen.route.replace("_", " ")
                                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                                    Text(label) 
                                }
                            )
                        }
                    }
                ) {
                    Scaffold { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = Screen.MyPlants.route,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(Screen.Calendar.route) { Text("Calendar Screen") }
                            plantsGraph(navController)
                            composable(Screen.Settings.route) { Text("Settings Screen") }
                        }
                    }
                }
            }
        }
    }
}
