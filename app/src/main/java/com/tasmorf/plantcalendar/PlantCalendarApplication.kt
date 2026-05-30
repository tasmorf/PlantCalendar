package com.tasmorf.plantcalendar

import android.app.Application
import com.tasmorf.plantcalendar.core.data.dataModule
import com.tasmorf.plantcalendar.core.database.databaseModule
import com.tasmorf.plantcalendar.feature.plants.plantsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PlantCalendarApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PlantCalendarApplication)
            modules(
                databaseModule,
                dataModule,
                plantsModule
            )
        }
    }
}
