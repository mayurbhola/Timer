package com.mayurbhola.timer.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TimerDataEntity::class], version = 1, exportSchema = false)
abstract class TimerDatabase : RoomDatabase() {
    abstract fun timerDao(): TimerDao

    companion object {
        private const val databaseName = "TimerDatabase"

        fun buildDatabase(context: Context): TimerDatabase {
            return Room
                .databaseBuilder(context, TimerDatabase::class.java, databaseName)
                .build()
        }
    }
}