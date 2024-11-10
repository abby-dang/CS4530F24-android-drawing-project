package com.example.drawingapp

import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

//button items associated with a file name
@Composable
fun FileItem(
    fileName: String,
    viewModel: SelectDrawingViewModel,
    converter: BitmapConverter,
    navController: NavController,
    onClose : () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = fileName,
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
                .clickable {
                viewModel.loadDrawing(fileName) { drawing ->
                    val bundle = Bundle().apply {
                        putByteArray("bitmap", converter.fromBitmap(drawing))
                    }
                    navController.navigate(R.id.action_open_drawing, bundle)
                }
            }
        )
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .padding(end = 8.dp)
        ) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}

//Testing composable
//@Composable
//fun SomeText() {
//    Text("hello world")
//}

