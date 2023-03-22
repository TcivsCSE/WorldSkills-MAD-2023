package com.example.speedrun6.ui.page

import android.graphics.Bitmap
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.set
import androidx.navigation.NavController
import com.example.speedrun6.service.SqliteHelper
import com.example.speedrun6.ui.widget.TopNavBar
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.google.zxing.qrcode.encoder.Encoder

object ViewTicketsPage {
    @Composable
    fun Build(navController: NavController) {
        val sqliteHelper = SqliteHelper(LocalContext.current)
        Scaffold(
            topBar = { TopNavBar.Build(title = "viewTickets", navController = navController) }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                items(sqliteHelper.getAllTickets()){
                    val qrcodeSource = Encoder.encode(it.email + it.date + it.phone, ErrorCorrectionLevel.H).matrix
                    val qrcodeBitmap = Bitmap.createBitmap(qrcodeSource.width * 4, qrcodeSource.height * 4, Bitmap.Config.RGB_565)
                    for (iw in 0 until qrcodeSource.width) {
                        for (ih in 0 until qrcodeSource.height) {
                            for (inw in (iw * 4) until (iw * 4) + 4) {
                                for (inh in (ih * 4) until (ih * 4) + 4) {
                                    qrcodeBitmap[inw, inh] = if (qrcodeSource[iw, ih].toInt() == 0) Color.WHITE else Color.BLACK
                                }
                            }
                        }
                    }
                    Image(
                        bitmap = qrcodeBitmap.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                    )
                }
             }
        }
    }
}