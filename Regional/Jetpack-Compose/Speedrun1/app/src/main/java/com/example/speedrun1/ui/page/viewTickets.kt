package com.example.speedrun1.ui.page

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.set
import androidx.navigation.NavController
import com.example.speedrun1.service.SqliteHelper
import com.example.speedrun1.ui.widget.TopNavBar
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.google.zxing.qrcode.encoder.Encoder

object ViewTicketsPage {
    @Composable
    fun Build(navController: NavController) {
        val context = LocalContext.current
        Scaffold(
            topBar = { TopNavBar.Build(title = "", navController = navController) }
        ) { pValue ->
            LazyColumn(
                modifier = Modifier
                    .padding(pValue)
            ) {
                items(SqliteHelper(context).getAllTickets()) {
                    val qrcodeData = Encoder.encode(it.email, ErrorCorrectionLevel.H).matrix
                    val qrcodeBitmap = Bitmap.createBitmap(qrcodeData.width * 4, qrcodeData.height * 4, Bitmap.Config.RGB_565)
                    for (iw in 0 until qrcodeData.width) {
                        for (ih in 0 until qrcodeData.height) {
                            for (inw in (iw * 4) until (iw * 4) + 4) {
                                for (inh in (ih * 4) until (ih * 4) + 4) {
                                    qrcodeBitmap[inw, inh] = if (qrcodeData[iw, ih].toInt() == 1) Color.BLACK else Color.WHITE
                                }
                            }
                        }
                    }
                    Box {
                        Image(bitmap = qrcodeBitmap.asImageBitmap(), contentDescription = null)
                    }
                }
            }
        }
    }
}