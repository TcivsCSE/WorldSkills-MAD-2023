package com.example.speedrun2.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.speedrun2.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object SplashPage {
    @Composable
    fun Build(navController: NavController) {
        val coroutineScope = rememberCoroutineScope()
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                delay(350)
                navController.navigate("home") {
                    this.popUpTo(0)
                }
            }
        }
        Scaffold() {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mol_logo),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(40.dp)
                )
            }
        }
    }
}