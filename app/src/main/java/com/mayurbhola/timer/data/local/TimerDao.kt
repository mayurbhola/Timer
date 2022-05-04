package com.mayurbhola.timer.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewTimer(timerDataEntity: TimerDataEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTimer(timerDataEntity: TimerDataEntity)

    @Query("UPDATE timers SET status = :status, modified_at = :modified_at WHERE id =:id")
    suspend fun updateTimer(id: Int, status: Int, modified_at: Long)

    @Query("UPDATE timers SET status = :status WHERE id =:id")
    suspend fun updateTimer(id: Int, status: Int)

    @Query("SELECT * FROM timers ORDER BY modified_at DESC, status ASC")
    fun allTimers(): Flow<List<TimerDataEntity>>
}