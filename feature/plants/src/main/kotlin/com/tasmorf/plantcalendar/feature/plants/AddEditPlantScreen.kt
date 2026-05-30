package com.tasmorf.plantcalendar.feature.plants

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tasmorf.plantcalendar.core.ui.PlantImage

@Composable
fun AddEditPlantRoute(
    viewModel: AddEditPlantViewModel,
    onBack: () -> Unit
) {
    val state = viewModel.state
    
    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            onBack()
        }
    }

    AddEditPlantScreen(
        state = state,
        onNameChange = viewModel::onNameChange,
        onPositionChange = viewModel::onPositionChange,
        onImageSelected = viewModel::onImageSelected,
        onSave = viewModel::savePlant,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddEditPlantScreen(
    state: AddEditPlantUiState,
    onNameChange: (String) -> Unit,
    onPositionChange: (String) -> Unit,
    onImageSelected: (android.net.Uri) -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> uri?.let { onImageSelected(it) } }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.isEditing) "Update Plant" else "Add Plant") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clickable {
                        launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
                contentAlignment = Alignment.Center
            ) {
                PlantImage(
                    imageUrl = state.imageUrl,
                    contentDescription = "Plant Image",
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.name,
                onValueChange = onNameChange,
                label = { Text("Plant Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.position,
                onValueChange = onPositionChange,
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            val defaultPositions = listOf("Bedroom", "Hallway", "Kitchen", "Living Room", "Main Bedroom", "Office", "Study")
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                maxItemsInEachRow = 3
            ) {
                defaultPositions.forEach { pos ->
                    SuggestionChip(
                        onClick = { onPositionChange(pos) },
                        label = { Text(pos) },
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onSave,
                enabled = state.canSave,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (state.isEditing) "Update Plant" else "Add Plant")
            }
        }
    }
}
