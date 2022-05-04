package com.mayurbhola.timer.ui.composables

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mayurbhola.timer.domain.model.TimerData
import com.mayurbhola.timer.ui.screens.main.MainTimerViewModel
import com.mayurbhola.timer.util.TestTags
import kotlinx.coroutines.delay
import java.util.*

@Composable
fun TimerRow(
    timerData: TimerData,
    mainTimerViewModel: MainTimerViewModel = hiltViewModel()
) {
    mainTimerViewModel.updateTimerRemainingValue(timerData = timerData)
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(all = 12.dp)) {

        Text(
            text = DateUtils.formatElapsedTime(
                mainTimerViewModel.timerRemainingValue[timerData.id] ?: 0
            ),
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp),
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.Monospace,
            style = if (timerData.status == 1) MaterialTheme.typography.h4
            else MaterialTheme.typography.h6
        )
        Button(
            modifier = Modifier.testTag(TestTags.BUTTON_CLICK_START2),
            onClick = {
                if (timerData.status == 1) {

                    mainTimerViewModel.updateTimer(id = timerData.id, status = 0)
                } else {

                    mainTimerViewModel.updateTimer(
                        id = timerData.id,
                        status = 1,
                        modified_at = Calendar.getInstance().timeInMillis
                    )
                }
            }
        ) {
            Text(if (timerData.status == 0) "Start" else "Stop")
        }
    }

    val updated =
        rememberUpdatedState(newValue = mainTimerViewModel.getStartedTimers())

    LaunchedEffect(key1 = updated, block = {
        delay(1000)
        mainTimerViewModel.updateCountDownTimers(updated)
    })
}

@Preview
@Composable
fun TimerRowPreview() {
    TimerRow(
        timerData = TimerData(0, 100, 0, 0, 0)
    )
}