package com.example.speedrun3.ui.page

import android.app.Instrumentation.ActivityResult
import android.graphics.BitmapFactory
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.speedrun3.ui.widget.TopNavBar
import com.google.zxing.*
import com.google.zxing.common.*

object ImportTicketPage {
    @Composable
    fun Build(navController: NavController) {
        val decodedData = remember {
            mutableStateOf("")
        }
        val contentResolver = LocalContext.current.contentResolver
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) {
            if (it != null) {
                val imageBitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(it)).asImageBitmap()
                val imageBuffer = IntArray(imageBitmap.width * imageBitmap.height)
                imageBitmap.readPixels(imageBuffer)
                val imageSource = RGBLuminanceSource(
                    imageBitmap.height,
                    imageBitmap.width,
                    imageBuffer
                )
                try {
                    decodedData.value = MultiFormatReader().decodeWithState(BinaryBitmap(HybridBinarizer(imageSource))).text
                } catch (_: NotFoundException) {

                }
            }
        }
        Scaffold(
            topBar = { TopNavBar.Build(title = "importTicket", navController = navController) }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
            ) {
                Text(text = decodedData.value)
                Button(
                    onClick = {
                        launcher.launch("image/*")
                    }
                ) {
                    Text(text = "import")
                }
            }
        }
    }
}
