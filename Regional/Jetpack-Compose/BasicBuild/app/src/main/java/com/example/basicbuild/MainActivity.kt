package com.example.basicbuild

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.basicbuild.ui.page.*
import com.example.basicbuild.ui.theme.BasicBuildTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {}

        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {}

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.CAMERA
            ) -> {}

            else -> requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }

        setContent {
            BasicBuildTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.tainex_ico),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        alpha = animateFloatAsState(
                            targetValue = if (navController.currentDestination == null) 1.0f else 0.0f,
                            animationSpec = tween(durationMillis = 750)
                        ).value,
                        modifier = Modifier
                            .padding(horizontal = 100.dp)
                    )
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier
                            .alpha(
                                animateFloatAsState(
                                    targetValue = if (navController.currentDestination == null) 0.0f else 1.0f,
                                    animationSpec = tween(durationMillis = 750)
                                ).value
                            )
                    ) {
                        composable("home") { HomePage.Build(navController) }
                        composable("exhibitHallInfo") { ExhibitHallInfo.Build(navController) }
                        composable("buyTicket") { BuyTicketPage.Build(navController) }
                        composable("viewTickets") { ViewTicketsPage.Build(navController) }
                        composable("scanQrcode") { ScanQrcodePage.Build(navController) }
                    }
                }
            }
        }
    }
}