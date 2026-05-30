package com.tasmorf.plantcalendar.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tasmorf.plantcalendar.core.model.Plant

@Entity(tableName = "plants")
data class PlantEntity(
    @PrimaryKey val id: String,
    val name: String,
    val position: String,
    val imageUrl: String?
)

fun PlantEntity.asExternalModel() = Plant(
    id = id,
    name = name,
    position = position,
    imageUrl = imageUrl
)

fun Plant.asEntity() = PlantEntity(
    id = id,
    name = name,
    position = position,
    imageUrl = imageUrl
)
