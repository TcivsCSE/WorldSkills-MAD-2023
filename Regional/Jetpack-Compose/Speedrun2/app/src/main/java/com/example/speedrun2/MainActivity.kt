package com.example.speedrun2

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import com.example.speedrun2.ui.page.*
import com.example.speedrun2.ui.theme.Speedrun2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            Speedrun2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("splash") { Text(text = "assadf") }
                        composable("home") { HomePage.Build(navController = navController) }
                        composable("buyTicket") { BuyTicketPage.Build(navController = navController) }
                        composable("viewTickets") { ViewTicketsPage.Build(navController = navController) }
                        composable("importTicket") { ImportTicketPage.Build(navController = navController) }
                        composable("exhibitHallInfo") { ExhibitHallInfoPage.Build(navController = navController) }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Speedrun2Theme {
        Greeting("Android")
    }
}