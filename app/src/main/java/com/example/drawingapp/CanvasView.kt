package com.example.drawingapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * A custom view object that paints pixels to the
 * view model and displays the view model's bitmap.
 */
class CanvasView(context: Context, attrs: AttributeSet? = null, var viewModel: DrawingViewModel) : View(context, attrs) {

    private val rect: Rect by lazy { Rect(0,0,width, height) }
    private val paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Paint the screen white.
        paint.color = Color.WHITE
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        // Paint the bitmap from the view model.
        paint.isFilterBitmap = false;
        viewModel.bitmap.value?.let { canvas.drawBitmap(it, null, rect, paint) }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        viewModel.drawPixel(event.x, event.y, width, height)
        invalidate()
        return true
    }
}