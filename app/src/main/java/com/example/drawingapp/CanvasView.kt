package com.example.drawingapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class CanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    // Move to View Model
    private val bitmap = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888)
    private val bitmapCanvas = Canvas(bitmap)
    private val paint = Paint()

    private val rect: Rect by lazy { Rect(0,0,width, height) }

    override fun onDraw(canvas: Canvas) {
        Log.e("DRAW", "WE DRAWEDED IT")
        super.onDraw(canvas)
        paint.color = Color.WHITE
        bitmapCanvas.drawRect(0f,0f, bitmap.width.toFloat(), bitmap.height.toFloat(), paint)
        paint.isAntiAlias = false
        bitmap.setPixel(1, 1, Color.RED)
        canvas.drawBitmap(bitmap, null, rect, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.e("COORDINATES:", "(" + event.x + "," + event.y + ")")
        var xMapping = ((event.x / width) * 40).toInt()
        var yMapping = ((event.y / height) * 40).toInt()
        Log.e("MODIFIED COORDINATES:", "(" + xMapping + "," + yMapping + ")")
        bitmap.setPixel(xMapping, yMapping, Color.RED)
        invalidate()
        return true
    }


}