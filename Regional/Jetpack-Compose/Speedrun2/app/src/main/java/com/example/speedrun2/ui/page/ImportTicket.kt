package com.example.speedrun2.ui.page

import android.app.Instrumentation.ActivityResult
import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.media.ImageReader
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import com.example.speedrun2.ui.widget.TopNavBar
import com.google.zxing.Binarizer
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.decoder.Decoder

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
                    imageBitmap.width,
                    imageBitmap.height,
                    imageBuffer
                )
                try {
                    decodedData.value = MultiFormatReader().decodeWithState(BinaryBitmap(HybridBinarizer(imageSource))).text
                } catch (_: NotFoundException) {
                }
            }
        }
        Scaffold(
            topBar = { TopNavBar.Build(title = "", navController = navController) }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
            ) {
                Button(
                    onClick = {
                        launcher.launch("image/*")
                    }
                ) {
                    Text(text = decodedData.value)
                }
            }
        }
    }
}