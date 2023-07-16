package dev.nightfeather.unittest.ui.page

import android.os.Build
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.nightfeather.unittest.ui.theme.UnitTestTheme

object MainPage {
    @Composable
    fun Build() {
        UnitTestTheme {
            val navController = rememberNavController()
            NavHost(
                startDestination = "login",
                navController = navController
            ) {
                composable("login") { LoginPage.Build(navController) }
                composable("home") { HomePage.Build(navController) }
            }
        }
    }
}