package com.tasmorf.plantcalendar.feature.plants

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val plantsModule = module {
    viewModel { MyPlantsViewModel(get()) }
    viewModel { (plantId: String?) -> AddEditPlantViewModel(get(), get(), plantId) }
}
