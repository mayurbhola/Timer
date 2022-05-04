package com.mayurbhola.timer.ui.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayurbhola.timer.di.IoDispatcher
import com.mayurbhola.timer.domain.model.TimerData
import com.mayurbhola.timer.domain.model.TimerInputState
import com.mayurbhola.timer.domain.model.TimerInputValidationEvent
import com.mayurbhola.timer.domain.usecases.AllTimers
import com.mayurbhola.timer.domain.usecases.SetTimer
import com.mayurbhola.timer.domain.usecases.UpdateTimer
import com.mayurbhola.timer.domain.usecases.ValidateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainTimerViewModel @Inject constructor(
    allTimers: AllTimers,
    private val setTimer: SetTimer,
    private val updateTimer: UpdateTimer,
    private val validateTime: ValidateTime,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val getAllTimers = allTimers.invoke()

    var timeInputValidationState by mutableStateOf(TimerInputState())

    private val timer = Timer()

    init {
        viewModelScope.launch {
            setupTimer()
            getAllTimers.collect {
                timeInputValidationState =
                    timeInputValidationState.copy(
                        listTimerData = it
                    )
            }
        }
    }

    private fun setupTimer() {
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                timeInputValidationState =
                    timeInputValidationState.copy(currentTimeMillis = Calendar.getInstance().timeInMillis)
            }
        }, 1000, 1000)
    }

    fun setTimer(timerData: TimerData) =
        viewModelScope.launch(ioDispatcher) {
            setTimer.invoke(timerData)
        }

    fun updateTimer(timerData: TimerData) =
        viewModelScope.launch(ioDispatcher) {
            updateTimer.invoke(timerData)
        }

    fun updateTimer(id: Int, status: Int, modified_at: Long) =
        viewModelScope.launch(ioDispatcher) {
            updateTimer.invoke(id, status, modified_at)
        }

    fun updateTimer(id: Int, status: Int) =
        viewModelScope.launch(ioDispatcher) {
            updateTimer.invoke(id, status)
        }

    fun timeInputToString(time: Int): String = (if (time < 0) "" else "$time")

    val focusRequesterForHourInputField = FocusRequester()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun calculateRemainingTime(timerData: TimerData, currentTimeMillis: Long): Long =
        if (timerData.status == 1) {
            val remainingTime =
                (timerData.timerValueInSeconds - ((currentTimeMillis - timerData.modifiedAtMillis) / 1000))

            if (remainingTime > 0) remainingTime else {
                updateTimer(id = timerData.id, status = 0)
                timerData.timerValueInSeconds
            }
        } else {
            timerData.timerValueInSeconds
        }

    fun onEvent(event: TimerInputValidationEvent) {
        when (event) {
            is TimerInputValidationEvent.HourChanged -> {
                timeInputValidationState = timeInputValidationState.copy(hours = event.hour)
                checkValidation()
            }
            is TimerInputValidationEvent.MinuteChanged -> {
                timeInputValidationState = timeInputValidationState.copy(minutes = event.minute)
                checkValidation()
            }
            is TimerInputValidationEvent.SecondChanged -> {
                timeInputValidationState = timeInputValidationState.copy(seconds = event.second)
                checkValidation()
            }
            is TimerInputValidationEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun checkValidation(): Boolean {

        val hourResult = validateTime.checkHour(timeInputValidationState.hours)
        val minuteResult = validateTime.checkMinute(timeInputValidationState.minutes)
        val secondResult = validateTime.checkSecond(timeInputValidationState.seconds)

        val inputtedTotalTime = timeInputValidationState.hours.times(3600L)
            .plus(timeInputValidationState.minutes.times(60L))
            .plus(timeInputValidationState.seconds)

        val totalTimerInSecondsResult = validateTime.checkTotalTimerSeconds(inputtedTotalTime)

        val hasError = listOf(
            hourResult,
            minuteResult,
            secondResult,
            totalTimerInSecondsResult
        ).any { !it.success }

        timeInputValidationState = timeInputValidationState.copy(
            totalTimerInSeconds = inputtedTotalTime,
            errorHour = hourResult.errorMessage,
            errorMinute = minuteResult.errorMessage,
            errorSecond = secondResult.errorMessage,
            errorTotalTimerInSeconds = totalTimerInSecondsResult.errorMessage,
            validated = !hasError
        )

        return hasError
    }

    private fun submitData() {
        if (checkValidation()) {
            return
        }

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    fun clear() {
        timeInputValidationState = timeInputValidationState.copy(
            hours = 0,
            minutes = 0,
            seconds = 0,
            totalTimerInSeconds = 0,
            errorHour = "",
            errorMinute = "",
            errorSecond = "",
            errorTotalTimerInSeconds = "",
            currentTimeMillis = 0L,
            validated = false
        )

        focusRequesterForHourInputField.requestFocus()
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        timer.purge()
    }
}