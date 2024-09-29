package com.example.drawingapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

/**
 * This activity shows the different fragments of the drawing App.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myViewModel : DrawingViewModel by viewModels()

        // Open the drawing fragment immediately. (CHANGE LATER)
        val drawingFragment = DrawingFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main, drawingFragment)
        fragmentTransaction.commit()
    }
}