package com.mayurbhola.timer.domain.usecases

import com.mayurbhola.timer.domain.model.TimerData

interface SetTimer {
    suspend operator fun invoke(timerData: TimerData)
}