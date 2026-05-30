package com.tasmorf.plantcalendar.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage

@Composable
fun PlantImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    if (imageUrl != null) {
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale,
            placeholder = painterResource(id = android.R.drawable.ic_menu_gallery),
            error = painterResource(id = android.R.drawable.ic_menu_report_image)
        )
    } else {
        // Default plant image
        Image(
            painter = painterResource(id = R.drawable.ic_plant_default),
            contentDescription = contentDescription,
            modifier = modifier.fillMaxSize(),
            contentScale = contentScale
        )
    }
}
