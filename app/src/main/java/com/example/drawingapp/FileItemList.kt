package com.example.drawingapp

import kotlinx.coroutines.flow.Flow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.runtime.remember
import androidx.navigation.NavController

//file creates list view of fileitems
@Composable
fun FileItemList(
    viewModel: SelectDrawingViewModel,
    converter: BitmapConverter,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val fileItem by viewModel.getAllFileNames().collectAsState(initial = emptyList())

    LazyColumn(
        modifier = modifier
    ) {
        items(fileItem) { file ->
            FileItem(file, viewModel, converter,  navController)
        }
    }
}