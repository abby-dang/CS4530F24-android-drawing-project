package com.example.drawingapp

import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter

//defines drawing items for the main screen
@Composable
fun FileItem(
    drawing: DrawingData,
    viewModel: SelectDrawingViewModel,
    converter: BitmapConverter,
    navController: NavController,
    onClose : () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ){

        Image( //bitmap as png
            painter = BitmapPainter(drawing.bitmap.asImageBitmap()),
            contentDescription = drawing.fileName,
            modifier = Modifier
                .size(100.dp)
                .aspectRatio(1f)
                .clickable {
                    viewModel.loadDrawing(drawing.fileName) { drawing ->
                        val bundle = Bundle().apply {
                            putByteArray("bitmap", converter.fromBitmap(drawing))
                        }
                        navController.navigate(R.id.action_open_drawing, bundle)
                    }
                }
        )
        Text( //file descriptor
            text = drawing.fileName,
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        )
        IconButton( //delete button
            onClick = onClose,
            modifier = Modifier
                .padding(end = 8.dp)
        ) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}



