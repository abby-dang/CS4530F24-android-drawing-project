package com.example.drawingapp

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SelectDrawingViewModel(private val repository: DrawingRepository) : ViewModel() {

    val drawings: LiveData<List<DrawingData>> = repository.allDrawings;

    fun saveDrawing(bitmap: Bitmap, fileName: String) {
        repository.saveNewDrawing(bitmap, fileName)
    }

    fun loadDrawing(fileName: String, onResult: (Bitmap) -> Unit) {
        viewModelScope.launch {
            val drawing = repository.getDrawing(fileName)
            onResult(drawing) // Pass the data back to the fragment
        }
    }

    fun clearDrawings() {
        repository.clearDAO()
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