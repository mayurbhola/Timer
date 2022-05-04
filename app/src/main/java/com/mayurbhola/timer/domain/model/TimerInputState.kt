package com.mayurbhola.timer.domain.model

data class TimerInputState(
    var hours: Int = -1,
    var minutes: Int = -1,
    var seconds: Int = -1,
    var totalTimerInSeconds: Long = -1,
    val errorHour: String? = null,
    val errorMinute: String? = null,
    val errorSecond: String? = null,
    val errorTotalTimerInSeconds: String? = null,
    val validated: Boolean = false
)
