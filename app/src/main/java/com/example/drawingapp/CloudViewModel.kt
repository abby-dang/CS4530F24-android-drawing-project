package com.example.drawingapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CloudViewModel : ViewModel() {
    private val converter = BitmapConverter()
    private val _dataList = MutableStateFlow<List<DrawingData>>(emptyList())
    val cloudDrawings: StateFlow<List<DrawingData>> = _dataList

    init {
        viewModelScope.launch {
            downloadDocument().collect { data ->
                _dataList.value = data
            }
        }
    }

    fun downloadDocument(): Flow<List<DrawingData>> = callbackFlow {
        val storage = FirebaseStorage.getInstance()

        Log.d("DOWNLOAD", "DOWNLOADING...")

        try {
            val rootReference = storage.reference

            // Get all files recursively.
            suspend fun listAllFiles(reference: StorageReference): List<StorageReference> {
                val listResult = reference.listAll().await()
                val fileReferences = mutableListOf<StorageReference>()

                fileReferences.addAll(listResult.items)

                for (folder in listResult.prefixes) {
                    fileReferences.addAll(listAllFiles(folder))
                }

                return fileReferences
            }

            val allFiles = listAllFiles(rootReference)

            val dataList = mutableListOf<DrawingData>()

            // Loop through and check if png.
            for (file in allFiles) {
                if (file.name.endsWith(".png", ignoreCase = true)) {
                    val bytes = file.getBytes(Long.MAX_VALUE).await()

                    val bitmap = converter.toBitmap(bytes)
                    val name = file.name

                    Log.d("DOWNLOAD", file.name)

                    if (bitmap != null) {
                        dataList.add(DrawingData(bitmap, name))
                    }
                }
            }

            // Send the pngs to the flow.
            trySend(dataList)

        } catch (e: Exception) {
            Log.d("DOWNLOAD", "DOWNLOAD FAILED.")
            close(e) // Close the flow if there is an error
        }

        awaitClose {  }
    }
}

