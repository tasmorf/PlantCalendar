package com.tasmorf.plantcalendar.core.model

import java.util.UUID

data class Plant(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val position: String,
    val imageUrl: String? = null
)
