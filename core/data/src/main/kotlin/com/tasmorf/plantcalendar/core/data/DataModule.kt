package com.tasmorf.plantcalendar.core.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindPlantRepository(
        repository: OfflineFirstPlantRepository
    ): PlantRepository

    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        repository: OfflineFirstTaskRepository
    ): TaskRepository
}
