package com.mayurbhola.timer.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mayurbhola.timer.R
import com.mayurbhola.timer.domain.model.TimerData
import com.mayurbhola.timer.domain.model.TimerInputValidationEvent
import com.mayurbhola.timer.ui.screens.main.MainTimerViewModel
import com.mayurbhola.timer.ui.theme.TimerTheme
import com.mayurbhola.timer.util.TestTags
import kotlinx.coroutines.flow.collect
import java.util.*

@Composable
fun MainTimerScreen(mainTimerViewModel: MainTimerViewModel = hiltViewModel()) {

    TimerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.primarySurface
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = stringResource(id = R.string.app_name))
                        }
                    )
                }, content = {

                    val state = mainTimerViewModel.timeInputValidationState
                    LaunchedEffect(key1 = Unit) {
                        mainTimerViewModel.validationEvents.collect { e ->
                            when (e) {
                                is MainTimerViewModel.ValidationEvent.Success -> {
                                    val t = Calendar.getInstance().timeInMillis
                                    mainTimerViewModel.setTimer(
                                        TimerData(
                                            timerValueInSeconds = mainTimerViewModel.timeInputValidationState.totalTimerInSeconds,
                                            createdAtMillis = t, modifiedAtMillis = t, status = 1
                                        )
                                    )
                                    mainTimerViewModel.clear()
                                }
                            }
                        }
                    }

                    Column {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            TimeInputField(
                                hintText = "Hours",
                                hintLabel = "00h",
                                modifier = Modifier
                                    .testTag(TestTags.INPUTFIELD_HOUR)
                                    .weight(1f)
                                    .focusRequester(mainTimerViewModel.focusRequesterForHourInputField),
                                {
                                    mainTimerViewModel.onEvent(
                                        TimerInputValidationEvent.HourChanged(
                                            it
                                        )
                                    )
                                }, textValue = mainTimerViewModel.timeInputToString(state.hours)
                            )

                            TimeInputField(
                                hintText = "Minutes",
                                hintLabel = "00m",
                                modifier = Modifier
                                    .testTag(TestTags.INPUTFIELD_MINUTE)
                                    .weight(1f),
                                {
                                    mainTimerViewModel.onEvent(
                                        TimerInputValidationEvent.MinuteChanged(
                                            it
                                        )
                                    )
                                }, textValue = mainTimerViewModel.timeInputToString(state.minutes)
                            )

                            TimeInputField(
                                hintText = "Seconds",
                                hintLabel = "00s",
                                modifier = Modifier
                                    .testTag(TestTags.INPUTFIELD_SECOND)
                                    .weight(1f),
                                {
                                    mainTimerViewModel.onEvent(
                                        TimerInputValidationEvent.SecondChanged(
                                            it
                                        )
                                    )
                                }, textValue = mainTimerViewModel.timeInputToString(state.seconds)
                            )
                        }

                        if (!state.validated) {
                            if (!state.errorHour.isNullOrBlank()) {
                                Text(text = "${state.errorHour}", color = Color.Red)
                            } else if (!state.errorMinute.isNullOrBlank()) {
                                Text(text = "${state.errorMinute}", color = Color.Red)
                            } else if (!state.errorSecond.isNullOrBlank()) {
                                Text(text = "${state.errorSecond}", color = Color.Red)
                            } else if (!state.errorTotalTimerInSeconds.isNullOrBlank()) {
                                Text(text = "${state.errorTotalTimerInSeconds}", color = Color.Red)
                            }
                        }

                        Button(
                            onClick = {
                                mainTimerViewModel.onEvent(
                                    TimerInputValidationEvent.Submit
                                )
                            },
                            Modifier
                                .testTag(TestTags.BUTTON_CLICK_START1)
                                .align(Alignment.End)
                                .padding(end = 12.dp),
                            enabled = (state.validated)
                        ) {
                            Text("Start!")
                        }
                        Text(
                            if (state.listTimerData.isNotEmpty()) "Timers" else "No Timers",
                            Modifier.padding(all = 12.dp),
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.SansSerif,
                            style = MaterialTheme.typography.h6
                        )

                        val listState = rememberLazyListState()

                        LazyColumn(
                            state = listState,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(state.listTimerData) { timer ->
                                Divider()
                                TimerRow(timerData = timer, state.currentTimeMillis)
                            }
                        }
                    }
                })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainTimerScreen()
}