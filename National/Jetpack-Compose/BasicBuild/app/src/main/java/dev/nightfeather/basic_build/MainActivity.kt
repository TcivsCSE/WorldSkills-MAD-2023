package dev.nightfeather.basic_build

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.nightfeather.basic_build.ui.page.*
import dev.nightfeather.basic_build.ui.theme.BasicBuildTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicBuildTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = intent.getStringExtra("shortcut_key") ?: "splash"
                ) {
                    composable("splash") { SplashPage.Build(navController) }
                    composable("login") { LoginPage.Build(navController) }
                    composable("signup") { SignupPage.Build(navController) }
                    composable("home") { HomePage.Build(navController) }
                    composable("settings") { SettingsPage.Build(navController) }
                    composable("news") { NewsPage.Build(navController) }
                    composable("history") { HistoryPage.Build(navController) }
                    composable("job") { JobPage.Build(navController) }
                    composable("ticket") { TicketPage.Build(navController) }
                }
            }
        }
    }
}
