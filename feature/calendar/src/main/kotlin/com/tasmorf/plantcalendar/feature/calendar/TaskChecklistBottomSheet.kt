package com.tasmorf.plantcalendar.feature.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.tasmorf.plantcalendar.core.model.PlantTask
import com.tasmorf.plantcalendar.core.model.TaskType
import com.tasmorf.plantcalendar.core.ui.PlantImage
import com.tasmorf.plantcalendar.core.ui.TaskCompleted
import com.tasmorf.plantcalendar.core.ui.TaskFeed
import com.tasmorf.plantcalendar.core.ui.TaskMove
import com.tasmorf.plantcalendar.core.ui.TaskPrune
import com.tasmorf.plantcalendar.core.ui.TaskWater

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskChecklistBottomSheet(
    title: String,
    tasks: List<PlantTask>,
    onTaskClick: (PlantTask) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            if (tasks.isEmpty()) {
                Text(
                    text = "No tasks for this day!",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 32.dp)
                )
            } else {
                LazyColumn {
                    items(tasks, key = { it.id }) { task ->
                        TaskItem(task = task, onClick = { onTaskClick(task) })
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: PlantTask, onClick: () -> Unit) {
    val color = if (task.isCompleted) TaskCompleted else when (task.type) {
        TaskType.WATER -> TaskWater
        TaskType.FEED -> TaskFeed
        TaskType.PRUNE -> TaskPrune
        TaskType.MOVE -> TaskMove
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !task.isCompleted, onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PlantImage(
            imageUrl = task.plantImageUrl,
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = task.plantName,
                style = MaterialTheme.typography.titleMedium,
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null,
                color = if (task.isCompleted) Color.Gray else Color.Unspecified
            )
            Text(
                text = task.type.name.lowercase().replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.bodySmall,
                color = color,
                fontWeight = FontWeight.Bold
            )
        }
        
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = { if (!task.isCompleted) onClick() },
            colors = CheckboxDefaults.colors(
                checkedColor = TaskCompleted,
                uncheckedColor = color
            )
        )
    }
}
