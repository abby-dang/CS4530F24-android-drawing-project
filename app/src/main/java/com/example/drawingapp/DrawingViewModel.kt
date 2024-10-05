package com.example.drawingapp

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
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
    private var currentColor = Color.argb(255, 0, 0, 0)
    private var currentSize = (0.1f * width / 2f).toInt()   // BRUSH RADIUS

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
        //_bitmap.value?.setPixel(xMapping, yMapping, currentColor)

        for (i in -currentSize..currentSize) {
            for (j in -currentSize..currentSize) {
                // Set pixel if inside brush radius AND not outside canvas
                // Pretty chonky if statement... idk if we can make it shorter but it works
                if (i * i + j * j <= currentSize * currentSize &&
                    (xMapping + i < width) && (yMapping + j < height) &&
                    (xMapping + i >= 0) && (yMapping + j >= 0)) {
                    _bitmap.value?.setPixel(xMapping + i, yMapping + j, currentColor)
                }
            }
        }
    }

    fun setBrush() {
        currentColor = Color.argb(255, Color.red(currentColor), Color.green(currentColor), Color.blue(currentColor))
    }

    fun setEraser() {
        currentColor = Color.argb(0, Color.red(currentColor), Color.green(currentColor), Color.blue(currentColor))
    }

    // Size is calculated with respect to canvas size
    fun setSize(sizePercentage: Float) {
        currentSize = ((sizePercentage/100f) * width / 2f).toInt()
        Log.d("BRUSH SIZE CHANGE", currentSize.toString())
    }

    fun setColor() {

    }
}