package com.mayurbhola.timer.domain.usecases

import javax.inject.Inject

data class TimerUseCases @Inject constructor(
    val allTimers: AllTimers,
    val setTimer: SetTimer,
    val updateTimer: UpdateTimer,
    val validateTime: ValidateTime
)