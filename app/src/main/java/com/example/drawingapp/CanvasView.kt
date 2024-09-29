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
    private val paint = Paint()

    private val rect: Rect by lazy { Rect(0,0,width, height) }

    private lateinit var viewModel: DrawingViewModel

    override fun onDraw(canvas: Canvas) {
        Log.e("WOW", "DRAW")
        super.onDraw(canvas)
        paint.color = Color.WHITE
        paint.setAntiAlias(false);
        paint.setFilterBitmap(false);
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        canvas.drawBitmap(bitmap, null, rect, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        viewModel.drawPixel(event.x, event.y, width, height)
        return true
    }

    public fun drawPixel(x: Int, y: Int){
        bitmap.setPixel(x, y, Color.GREEN)
        invalidate()
    }


}