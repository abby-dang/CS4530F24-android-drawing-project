package com.example.drawingapp

import android.graphics.Color
import androidx.core.graphics.alpha
import androidx.lifecycle.testing.TestLifecycleOwner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.drawingapp", appContext.packageName)
    }

    @Test
    fun testBasicBitmapEvent() {
        val vm = DrawingViewModel()
        runBlocking {
            val lifecycleOwner = TestLifecycleOwner()
            var callbackFired = false

            lifecycleOwner.run {
                withContext(Dispatchers.Main) {
                    vm.bitmap.observe(lifecycleOwner) {
                        callbackFired = true
                    }

                    vm.drawPixel(30.0F, 30.0F, 100, 100)
                    assertTrue(callbackFired)
                }
            }

        }

    }

    @Test
    fun testBasicBrushSizeChange() {
        val vm = DrawingViewModel()
        runBlocking {
            val lifecycleOwner = TestLifecycleOwner()
            lifecycleOwner.run {
                withContext(Dispatchers.Main) {
                    vm.setSize(30.0F)
                    assertEquals((((30.0F/100f) * (40/2)).toInt() / 2), vm.getSize())
                }
            }

        }

    }

    @Test
    fun testBasicBrushColorChange() {
        val vm = DrawingViewModel()
        runBlocking {
            val lifecycleOwner = TestLifecycleOwner()
            lifecycleOwner.run {
                withContext(Dispatchers.Main) {
                    vm.setColor(100)
                    assertEquals(Color.argb(255, Color.red(100), Color.green(100), Color.blue(100)), vm.getColor())
                }
            }

        }
    }

    @Test
    fun testBasicEraseChange() {
        val vm = DrawingViewModel()
        runBlocking {
            val lifecycleOwner = TestLifecycleOwner()
            lifecycleOwner.run {
                withContext(Dispatchers.Main) {
                    vm.setEraser()
                    assertEquals(0, vm.getColor().alpha)
                }
            }

        }
    }

    @Test
    fun testBasicBrushChange() {
        val vm = DrawingViewModel()
        runBlocking {
            val lifecycleOwner = TestLifecycleOwner()
            lifecycleOwner.run {
                withContext(Dispatchers.Main) {
                    vm.setBrush()
                    assertEquals(255, vm.getColor().alpha)
                }
            }

        }
    }
}