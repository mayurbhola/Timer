package com.mayurbhola.timer.domain.usecases

import com.mayurbhola.timer.domain.model.TimerData

interface UpdateTimer {
    suspend fun invoke(timerData: TimerData)
    suspend fun invoke(id: Int, status: Int, modified_at: Long)
    suspend fun invoke(id: Int, status: Int)
}