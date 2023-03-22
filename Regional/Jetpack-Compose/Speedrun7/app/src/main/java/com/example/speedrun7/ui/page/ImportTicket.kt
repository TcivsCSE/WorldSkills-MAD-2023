package com.example.speedrun7.ui.page

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.speedrun7.ui.widget.TopNavBar
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.HybridBinarizer

object ImportTicketPage {
    @Composable
    fun Build(navController: NavController) {
        val qrcodeDecodeResult = remember {
            mutableStateOf("Empty")
        }
        val contentResolver = LocalContext.current.contentResolver
        val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                val qrcodeBitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(it)).asImageBitmap()
                val imageBuffer = IntArray(qrcodeBitmap.width * qrcodeBitmap.height)
                qrcodeBitmap.readPixels(imageBuffer)
                val qrcodeSource = RGBLuminanceSource(
                    qrcodeBitmap.width,
                    qrcodeBitmap.height,
                    imageBuffer
                )
                try {
                    qrcodeDecodeResult.value = MultiFormatReader().decodeWithState(BinaryBitmap(HybridBinarizer(qrcodeSource))).text
                } catch (_: Exception) {
                    qrcodeDecodeResult.value = "Error"
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
                Button(
                    onClick = {
                        launcher.launch("image/*")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 50.dp)
                ) {
                    Text(text = "Import")
                }
                Text(text = qrcodeDecodeResult.value)
            }
        }
    }
}