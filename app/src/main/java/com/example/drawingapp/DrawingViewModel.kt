package com.example.drawingapp

import android.graphics.Bitmap
import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class DrawingViewModel: ViewModel() {
    private var width = 40
    private var height = 40
    private val _bitmap : MutableLiveData<Bitmap> =
        MutableLiveData(Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888))

    val bitmap = _bitmap as LiveData<Bitmap>

    fun drawPixel(x: Float, y: Float, canvasWidth: Int, canvasHeight: Int){
        var xMapping = ((x / canvasWidth) * width).toInt()
        var yMapping = ((y / canvasHeight) * height).toInt()
        if (xMapping > width || yMapping > height)
            return
        _bitmap.value?.setPixel(xMapping, yMapping, Color.GREEN)
    }
}