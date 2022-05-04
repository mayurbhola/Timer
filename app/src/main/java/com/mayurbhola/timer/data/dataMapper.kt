package com.mayurbhola.timer.data

import com.mayurbhola.timer.data.local.TimerDataEntity
import com.mayurbhola.timer.domain.model.TimerData

fun TimerData.toData() = TimerDataEntity(
    id,
    timerValueInSeconds,
    createdAtMillis,
    modifiedAtMillis,
    status
)

fun TimerDataEntity.toModel() = TimerData(
    id,
    timerValueInSeconds,
    createdAtMillis,
    modifiedAtMillis,
    status
)