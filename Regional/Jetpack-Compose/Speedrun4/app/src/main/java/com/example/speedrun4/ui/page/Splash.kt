package com.example.speedrun4.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.speedrun4.R
import kotlinx.coroutines.delay

object SplashPage {
    @Composable
    fun Build(navController: NavController) {
        LaunchedEffect(Unit) {
            delay(350)
            navController.navigate("home") {
                this.popUpTo(0)
            }
        }
        Scaffold {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mol),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(80.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}
