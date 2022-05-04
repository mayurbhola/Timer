package com.mayurbhola.timer.domain.usecases

import com.mayurbhola.timer.domain.Repository
import com.mayurbhola.timer.domain.model.TimerData

class UpdateTimerImpl(val repository: Repository) : UpdateTimer {
    override suspend fun invoke(timerData: TimerData) = repository.updateTimer(timerData)
    override suspend fun invoke(id: Int, status: Int, modified_at: Long) =
        repository.updateTimerStatus(id, status, modified_at)

    override suspend fun invoke(id: Int, status: Int) =
        repository.updateTimerStatus(id, status)
}