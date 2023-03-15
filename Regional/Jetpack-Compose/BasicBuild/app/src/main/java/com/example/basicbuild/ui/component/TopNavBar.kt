package com.example.basicbuild.ui.component

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

object TopNavBar {
    @Composable
    fun Build(navController: NavController, title: String, modifier: Modifier = Modifier) {
        TopAppBar(
            title = { Text(
                text = title,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp,
                fontSize = 22.sp,
                modifier = modifier
            ) },
            navigationIcon = {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        null,
                        tint = Color.Black
                    )
                }
            },
            backgroundColor = Color.White,
            elevation = 0.dp
        )
    }
}