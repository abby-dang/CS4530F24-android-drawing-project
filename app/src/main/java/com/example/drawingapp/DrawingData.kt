package com.example.drawingapp

import android.graphics.Bitmap
import androidx.room.Entity

@Entity(tableName="drawings")
data class DrawingData(var bitmap: Bitmap, var fileName: String){

}