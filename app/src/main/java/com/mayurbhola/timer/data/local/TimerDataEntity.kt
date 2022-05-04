package com.mayurbhola.timer.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timers")
data class TimerDataEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "timer_value_in_seconds")
    val timerValueInSeconds: Long = 0,

    @ColumnInfo(name = "created_at")
    val createdAtMillis: Long = 0,

    @ColumnInfo(name = "modified_at")
    val modifiedAtMillis: Long = 0,

    //  0 = Stopped, 1 = Started, 2 = Paused
    @ColumnInfo(name = "status", defaultValue = "0")
    val status: Int = 0
)