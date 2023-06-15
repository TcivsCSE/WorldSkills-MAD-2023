package dev.nightfeather.basic_build.ui.page

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.nightfeather.basic_build.Constants.baseUrl
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

object HomePage {
    @Composable
    fun Build(navController: NavController) {
        Scaffold() { paddingValues ->  
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                BuildNavButton(label = "最新消息", navTarget = "news", navController)
                BuildNavButton(label = "職類介紹", navTarget = "job", navController)
                BuildNavButton(label = "展覽購票", navTarget = "ticket", navController)
                BuildNavButton(label = "WorldSkills 歷史介紹", navTarget = "history", navController)
                BuildNavButton(label = "設定", navTarget = "settings", navController)
            }
        }
    }
    
    @Composable
    private fun BuildNavButton(label: String, navTarget: String, navController: NavController) {
        Button(
            onClick = {
                navController.navigate(navTarget)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Blue,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp)
                .padding(vertical = 30.dp)
                .height(60.dp)
        ) {
            Text(
                text = label,
                fontSize = 22.sp
            )
        }
    }
}
