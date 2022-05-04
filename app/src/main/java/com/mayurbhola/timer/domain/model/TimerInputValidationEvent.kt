package com.mayurbhola.timer.domain.model

sealed class TimerInputValidationEvent {
    data class HourChanged(val hour: Int) : TimerInputValidationEvent()
    data class MinuteChanged(val minute: Int) : TimerInputValidationEvent()
    data class SecondChanged(val second: Int) : TimerInputValidationEvent()
    object Submit : TimerInputValidationEvent()
}