package com.tasmorf.plantcalendar.core.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePlantDatabase(
        @ApplicationContext context: Context
    ): PlantDatabase {
        return Room.databaseBuilder(
            context,
            PlantDatabase::class.java,
            "plant-database"
        ).build()
    }

    @Provides
    fun providePlantDao(database: PlantDatabase): PlantDao {
        return database.plantDao()
    }
}
