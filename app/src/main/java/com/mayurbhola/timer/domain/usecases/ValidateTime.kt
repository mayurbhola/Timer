package com.mayurbhola.timer.domain.usecases

import com.mayurbhola.timer.domain.model.ValidationResult

interface ValidateTime {
    fun checkHour(hour: Int): ValidationResult
    fun checkMinute(minute: Int): ValidationResult
    fun checkSecond(second: Int): ValidationResult
    fun checkTotalTimerSeconds(totalTimerSeconds: Long): ValidationResult
}