package com.example.basicbuild.ui.page

import androidx.camera.core.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.basicbuild.service.SqliteHelper
import com.example.basicbuild.ui.component.TopNavBar
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer
import org.json.JSONObject
import java.util.*

object ScanQrcodePage {
    @Composable
    fun Build(navController: NavController) {
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        val scanResult = remember {
            mutableStateOf(JSONObject())
        }

        val scannedQrcode = remember {
            mutableListOf("")
        }

        val showDialog = remember {
            mutableStateOf(false)
        }
        val sqliteHelper = remember {
            SqliteHelper(context, null)
        }

        Column {
            TopNavBar.Build(navController, "匯入票券", modifier = Modifier.zIndex(1.5f))
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .size(260.dp)
                        .align(Alignment.Center)
                        .border(2.dp, Color.White.copy(alpha = 0.75f), RoundedCornerShape(10.dp))
                        .zIndex(1.5f)
                ) {
                    Icon(
                        Icons.Rounded.Add,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.75f),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(80.dp)
                            .zIndex(1.5f)
                    )
                }
                AndroidView(
                    factory = {
                        val previewView = PreviewView(context)
                        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

                        val executor = ContextCompat.getMainExecutor(context)
                        cameraProviderFuture.addListener({
                            val cameraProvider = cameraProviderFuture.get()
                            val preview = Preview.Builder().build().also {
                                it.setSurfaceProvider(previewView.surfaceProvider)
                            }

                            val imageAnalysis = ImageAnalysis.Builder()
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .build()

                            val qrCodeAnalyzer = QRCodeAnalyzer { qrCode ->
                                if (!scannedQrcode.contains(qrCode.text)) {
                                    scanResult.value = JSONObject(Base64.getDecoder().decode(qrCode.text).decodeToString())
                                    scannedQrcode.add(qrCode.text)
                                    showDialog.value = true
                                }
                            }


                            imageAnalysis.setAnalyzer(
                                ContextCompat.getMainExecutor(context),
                                qrCodeAnalyzer
                            )

                            val cameraSelector = CameraSelector.Builder()
                                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                .build()

                            cameraProvider.unbindAll()
                            cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                cameraSelector,
                                preview,
                                imageAnalysis
                            )
                        }, executor)
                        previewView
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(1f)
                )
            }
        }
        if (showDialog.value) {
            Dialog(
                onDismissRequest = {
                    showDialog.value = false
                }
            ) {
                ConfirmAddDialog(showDialog, scanResult.value, sqliteHelper)
            }
        }
    }
    @Composable
    private fun ConfirmAddDialog(showDialog: MutableState<Boolean>, ticketData: JSONObject, sqliteHelper: SqliteHelper) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .size(340.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Text(
                    text = "確認新增票券？",
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = "票券資料",
                    textAlign = TextAlign.Start,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
                Text(
                    text = "展覽名稱: ${ticketData["exhibitName"]}\n票券類別: ${ticketData["ticketType"]}\n價格: ${ticketData["price"]}\n姓名: ${ticketData["name"]}\n電子郵箱: ${ticketData["email"]}\n行動電話: ${ticketData["phone"]}",
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp)
                ) {
                    Button(
                        onClick = {
                            showDialog.value = false
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.DarkGray,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "取消")
                    }
                    Button(
                        onClick = {
                            sqliteHelper.addTicket(
                                exhibitName = ticketData["exhibitName"] as String,
                                ticketType = ticketData["ticketType"] as String,
                                price = ticketData["price"] as Int,
                                name = ticketData["name"] as String,
                                email = ticketData["email"] as String,
                                phone = ticketData["phone"] as String,
                            )
                            showDialog.value = false
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.DarkGray,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "新增")
                    }
                }
            }
        }
    }
}

class QRCodeAnalyzer(private val onQRCodeScanned: (com.google.zxing.Result) -> Unit): ImageAnalysis.Analyzer {
    private val reader = MultiFormatReader()
    override fun analyze(image: ImageProxy) {
        val buffer = image.planes[0].buffer
        val data = ByteArray(buffer.remaining())
        buffer.get(data)

        val source = PlanarYUVLuminanceSource(
            data,
            image.width,
            image.height,
            0,
            0,
            image.width,
            image.height,
            false
        )
        try {
            val result = reader.decodeWithState(BinaryBitmap(HybridBinarizer(source)))
            onQRCodeScanned(result)
        } catch (_: NotFoundException) {
        } catch (_: Exception) {
        } finally {
            reader.reset()
            image.close()
        }
    }
}