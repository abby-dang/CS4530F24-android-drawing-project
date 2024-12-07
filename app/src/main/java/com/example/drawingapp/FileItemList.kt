package com.example.drawingapp

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
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

    if (cloudDrawings.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "LOADING DRAWINGS",
                    color = Color.White,
                    fontSize = 30.sp
                )
                Text(
                    "Please Wait Patiently",
                    color = Color.White,
                    fontSize = 15.sp
                )
            }
        }
    }

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