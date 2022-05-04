package com.mayurbhola.timer.domain.model

data class TimerData(
    val id: Int = 0,
    val timerValueInSeconds: Long = 0,
    val createdAtMillis: Long = 0,
    val modifiedAtMillis: Long = 0,
    //  0 = Stopped, 1 = Started
    val status: Int = 0
)
