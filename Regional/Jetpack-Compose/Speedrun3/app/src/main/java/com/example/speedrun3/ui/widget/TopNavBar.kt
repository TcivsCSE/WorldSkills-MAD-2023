package com.example.speedrun3.ui.widget

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

object TopNavBar {
    @Composable
    fun Build(title: String, navController: NavController) {
        TopAppBar(
            backgroundColor = Color.White,
            contentColor = Color.Black
        ) {
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
            Text(
                text = title,
                color = Color.Black,
                fontSize = 18.sp
            )
        }
    }
}
