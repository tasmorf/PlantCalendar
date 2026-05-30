package com.tasmorf.plantcalendar.core.domain

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    // AddPlantUseCase is already handled by @Inject constructor
}
