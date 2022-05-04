package com.mayurbhola.timer.ui

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.mayurbhola.timer.di.DBModule
import com.mayurbhola.timer.ui.composables.MainTimerScreen
import com.mayurbhola.timer.ui.screens.main.MainTimerActivity
import com.mayurbhola.timer.util.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(DBModule::class)
class TimerTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainTimerActivity>()

    lateinit var instrumentationContext: Context

    @Before
    fun setUp() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        hiltRule.inject()
        composeRule.setContent {
            MainTimerScreen()
        }
    }

    @Test
    fun inputHourTest() {
        val testValue = "1"
        composeRule.onNodeWithTag(TestTags.INPUTFIELD_HOUR).performTextInput(testValue)

        composeRule.onNodeWithTag(TestTags.INPUTFIELD_HOUR).assert(hasText(testValue))
    }

    @Test
    fun inputMinuteTest() {
        val testValue = "20"
        composeRule.onNodeWithTag(TestTags.INPUTFIELD_MINUTE).performTextInput(testValue)

        composeRule.onNodeWithTag(TestTags.INPUTFIELD_MINUTE).assert(hasText(testValue))
    }

    @Test
    fun inputSecondTest() {
        val testValue = "30"
        composeRule.onNodeWithTag(TestTags.INPUTFIELD_SECOND).performTextInput(testValue)

        composeRule.onNodeWithTag(TestTags.INPUTFIELD_SECOND).assert(hasText(testValue))
    }

    @Test
    fun checkStartButtonDisabledInitially() {

        composeRule.onNodeWithTag(TestTags.INPUTFIELD_HOUR).performTextInput("1")
        composeRule.onNodeWithTag(TestTags.INPUTFIELD_MINUTE).performTextInput("49")
        composeRule.onNodeWithTag(TestTags.INPUTFIELD_SECOND).performTextInput("11")

        composeRule.onNodeWithTag(TestTags.BUTTON_CLICK_START1).performClick()
    }
}