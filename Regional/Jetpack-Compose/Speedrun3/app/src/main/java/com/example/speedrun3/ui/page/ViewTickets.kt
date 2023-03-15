package com.example.speedrun3.ui.page

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.get
import androidx.core.graphics.set
import androidx.navigation.NavController
import com.example.speedrun3.service.SqliteHelper
import com.example.speedrun3.ui.widget.TopNavBar
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.google.zxing.qrcode.encoder.Encoder

object ViewTicketsPage {
    @Composable
    fun Build(navController: NavController) {
        val sqliteHelper = SqliteHelper(LocalContext.current)
        Scaffold(
            topBar = { TopNavBar.Build(title = "viewTickets", navController = navController) }
        ) { sPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(sPadding)
            ) {
                items(
                    sqliteHelper.getAllTickets()
                ) {
                    val qrcodeData = Encoder.encode(it.email, ErrorCorrectionLevel.H).matrix
                    val qrcodeBitmap = Bitmap.createBitmap(qrcodeData.width * 4, qrcodeData.height * 4, Bitmap.Config.RGB_565)
                    for (iw in 0 until qrcodeData.width) {
                        for (ih in 0 until  qrcodeData.height) {
                            for (inw in (iw * 4) until (iw * 4) + 4) {
                                for (inh in (ih * 4) until (ih * 4) + 4) {
                                    qrcodeBitmap[inw, inh] = if (qrcodeData[iw, ih].toInt() == 0) Color.WHITE else Color.BLACK
                                }
                            }
                        }
                    }

                    Image(
                        bitmap = qrcodeBitmap.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(60.dp)
                    )
                }
            }
        }
    }
}
