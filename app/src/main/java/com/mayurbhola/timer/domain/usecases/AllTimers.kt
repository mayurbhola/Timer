package com.mayurbhola.timer.domain.usecases

import com.mayurbhola.timer.domain.model.TimerData
import kotlinx.coroutines.flow.Flow

interface AllTimers {
    operator fun invoke(): Flow<List<TimerData>>
}