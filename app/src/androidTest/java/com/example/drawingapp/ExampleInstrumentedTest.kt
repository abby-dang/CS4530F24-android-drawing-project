package com.example.drawingapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.alpha
import androidx.lifecycle.testing.TestLifecycleOwner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private lateinit var drawingDao: DrawingDAO
    private lateinit var db: DrawingDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DrawingDatabase::class.java).build()
        drawingDao = db.dao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.drawingapp", appContext.packageName)
    }


    @Test
    fun testDatabaseSave() {
        val bitmap = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888)
        val drawingData = DrawingData(bitmap, "helloworld")
        runBlocking {
            val lifeCycleOwner = TestLifecycleOwner()
            lifeCycleOwner.run {
                withContext(Dispatchers.IO) {
                    drawingDao.saveDrawing(drawingData)
                    val findDrawing = drawingDao.checkIfExists("helloworld")
                    val drawingExists = findDrawing > 0
                    assertTrue(drawingExists)
                }

            }
        }
    }

    @Test
    fun testDatabaseDelete() {
        val bitmap = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888)
        val drawingData = DrawingData(bitmap, "helloworld")
        runBlocking {
            val lifeCycleOwner = TestLifecycleOwner()
            lifeCycleOwner.run {
                withContext(Dispatchers.IO) {
                    drawingDao.saveDrawing(drawingData)
                    val prevNumDrawing = drawingDao.totalNumDrawings()
                    drawingDao.deleteDrawing("helloworld")
                    val currNumDrawing = drawingDao.totalNumDrawings()
                    assertNotEquals(prevNumDrawing, currNumDrawing)
                }

            }
        }
    }

    @Test
    fun testDatabaseDataRetrieval() {
        val bitmap = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888)
        val drawingData = DrawingData(bitmap, "helloworld")
        runBlocking {
            val lifeCycleOwner = TestLifecycleOwner()
            lifeCycleOwner.run {
                withContext(Dispatchers.IO) {
                    drawingDao.saveDrawing(drawingData)
                    val actualDrawingData = drawingDao.retrieveDrawing("helloworld")
                    assertNotEquals(drawingData, actualDrawingData)
                }

            }
        }
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
                    vm.setCircleBrush()
                    assertEquals(255, vm.getColor().alpha)
                }
            }

        }
    }

}