package dev.nightfeather.unittest

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import dev.nightfeather.unittest.ui.page.MainPage
import dev.nightfeather.unittest.ui.theme.UnitTestTheme

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule


class InstrumentedTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun startTesting() {
        rule.setContent {
            MainPage.Build()
        }

        rule.onNodeWithTag("login:emailTextField").performTextInput("test@mail.com")
        rule.onNodeWithTag("login:emailTextField").assertTextEquals("test@mail.com")
        rule.onNodeWithTag("login:passwordTextField").performTextInput("password")
        rule.onNodeWithTag("login:loginButton").performClick()
        rule.onNodeWithTag("home:welcomeText").assertExists()
    }
}