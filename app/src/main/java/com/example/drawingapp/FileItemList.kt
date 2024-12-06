package com.example.drawingapp

import android.util.Log
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
    converter: BitmapConverter,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val drawings by viewModel.getAllDrawings().collectAsState(initial = emptyList())
    LazyColumn(
        modifier = modifier
    ) {
        items(drawings) { drawing ->
            FileItem(drawing, viewModel, converter, navController,
                onClose = { viewModel.removeDrawing(drawing.fileName) }
            )
        }
    }
}

@Composable
fun DownloadableList(
    cloudData: StateFlow<List<DrawingData>>,
    repo: DrawingRepository,
    modifier: Modifier = Modifier
) {
    val cloudDrawings by cloudData.collectAsState(initial = emptyList())

    Log.d("DOWNLOAD", "RETRIEVING LIST...")

    LazyColumn(
        modifier = modifier
    ) {
        items(cloudDrawings) { drawing ->
            Log.d("DOWNLOAD", "LOOPING...")
            DownloadableItem(drawing, repo)
        }
    }
}