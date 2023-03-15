package com.example.basicbuild.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.basicbuild.ui.component.TopNavBar

object ExhibitHallInfo {
    @Composable
    fun Build(navController: NavController) {
        Column {
            TopNavBar.Build(navController, "展館介紹")
            Text(text = "Some Info (no need to impl)")
        }
    }
}