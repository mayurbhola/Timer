package com.mayurbhola.timer.domain.model

data class ValidationResult(
    val success: Boolean,
    val errorMessage: String? = null
)