package com.example.drawingapp

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.coroutines.flow.StateFlow


//file creates list view of drawings
@Composable
fun FileItemList(
    viewModel: SelectDrawingViewModel,
    cloudData: StateFlow<List<DrawingData>>,
    converter: BitmapConverter,
    navController: NavController,
    upload: Boolean,
    modifier: Modifier = Modifier
) {
    val drawings by viewModel.getAllDrawings().collectAsState(initial = emptyList())
    val cloudDrawings by cloudData.collectAsState(initial = emptyList())
    LazyColumn(
        modifier = modifier
    ) {
        if (upload) {
            items(drawings) { drawing ->
                FileItem(drawing, viewModel, converter, navController, upload,
                    onClose = { viewModel.removeDrawing(drawing.fileName) }
                )
            }
        } else {
            items(cloudDrawings) { drawing ->
                FileItem(drawing, viewModel, converter, navController, upload,
                    onClose = { viewModel.removeDrawing(drawing.fileName) }
                )
            }
        }
    }
}