package com.example.drawingapp

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.flow.Flow

/**
 *  A room database with only one entity/table, a DrawingData table.
 */
@Database(entities= [DrawingData::class], version = 2, exportSchema = false)
@TypeConverters(BitmapConverter::class)
abstract class DrawingDatabase : RoomDatabase(){
    abstract fun dao(): DrawingDAO

    // This make database a singleton class.
    companion object {
        // The instance that can be accessed.
        @Volatile
        private var INSTANCE: DrawingDatabase? = null

        /**
         * Returns an instance of the data base.
         */
        fun getDatabase(context: Context): DrawingDatabase {
            return INSTANCE ?: synchronized(this) {
                    if(INSTANCE != null) return INSTANCE!!

                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        DrawingDatabase::class.java,
                    "drawing_database"
                    ).build()

                    INSTANCE = instance
                    return instance
            }
        }
    }
}

// This DAO provides data accessing functions to the repository.
@Dao
interface DrawingDAO {
    @Insert
    suspend fun saveDrawing(data: DrawingData)

    @Query("SELECT * from drawings ORDER BY fileName DESC")
    fun allDrawings() : Flow<List<DrawingData>>
}