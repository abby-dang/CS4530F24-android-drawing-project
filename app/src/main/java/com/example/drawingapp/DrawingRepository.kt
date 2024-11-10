package com.example.drawingapp

import android.graphics.Bitmap
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DrawingRepository(private val scope: CoroutineScope,
                        private val dao: DrawingDAO) {

    val allDrawings = dao.allDrawings().asLiveData()

    fun saveNewDrawing(bitmap: Bitmap, fileName: String) {
        scope.launch {
            dao.upsert(fileName, bitmap)
        }
    }

    suspend fun getDrawing(fileName: String): Bitmap = dao.retrieveDrawing(fileName).bitmap

    fun clearDAO() {
        scope.launch {
            dao.clearDrawings()
        }
    }

    fun getAllFileNames(): Flow<List<String>> {
        return dao.retrieveAllFileName()
    }

    suspend fun removeDrawing(fileName: String) {
        dao.deleteDrawing(fileName)
    }


    fun getAllDrawings(): Flow<List<DrawingData>> {
        return dao.allDrawings()
    }
}