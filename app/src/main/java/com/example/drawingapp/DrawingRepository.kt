package com.example.drawingapp

import android.graphics.Bitmap
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DrawingRepository(private val scope: CoroutineScope,
                        private val dao: DrawingDAO) {

    val allDrawings = dao.allDrawings().asLiveData()

    fun saveNewDrawing(bitmap: Bitmap, fileName: String) {
        scope.launch {
            dao.saveDrawing(DrawingData(bitmap, fileName))
        }
    }

    suspend fun getDrawing(fileName: String) = dao.retrieveDrawing(fileName).bitmap
}