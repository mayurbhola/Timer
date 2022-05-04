package com.mayurbhola.timer

import com.mayurbhola.timer.domain.Repository
import com.mayurbhola.timer.domain.model.TimerData
import com.mayurbhola.timer.domain.usecases.AllTimers
import com.mayurbhola.timer.domain.usecases.AllTimersImpl
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class AllTimersTest {
    @Mock
    private lateinit var repository: Repository

    private lateinit var allTimers: AllTimers

    @Before
    fun setup() {
        allTimers = AllTimersImpl(repository)

        val currentTime = Calendar.getInstance().timeInMillis
        val timerDate = TimerData(
            id = 0,
            status = 1,
            createdAtMillis = currentTime,
            modifiedAtMillis = currentTime,
            timerValueInSeconds = 1000
        )
        runBlocking {
            repository.setTimer(timerDate)
        }
    }

    @Test
    fun getAllTimerListSuccessfully() {

        allTimers.invoke()

        Mockito.verify(repository).allTimers()
    }
}