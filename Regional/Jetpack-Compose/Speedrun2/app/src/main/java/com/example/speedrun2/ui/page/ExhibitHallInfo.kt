package com.example.speedrun2.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.speedrun2.ui.widget.TopNavBar

object ExhibitHallInfoPage {
    @Composable
    fun Build(navController: NavController) {
        Scaffold(
            topBar = { TopNavBar.Build(title = "", navController = navController) }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
            ) {
                Text(text = "asdfsdddfgdfgfd")
            }
        }
    }
}