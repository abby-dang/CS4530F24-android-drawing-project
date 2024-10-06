package com.example.drawingapp

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridLayout
import android.widget.GridView

class ColorGridAdapter(private val context: Context?, private val colors: List<Int>) : BaseAdapter() {

    override fun getCount(): Int = colors.size

    override fun getItem(position: Int): Any = colors[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: View(context)
        view.setBackgroundColor(colors[position])
        val layoutParams = GridLayout.LayoutParams()
        layoutParams.width = 100
        layoutParams.height = 100
        view.layoutParams = layoutParams // Set item size
        return view
    }
}
