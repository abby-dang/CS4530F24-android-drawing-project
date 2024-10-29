package com.example.drawingapp

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SelectDrawingViewModel(private val repository: DrawingRepository) : ViewModel() {

    val drawings: LiveData<List<DrawingData>> = repository.allDrawings;

    fun saveDrawing(bitmap: Bitmap, fileName: String) {
        repository.saveNewDrawing(bitmap, fileName)
    }
}

// This provides a way of making a view model with parameters.
class SelectDrawingViewModelFactory(private val repository: DrawingRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectDrawingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SelectDrawingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}