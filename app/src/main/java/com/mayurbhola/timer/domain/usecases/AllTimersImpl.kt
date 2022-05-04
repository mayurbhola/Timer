package com.mayurbhola.timer.domain.usecases

import com.mayurbhola.timer.domain.Repository

class AllTimersImpl(private val repository: Repository) : AllTimers {
    override operator fun invoke() = repository.allTimers()
}