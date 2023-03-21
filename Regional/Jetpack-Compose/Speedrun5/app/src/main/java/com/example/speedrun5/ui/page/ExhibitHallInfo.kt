package com.example.speedrun5.ui.page

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.speedrun5.ui.widget.TopNavBar

object ExhibitHallInfoPage {
    @Composable
    fun Build(navController: NavController) {
        Scaffold(
            topBar = { TopNavBar.Build(title = "exhibitHallInfo", navController = navController) }
        ) {
            Text(
                text = "Not Impl",
                modifier = Modifier
                    .padding(it)
            )
        }
    }
}