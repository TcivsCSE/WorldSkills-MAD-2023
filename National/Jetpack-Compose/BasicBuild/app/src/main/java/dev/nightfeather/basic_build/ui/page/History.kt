package dev.nightfeather.basic_build.ui.page

import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController

object HistoryPage {
    @Composable
    fun Build(navController: NavController) {
        Scaffold(
            topBar = {
                Row() {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                    Spacer(Modifier.fillMaxWidth())
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                AndroidView(
                    factory = {
                        WebView(it).apply {
                            loadUrl("https://stackoverflow.com/questions/48838992/how-to-convert-date-string-to-timestamp-in-kotlin")
                        }
                    }
                )
            }
        }
    }
}