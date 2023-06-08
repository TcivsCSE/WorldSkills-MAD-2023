package dev.nightfeather.basic_build.ui.page

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import dev.nightfeather.basic_build.R
import kotlinx.coroutines.delay

object SplashPage {
    @Composable
    fun Build(navController: NavController) {
        val sharedPrefs = LocalContext.current.getSharedPreferences("data", Context.MODE_PRIVATE)

        LaunchedEffect(Unit) {
            delay(350)
            if (sharedPrefs.getString("userData", null) != null) {
                navController.navigate("home")
            } else {
                navController.navigate("login")
            }
        }

        Scaffold() { paddingValues ->
            Image(
                painter = painterResource(id = R.drawable.skillsweek_icon),
                contentDescription = null,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color.Black)
            )
        }
    }
}