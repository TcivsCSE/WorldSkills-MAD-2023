package com.example.basicbuild.ui.page

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.graphics.set
import androidx.navigation.NavController
import com.example.basicbuild.service.SqliteHelper
import com.example.basicbuild.ui.component.TopNavBar
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

object ViewTicketsPage {
    @Composable
    fun Build(navController: NavController) {
        val context = LocalContext.current
        val sqliteHelper = remember {
            SqliteHelper(context, null)
        }

        val showDialog = remember {
            mutableStateOf(false)
        }

        Column {
            TopNavBar.Build(navController, "已擁有票券")
            LazyColumn(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                items(sqliteHelper.getTickets()) { ticketData ->
                    val qrcodeMatrix = com.google.zxing.qrcode.encoder.Encoder.encode(
                        java.util.Base64.getEncoder().encodeToString("""{"exhibitName": "${ticketData.exhibitName}", "ticketType": "${ticketData.ticketType}", "price": ${ticketData.price}, "name": "${ticketData.name}", "email": "${ticketData.email}", "phone": "${ticketData.phone}"}""".encodeToByteArray()),
                        ErrorCorrectionLevel.L
                    ).matrix
                    val qrcodeBitmap = Bitmap.createBitmap(qrcodeMatrix.width * 4, qrcodeMatrix.height * 4, Bitmap.Config.ARGB_8888)
                    for (iw in 0 until qrcodeMatrix.width) {
                        for (ih in 0 until qrcodeMatrix.height) {
                            for (inw in (iw * 4) until 4 + (iw * 4)) {
                                for (inh in (ih * 4) until 4 + (ih * 4)) {
                                    qrcodeBitmap[inw, inh] = if (qrcodeMatrix[iw, ih].toInt() == 1) Color.Black.toArgb() else Color.Transparent.toArgb()
                                }
                            }
                        }
                    }

                    Card(
                        elevation = 5.dp,
                        backgroundColor = Color.LightGray,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = ticketData.exhibitName,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 24.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = ticketData.ticketType,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = ticketData.price.toString() + "元",
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Light,
                                    fontSize = 16.sp
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = ticketData.name,
                                    textAlign = TextAlign.End
                                )
                                Text(
                                    text = ticketData.phone,
                                    textAlign = TextAlign.End
                                )
                                Text(
                                    text = ticketData.email,
                                    textAlign = TextAlign.End
                                )
                            }
                            IconButton(
                                onClick = { showDialog.value = true },
                                modifier = Modifier
                                    .size(20.dp)
                            ) {
                                Icon(Icons.Default.Share, contentDescription = null)
                            }
                        }
                    }

                    if (showDialog.value) {
                        Dialog(onDismissRequest = { showDialog.value = false }) {
                            ShareTicketQrcodeDialog(showDialog, qrcodeBitmap)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ShareTicketQrcodeDialog(showDialog: MutableState<Boolean>, qrcodeBitmap: Bitmap) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .size(280.dp)
            ) {
                Image(
                    bitmap = qrcodeBitmap.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                )
            }
            IconButton(
                onClick = {
                    showDialog.value = false
                },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .background(Color.White.copy(alpha = 0.85f), shape = CircleShape)
            ) {
                Icon(Icons.Default.Close, null)
            }
        }
    }
}