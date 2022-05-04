package com.mayurbhola.timer.domain.usecases

import com.mayurbhola.timer.domain.Repository
import com.mayurbhola.timer.domain.model.TimerData

class SetTimerImpl(private val repository: Repository) : SetTimer {
    override suspend fun invoke(timerData: TimerData) {
        repository.setTimer(timerData)
    }
}