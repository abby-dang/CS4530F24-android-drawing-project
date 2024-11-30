package com.example.drawingapp

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController



//file creates list view of drawings
@Composable
fun FileItemList(
    viewModel: SelectDrawingViewModel,
    converter: BitmapConverter,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val drawings by viewModel.getAllDrawings().collectAsState(initial = emptyList())
    LazyColumn(
        modifier = modifier
    ) {
        items(drawings) { drawing ->
            FileItem(drawing, viewModel, converter,  navController,
                onClose = {viewModel.removeDrawing(drawing.fileName)}
            )
        }
    }
}