package com.tasmorf.plantcalendar.core.navigation

sealed class Screen(val route: String) {
    object Calendar : Screen("calendar")
    object MyPlants : Screen("my_plants")
    object Settings : Screen("settings")
    object AddPlant : Screen("add_plant")
    object EditPlant : Screen("edit_plant/{plantId}") {
        fun createRoute(plantId: String) = "edit_plant/$plantId"
    }
}
