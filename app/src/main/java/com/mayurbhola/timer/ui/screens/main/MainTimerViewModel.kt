package com.mayurbhola.timer.ui.screens.main

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayurbhola.timer.domain.model.TimerData
import com.mayurbhola.timer.domain.model.TimerInputState
import com.mayurbhola.timer.domain.model.TimerInputValidationEvent
import com.mayurbhola.timer.domain.usecases.AllTimers
import com.mayurbhola.timer.domain.usecases.SetTimer
import com.mayurbhola.timer.domain.usecases.UpdateTimer
import com.mayurbhola.timer.domain.usecases.ValidateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainTimerViewModel @Inject constructor(
    allTimers: AllTimers,
    private val setTimer: SetTimer,
    private val updateTimer: UpdateTimer,
    private val validateTime: ValidateTime
) : ViewModel() {
    var getAllTimers = allTimers.invoke()

    fun setTimer(timerData: TimerData) =
        viewModelScope.launch(Dispatchers.IO) {
            setTimer.invoke(timerData)
        }

    fun updateTimer(timerData: TimerData) =
        viewModelScope.launch(Dispatchers.IO) {
            updateTimer.invoke(timerData)
        }

    fun updateTimer(id: Int, status: Int, modified_at: Long) =
        viewModelScope.launch(Dispatchers.IO) {
            updateTimer.invoke(id, status, modified_at)
        }

    fun updateTimer(id: Int, status: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            updateTimer.invoke(id, status)
        }

    fun timeInputToString(time: Int): String = (if (time < 0) "" else "$time")

    val focusRequesterForHourInputField = FocusRequester()

    private val countDownTimersMap = mutableStateMapOf<Int, CountDownTimer?>()

    private val timerDataState = mutableStateMapOf<Int, TimerData>()
    val timerRemainingValue = mutableStateMapOf<Int, Long>()

    fun getStartedTimers(): Map<Int, TimerData> =
        timerDataState.filterValues { v -> v.status == 1 }

    var timeInputValidationState by mutableStateOf(TimerInputState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun updateTimerRemainingValue(timerData: TimerData) {
        timerDataState[timerData.id] = timerData
        if (timerData.status == 1) {
            val remainingTime =
                (timerData.timerValueInSeconds - ((Calendar.getInstance().timeInMillis - timerData.modifiedAtMillis) / 1000))
            timerRemainingValue[timerData.id] =
                if (remainingTime > 0) remainingTime else timerData.timerValueInSeconds
        } else {
            timerRemainingValue[timerData.id] = timerData.timerValueInSeconds
        }
    }

    fun updateCountDownTimers(startedTimersMap: State<Map<Int, TimerData>>) {
        startedTimersMap.value.forEach { (id, timerData) ->
            countDownTimersMap[id]?.cancel()
            countDownTimersMap[id] = getCountDownTimer(timerData)
        }
    }

    private fun getCountDownTimer(timerData: TimerData): CountDownTimer {
        val cdt =
            object : CountDownTimer(timerData.timerValueInSeconds.times(1000), 1000) {
                override fun onTick(tick: Long) {
                    try {
                        if (timerData.status == 1) {
                            val remainingTime =
                                (timerData.timerValueInSeconds - ((Calendar.getInstance().timeInMillis - timerData.modifiedAtMillis) / 1000))

                            if (remainingTime > 0) {
                                timerRemainingValue[timerData.id] = remainingTime
                            } else {
                                updateTimer(id = timerData.id, status = 0)
                                timerRemainingValue[timerData.id] = timerData.timerValueInSeconds
                                cancel()
                            }
                        } else {
                            updateTimer(id = timerData.id, status = 0)
                            timerRemainingValue[timerData.id] = timerData.timerValueInSeconds
                            cancel()
                        }
                    } catch (e: Exception) {
                        Log.e("TAG", "Exception == ${e.message}")
                    }
                }

                override fun onFinish() {}
            }
        cdt.start()

        return cdt
    }

    fun clear() {
        timeInputValidationState = timeInputValidationState.copy(
            hours = -1,
            minutes = -1,
            seconds = -1,
            totalTimerInSeconds = -1,
            errorHour = "",
            errorMinute = "",
            errorSecond = "",
            errorTotalTimerInSeconds = "",
            validated = false
        )

        focusRequesterForHourInputField.requestFocus()
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

        val inputtedTotalTime = timeInputValidationState.hours.times(3600)
            .plus(timeInputValidationState.minutes.times(60))
            .plus(timeInputValidationState.seconds)
            .toLong()

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

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }
}