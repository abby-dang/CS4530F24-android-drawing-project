package com.example.drawingapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.util.Date

/**
 * This class contains methods that convert bitmaps to byte arrays and vice versa.
 * This is because room databases can't store bitmaps. These methods were written
 * with the help of ChatGTP.
 */
class BitmapConverter {
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): ByteArray? {
        if (bitmap == null) return null
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray?): Bitmap? {
        if (byteArray == null) return null
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}

@Entity(tableName="drawings")
data class DrawingData(var bitmap: Bitmap, var fileName: String){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}