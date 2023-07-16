package dev.nightfeather.speedrun1.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.nightfeather.speedrun1.R
import kotlinx.coroutines.delay

object SplashPage {
    @Composable
    fun Build(navController: NavController) {
        LaunchedEffect(Unit) {
            delay(350)
            navController.navigate("login") {
                this.popUpTo(0)
            }
        }

        Scaffold(
            backgroundColor = Color.DarkGray
        ) {
            Image(
                painter = painterResource(id = R.drawable.skillsweek_icon),
                contentDescription = null,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .padding(60.dp)
            )
        }
    }
}