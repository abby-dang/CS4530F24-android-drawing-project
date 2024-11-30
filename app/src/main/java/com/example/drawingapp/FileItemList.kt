package com.example.drawingapp

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage

//file creates list view of drawings
@Composable
fun FileItemList(
    viewModel: SelectDrawingViewModel,
    converter: BitmapConverter,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val firebaseDB = FirebaseFirestore.getInstance()
    val drawings by viewModel.getAllDrawings().collectAsState(initial = emptyList())
    LazyColumn(
        modifier = modifier
    ) {
        items(drawings) { drawing ->
            val byteArr = viewModel.bitmapToByteArray(drawing.bitmap)
            FileItem(drawing, viewModel, converter,  navController,
                onClose = {viewModel.removeDrawing(drawing.fileName)}
            )
        }
    }
}