package dev.nightfeather.basic_build.ui.page

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

object HomePage {
    @Composable
    fun Build(navController: NavController) {
        val sharedPrefs = LocalContext.current.getSharedPreferences("data", Context.MODE_PRIVATE)
        
        Scaffold() { paddingValues ->  
            Column(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                Button(
                    onClick = {
                        sharedPrefs
                            .edit()
                            .remove("userData")
                            .apply()
                        navController.navigate("login") {
                            popUpTo(0)
                        }
                    }
                ) {
                    Text(text = "Logout")
                }
            }
        }
    }
}
