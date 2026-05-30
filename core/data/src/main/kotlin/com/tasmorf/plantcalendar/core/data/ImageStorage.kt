package com.tasmorf.plantcalendar.core.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class ImageStorage(private val context: Context) {

    fun saveImage(uri: Uri): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val bitmap = BitmapFactory.decodeStream(inputStream)
            
            val fileName = "plant_${UUID.randomUUID()}.jpg"
            val file = File(context.filesDir, fileName)
            
            var quality = 100
            var size = Long.MAX_VALUE
            val targetSize = 500 * 1024 // 0.5 MB
            
            while (size > targetSize && quality > 10) {
                FileOutputStream(file).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)
                }
                size = file.length()
                quality -= 10
            }
            
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun deleteImage(path: String) {
        try {
            val file = File(path)
            if (file.exists()) {
                file.delete()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
