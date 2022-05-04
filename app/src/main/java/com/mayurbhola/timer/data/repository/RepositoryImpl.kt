package com.mayurbhola.timer.data.repository

import com.mayurbhola.timer.data.local.TimerDatabase
import com.mayurbhola.timer.data.toData
import com.mayurbhola.timer.data.toModel
import com.mayurbhola.timer.domain.Repository
import com.mayurbhola.timer.domain.model.TimerData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl constructor(timerDatabase: TimerDatabase) : Repository {

    private var timerDao = timerDatabase.timerDao()

    override suspend fun setTimer(timerData: TimerData) {
        timerDao.insertNewTimer(timerData.toData())
    }

    override fun allTimers(): Flow<List<TimerData>> =
        timerDao.allTimers().map { list -> list.map { it.toModel() } }

    override suspend fun updateTimerStatus(id: Int, status: Int, modified_at: Long) {
        timerDao.updateTimer(id, status, modified_at)
    }

    override suspend fun updateTimerStatus(id: Int, status: Int) {
        timerDao.updateTimer(id, status)
    }

    override suspend fun updateTimer(timerData: TimerData) {
        timerDao.updateTimer(timerData.toData())
    }
}