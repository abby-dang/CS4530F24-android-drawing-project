package com.example.drawingapp

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.storage.storage
import kotlinx.coroutines.launch


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
    val coroutineScope = rememberCoroutineScope()
    val user by remember { mutableStateOf(Firebase.auth.currentUser) }
    var successfulPicUpload by remember {mutableStateOf(false)} // used for picture upload
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

        IconButton( //Upload Button
            onClick = {
                    coroutineScope.launch {
                        if (user == null) {
                            Log.d("ERROR", "Not logged in.")
                        } else {
                            if(viewModel.uploadData(
                                    Firebase.storage.reference,
                                    "${user!!.uid}/" + drawing.fileName + ".png",
                                    viewModel.bitmapToByteArray(drawing.bitmap)
                                )) {
                                successfulPicUpload = true //cues the icon to change
                            }
                        }
                    }
                },
            modifier = Modifier
                .padding(end = 8.dp)
        ) {
            if (successfulPicUpload) {
                Icon(
                    Icons.Filled.Check, contentDescription = "Successful Upload",
                    tint = Color.White
                )
            } else {
                Icon(
                    Icons.Filled.Share, contentDescription = "Upload",
                    tint = Color.White
                )
            }
        }
        IconButton( //delete button
            onClick = onClose,
            modifier = Modifier
                .padding(end = 8.dp)
        ) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete",
                tint = Color.White)
        }

    }
}

@Composable
fun DownloadableItem(
    drawing: DrawingData,
    repo: DrawingRepository,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var successfulPicDownload by remember {mutableStateOf(false)} // used for picture download
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ){

        Image( //bitmap as png
            painter = BitmapPainter(drawing.bitmap.asImageBitmap()),
            contentDescription = drawing.fileName,
            modifier = Modifier
                .size(100.dp)
                .aspectRatio(1f)
        )
        Text( //file descriptor
            text = drawing.fileName,
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        )

        IconButton( //Download Button
            onClick = {
                    coroutineScope.launch {
                        // DOWNLOAD PIC
                        repo.saveNewDrawing(drawing.bitmap, drawing.fileName)
                        successfulPicDownload = true //cues the icon to change
                    }
                },
            modifier = Modifier
                .padding(end = 8.dp)
        ) {
            if (successfulPicDownload) {
                Icon(
                    Icons.Filled.Check, contentDescription = "Successful Download",
                    tint = Color.White
                )
            } else {
                Icon(
                    Icons.Filled.Add, contentDescription = "Download",
                    tint = Color.White
                )
            }
        }
    }
}



