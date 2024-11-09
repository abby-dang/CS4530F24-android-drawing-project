package com.example.drawingapp

import android.os.Bundle
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

//button items associated with a file name
@Composable
fun FileItem(
    fileName: String,
    viewModel: SelectDrawingViewModel,
    converter: BitmapConverter,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ){
        Button(
            onClick = {
                viewModel.loadDrawing(fileName) { drawing ->
                    val bundle = Bundle().apply {
                        putByteArray("bitmap", converter.fromBitmap(drawing))
                    }
                    navController.navigate(R.id.action_open_drawing, bundle)
                }
            }
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                text = fileName
            )
        }
    }
}

//Testing composable
//@Composable
//fun SomeText() {
//    Text("hello world")
//}

