package com.tasmorf.plantcalendar.core.data

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single<PlantRepository> { OfflineFirstPlantRepository(get()) }
    single { ImageStorage(androidContext()) }
}
