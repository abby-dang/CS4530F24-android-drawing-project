package com.example.drawingapp

import android.graphics.Bitmap
import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

/**
 * A view model that stores a bitmap representing the canvas as well
 * as pen properties
 */
class DrawingViewModel: ViewModel() {
    private var width = 40
    private var height = 40
    private val _bitmap : MutableLiveData<Bitmap> =
        MutableLiveData(Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888))

    val bitmap = _bitmap as LiveData<Bitmap>

    /**
     * Draws a pixel to the bitmap.
     * @param x The x canvas coordinates of where to draw.
     * @param y The y canvas coordinates of where to draw.
     */
    fun drawPixel(x: Float, y: Float, canvasWidth: Int, canvasHeight: Int){
        // Convert canvas coordinates to bitmap coordinates.
        var xMapping = ((x / canvasWidth) * width).toInt()
        var yMapping = ((y / canvasHeight) * height).toInt()

        // Only attempt to draw if the coordinates are on the bitmap. (Prevents Crash)
        if (xMapping >= width || yMapping >= height || xMapping < 0 || yMapping < 0)
            return

        // Actually draw the pixel.
        _bitmap.value?.setPixel(xMapping, yMapping, Color.GREEN)
    }
}