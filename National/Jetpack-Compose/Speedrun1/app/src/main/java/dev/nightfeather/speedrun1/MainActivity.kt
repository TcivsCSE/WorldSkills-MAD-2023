package dev.nightfeather.speedrun1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.nightfeather.speedrun1.ui.page.HomePage
import dev.nightfeather.speedrun1.ui.page.LoginPage
import dev.nightfeather.speedrun1.ui.page.SignupPage
import dev.nightfeather.speedrun1.ui.page.SplashPage
import dev.nightfeather.speedrun1.ui.theme.Speedrun1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Speedrun1Theme {
                val navController = rememberNavController()

                NavHost(
                    startDestination = "login",
                    navController = navController
                ) {
                    composable("splash") { SplashPage.Build(navController) }
                    composable("login") { LoginPage.Build(navController) }
                    composable("signup") { SignupPage.Build(navController) }
                    composable("home") { HomePage.Build(navController) }
                }
            }
        }
    }
}
