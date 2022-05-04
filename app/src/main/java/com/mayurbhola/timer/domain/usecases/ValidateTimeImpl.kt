package com.mayurbhola.timer.domain.usecases

import com.mayurbhola.timer.domain.model.ValidationResult

class ValidateTimeImpl : ValidateTime {

    override fun checkHour(hour: Int): ValidationResult {
        if (hour < 0) {
            return ValidationResult(
                success = false,
                errorMessage = "Please enter valid hours."
            )
        }
        return ValidationResult(
            success = true
        )
    }

    override fun checkMinute(minute: Int): ValidationResult {
        if (minute < 0) {
            return ValidationResult(
                success = false,
                errorMessage = "Please enter valid minutes."
            )
        }
        return ValidationResult(
            success = true
        )
    }

    override fun checkSecond(second: Int): ValidationResult {
        if (second < 0) {
            return ValidationResult(
                success = false,
                errorMessage = "Please enter valid seconds."
            )
        }
        return ValidationResult(
            success = true
        )
    }

    override fun checkTotalTimerSeconds(totalTimerSeconds: Long): ValidationResult {
        if (totalTimerSeconds < 30) {
            return ValidationResult(
                success = false,
                errorMessage = "Please set minimum timer for 30 seconds."
            )
        }
        return ValidationResult(
            success = true
        )
    }
}