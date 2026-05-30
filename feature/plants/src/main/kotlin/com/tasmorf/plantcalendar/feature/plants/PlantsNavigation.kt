package com.tasmorf.plantcalendar.feature.plants

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.tasmorf.plantcalendar.core.navigation.Screen
import androidx.hilt.navigation.compose.hiltViewModel

fun NavGraphBuilder.plantsGraph(navController: NavHostController) {
    composable(Screen.MyPlants.route) {
        MyPlantsRoute(
            viewModel = hiltViewModel(),
            onAddPlant = { navController.navigate(Screen.AddPlant.route) },
            onEditPlant = { plantId -> navController.navigate(Screen.EditPlant.createRoute(plantId)) }
        )
    }
    
    composable(Screen.AddPlant.route) {
        AddEditPlantRoute(
            viewModel = hiltViewModel(),
            onBack = { navController.popBackStack() }
        )
    }

    composable(Screen.EditPlant.route) {
        AddEditPlantRoute(
            viewModel = hiltViewModel(),
            onBack = { navController.popBackStack() }
        )
    }
}
