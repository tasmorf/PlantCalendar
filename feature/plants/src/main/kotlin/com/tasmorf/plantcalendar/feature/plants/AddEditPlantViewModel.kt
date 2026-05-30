package com.tasmorf.plantcalendar.feature.plants

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasmorf.plantcalendar.core.data.ImageStorage
import com.tasmorf.plantcalendar.core.data.PlantRepository
import com.tasmorf.plantcalendar.core.model.Plant
import kotlinx.coroutines.launch

class AddEditPlantViewModel(
    private val repository: PlantRepository,
    private val imageStorage: ImageStorage,
    private val plantId: String? = null
) : ViewModel() {

    var state by mutableStateOf(AddEditPlantUiState(isEditing = plantId != null))
        private set

    init {
        if (plantId != null) {
            loadPlant(plantId)
        }
    }

    private fun loadPlant(id: String) {
        viewModelScope.launch {
            repository.getPlantById(id).onSuccess { plant ->
                state = state.copy(
                    name = plant.name,
                    position = plant.position,
                    imageUrl = plant.imageUrl
                )
            }.onFailure {
                state = state.copy(error = "Failed to load plant")
            }
        }
    }

    fun onNameChange(name: String) {
        state = state.copy(name = name)
    }

    fun onPositionChange(position: String) {
        state = state.copy(position = position)
    }

    fun onImageSelected(uri: Uri) {
        val path = imageStorage.saveImage(uri)
        if (path != null) {
            state = state.copy(imageUrl = path)
        } else {
            state = state.copy(error = "Failed to save image")
        }
    }

    fun savePlant() {
        viewModelScope.launch {
            state = state.copy(isSaving = true)
            val plant = Plant(
                id = plantId ?: java.util.UUID.randomUUID().toString(),
                name = state.name,
                position = state.position,
                imageUrl = state.imageUrl
            )
            
            val result = if (plantId == null) {
                repository.insertPlant(plant)
            } else {
                repository.updatePlant(plant)
            }
            
            result.onSuccess {
                state = state.copy(isSaving = false, isSaved = true)
            }.onFailure {
                state = state.copy(isSaving = false, error = "Failed to save plant")
            }
        }
    }
}
