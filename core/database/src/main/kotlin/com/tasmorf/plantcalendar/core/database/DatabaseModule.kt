package com.tasmorf.plantcalendar.core.database

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            PlantDatabase::class.java,
            "plant-database"
        ).build()
    }
    single { get<PlantDatabase>().plantDao() }
}
