package com.example.drawingapp


import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


/**
 * A view model that stores a bitmap representing the canvas as well
 * as pen properties
 */
class DrawingViewModel() : ViewModel() {
    private val _bitmap : MutableLiveData<Bitmap> = MutableLiveData()
    val bitmap : LiveData<Bitmap> get() = _bitmap
    private var width = 40
    private var height = 40

    private var currentColor = Color.argb(255, 0, 0, 0)
    private var currentSize = ((0.1f * width).toInt() / 2)  // BRUSH RADIUS

    enum class BrushTypes {
        circle,
        square
    }
    var currentBrushType = BrushTypes.circle;

    fun updateBitmap(newBitmap: Bitmap) {
        Log.d("Update Bitmap", newBitmap.toString())
        _bitmap.value = newBitmap
        width = newBitmap.width
        height = newBitmap.height
    }

    fun getBitmap() : Bitmap? = _bitmap.value

    /**
     * Draws a pixel to the bitmap.
     * @param x The x canvas coordinates of where to draw.
     * @param y The y canvas coordinates of where to draw.
     */
    fun drawPixel(x: Float, y: Float, canvasWidth: Int, canvasHeight: Int){
        // Convert canvas coordinates to bitmap coordinates.
        var xMapping = ((x / canvasWidth) * width).toInt()
        var yMapping = ((y / canvasHeight) * height).toInt()

        if (currentBrushType == BrushTypes.circle){
            drawCircle(xMapping, yMapping)
        }
        else if (currentBrushType == BrushTypes.square){
            drawSquare(xMapping, yMapping)
        }
    }


    fun drawCircle(xMapping: Int, yMapping: Int){
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


    fun drawSquare(xMapping: Int, yMapping: Int){
        for (i in -currentSize..currentSize) {
            for (j in -currentSize..currentSize) {
                if ((xMapping + i < width) && (yMapping + j < height) &&
                    (xMapping + i >= 0) && (yMapping + j >= 0)
                ) {
                    _bitmap.value?.setPixel(xMapping + i, yMapping + j, currentColor)
                }
            }
        }
    }


    fun setCircleBrush() {
        currentBrushType = BrushTypes.circle;
        currentColor = Color.argb(255, Color.red(currentColor), Color.green(currentColor), Color.blue(currentColor))
    }

    fun setSquareBrush() {
        currentBrushType = BrushTypes.square;
        currentColor = Color.argb(255, Color.red(currentColor), Color.green(currentColor), Color.blue(currentColor))
    }

    fun setEraser() {
        currentBrushType = BrushTypes.square;
        currentColor = Color.argb(0, Color.red(currentColor), Color.green(currentColor), Color.blue(currentColor))
    }


    // Size is calculated with respect to canvas size
    fun setSize(sizePercentage: Float) {
        currentSize = (((sizePercentage/100f) * (width/2)).toInt() / 2)
    }

    fun setColor(newColor: Int) {
        currentColor = Color.argb(Color.alpha(currentColor), Color.red(newColor), Color.green(newColor), Color.blue(newColor))
    }

    fun getSize(): Int {
        return currentSize;
    }

    fun getColor(): Int {
        return currentColor;
    }
}
