package com.mayurbhola.timer.ui.screens.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mayurbhola.timer.ui.composables.MainTimerScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainTimerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTimerScreen()
        }
    }
}