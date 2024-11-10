package com.example.drawingapp

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SelectDrawingViewModel(private val repository: DrawingRepository) : ViewModel() {

    val drawings: LiveData<List<DrawingData>> = repository.allDrawings;
    private val fileItemsState = MutableStateFlow<List<String>>(emptyList())

    init { //initializing mutablestateflow so UI can update accordingly
        viewModelScope.launch {
            repository.getAllFileNames().collect {fileName ->
                fileItemsState.value = fileName
            }
        }
    }

    fun saveDrawing(bitmap: Bitmap, fileName: String) {
        repository.saveNewDrawing(bitmap, fileName)
    }


    fun loadDrawing(fileName: String, onResult: (Bitmap) -> Unit) {
        viewModelScope.launch {
            val drawing = repository.getDrawing(fileName)
            onResult(drawing) // Pass the data back to the fragment
        }
    }

    //returns list of drawings
    fun getAllDrawings() : Flow<List<DrawingData>> {
        return repository.getAllDrawings()
    }
    fun removeDrawing(fileName: String) {
        viewModelScope.launch(Dispatchers.IO) { //ran as a background thread
            try {
                repository.removeDrawing(fileName)
            } catch (e:Exception) {
                Log.e("SelectDrawingViewModel", "error removing file")
            }
        }
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