package com.example.speedrun7

import android.os.Bundle
import android.view.View
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
import com.example.speedrun7.ui.page.*
import com.example.speedrun7.ui.theme.Speedrun7Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Speedrun7Theme {
                NavHost(
                    navController = navController,
                    startDestination = "splash"
                ) {
                    composable("splash") { SplashPage.Build(navController = navController) }
                    composable("home") { HomePage.Build(navController = navController) }
                    composable("exhibitHallInfo") { ExhibitHallInfoPage.Build(navController = navController)}
                    composable("buyTicket") { BuyTicketPage.Build(navController = navController)}
                    composable("importTicket") { ImportTicketPage.Build(navController = navController)}
                    composable("viewTickets") { ViewTicketsPage.Build(navController = navController)}
                }
            }
        }
    }
}