package com.example.speedrun1

import android.content.pm.PackageInfo
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.speedrun1.ui.page.*
import com.example.speedrun1.ui.theme.Speedrun1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val requestPermissionsLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {}

        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageInfo.REQUESTED_PERMISSION_GRANTED -> {}

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.CAMERA
            ) -> {}

            else -> requestPermissionsLauncher.launch(android.Manifest.permission.CAMERA)
        }

        setContent {
            Speedrun1Theme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "viewTickets"
                    ) {
                        composable("splash") { SplashPage.Build(navController = navController) }
                        composable("home") { HomePage.Build(navController = navController) }
                        composable("buyTicket") { BuyTicketPage.Build(navController = navController) }
                        composable("viewTickets") { ViewTicketsPage.Build(navController = navController) }
                        composable("scanQrcode") { ScanQrcodePage.Build(navController = navController) }
                    }
                }
            }
        }
    }
}