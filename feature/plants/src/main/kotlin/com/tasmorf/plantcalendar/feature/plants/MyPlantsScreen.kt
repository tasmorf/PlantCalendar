package com.tasmorf.plantcalendar.feature.plants

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tasmorf.plantcalendar.core.model.Plant
import com.tasmorf.plantcalendar.core.ui.PlantImage

@Composable
fun MyPlantsRoute(
    viewModel: MyPlantsViewModel,
    onAddPlant: () -> Unit,
    onEditPlant: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    MyPlantsScreen(
        uiState = uiState,
        onAddPlant = onAddPlant,
        onPlantClick = onEditPlant
    )
}

@Composable
fun MyPlantsScreen(
    uiState: MyPlantsUiState,
    onAddPlant: () -> Unit,
    onPlantClick: (String) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddPlant) {
                Icon(Icons.Default.Add, contentDescription = "Add Plant")
            }
        }
    ) { padding ->
        if (uiState.plants.isEmpty() && !uiState.isLoading) {
            EmptyPlantsState(onAddPlant, modifier = Modifier.padding(padding))
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(uiState.plants, key = { it.id }) { plant ->
                    PlantItem(plant = plant, onClick = { onPlantClick(plant.id) })
                }
            }
        }
    }
}

@Composable
fun PlantItem(plant: Plant, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onClick)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            PlantImage(
                imageUrl = plant.imageUrl,
                contentDescription = plant.name,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                            startY = 100f
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            ) {
                Text(
                    text = plant.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = plant.position,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
fun EmptyPlantsState(onAddPlant: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = com.tasmorf.plantcalendar.core.ui.R.drawable.ic_plant_sad),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No plants yet :(",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onAddPlant) {
                Text("Add your first plant")
            }
        }
    }
}
