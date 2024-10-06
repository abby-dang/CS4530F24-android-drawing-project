package com.example.drawingapp

import androidx.lifecycle.testing.TestLifecycleOwner
import org.junit.Test

import org.junit.Assert.*

/**
 * Unit Tests for drawing app.
 * Makes sure functions are working as intended.
 */
class UnitTests {
    private val vm = DrawingViewModel();
    val lifecycleOwner = TestLifecycleOwner();
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun basicSizeTest() {

    }
}