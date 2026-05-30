package com.tasmorf.plantcalendar.feature.plants

data class AddEditPlantUiState(
    val name: String = "",
    val position: String = "",
    val imageUrl: String? = null,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val error: String? = null,
    val isEditing: Boolean = false
) {
    val canSave: Boolean get() = name.isNotBlank() && position.isNotBlank() && !isSaving
}
