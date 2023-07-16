package dev.nightfeather.unittest.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavController

object HomePage {
    @Composable
    fun Build(navController: NavController) {
        Scaffold() { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                Text(
                    text = "Welcome, Test01!",
                    modifier = Modifier
                        .testTag("home:welcomeText")
                )
            }
        }
    }
}