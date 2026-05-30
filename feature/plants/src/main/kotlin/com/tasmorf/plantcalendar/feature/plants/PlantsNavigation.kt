package com.tasmorf.plantcalendar.feature.plants

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.tasmorf.plantcalendar.core.navigation.Screen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

fun NavGraphBuilder.plantsGraph(navController: NavHostController) {
    composable(Screen.MyPlants.route) {
        MyPlantsRoute(
            viewModel = koinViewModel(),
            onAddPlant = { navController.navigate(Screen.AddPlant.route) },
            onEditPlant = { plantId -> navController.navigate(Screen.EditPlant.createRoute(plantId)) }
        )
    }
    
    composable(Screen.AddPlant.route) {
        AddEditPlantRoute(
            viewModel = koinViewModel { parametersOf(null) },
            onBack = { navController.popBackStack() }
        )
    }

    composable(Screen.EditPlant.route) { backStackEntry ->
        val plantId = backStackEntry.arguments?.getString("plantId")
        AddEditPlantRoute(
            viewModel = koinViewModel { parametersOf(plantId) },
            onBack = { navController.popBackStack() }
        )
    }
}
