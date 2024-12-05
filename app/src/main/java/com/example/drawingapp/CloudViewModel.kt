package com.example.drawingapp

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
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

    fun downloadDocument(): Flow<List<DrawingData>> = callbackFlow {
        val storage = FirebaseStorage.getInstance()
        val user = FirebaseAuth.getInstance().currentUser

        if (user == null)
            return@callbackFlow

        val storageReference: StorageReference = storage.reference.child("${user.uid}/")

        try {
            // List all items (files) in the specified storage folder
            val listResult = storageReference.listAll().await()

            val dataList = mutableListOf<DrawingData>()

            // Loop through each item in the folder
            for (item in listResult.items) {
                // For each item, get the file as a byte array
                val bytes = item.getBytes(Long.MAX_VALUE).await()

                // Convert the byte array into a Bitmap
                val bitmap = converter.toBitmap(bytes)
                val name = item.name

                if (bitmap != null) {
                    dataList.add(DrawingData(bitmap, name))
                }
            }

            trySend(dataList) // Send the list of Bitmaps to the Flow
            launch {
                downloadDocument().collect { data ->
                    _dataList.value = data
                }
            }
        } catch (e: Exception) {
            close(e) // Close the flow if there is an error
        }

        awaitClose { /* No cleanup necessary in this case */ }
    }
}

