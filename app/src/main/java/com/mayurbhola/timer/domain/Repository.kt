package com.mayurbhola.timer.domain

import com.mayurbhola.timer.domain.model.TimerData
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun setTimer(timerData: TimerData)

    fun allTimers(): Flow<List<TimerData>>

    suspend fun updateTimerStatus(id: Int, status: Int)

    suspend fun updateTimerStatus(id: Int, status: Int, modified_at: Long)

    suspend fun updateTimer(timerData: TimerData)
}