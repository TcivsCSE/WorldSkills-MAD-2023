package com.example.speedrun1.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.speedrun1.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object SplashPage {
    @Composable
    fun Build(navController: NavController) {
        val coroutineScope = rememberCoroutineScope()
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                delay(500)
                navController.navigate("home") {
                    popUpTo(0)
                }
            }
        }
        Scaffold() {
            Image(
                painter = painterResource(id = R.drawable.tainex_ico),
                contentDescription = null,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .padding(100.dp)
            )
        }

    }
}