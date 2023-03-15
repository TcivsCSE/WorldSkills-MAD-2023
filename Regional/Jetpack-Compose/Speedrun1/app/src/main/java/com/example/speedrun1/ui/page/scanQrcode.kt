package com.example.speedrun1.ui.page

import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraSelector.LENS_FACING_BACK
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.speedrun1.service.SqliteHelper
import com.example.speedrun1.ui.widget.TopNavBar
import com.google.zxing.*
import com.google.zxing.common.*
import org.json.JSONObject

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
            mutableStateOf(true)
        }
        val sqliteHelper = SqliteHelper(LocalContext.current)
        Scaffold(
            topBar = { TopNavBar.Build(title = "掃描QRCode", navController = navController) }
        ) { scaffoldPadding ->
            Box(
                modifier = Modifier
                    .padding(scaffoldPadding)
            ) {
                AndroidView(
                    factory = {
                        val previewView = PreviewView(context)
                        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
                        val contextExecutor = ContextCompat.getMainExecutor(context)
                        cameraProviderFuture.addListener({
                            val cameraProvider = cameraProviderFuture.get()
                            val preview = Preview.Builder().build().also {
                                it.setSurfaceProvider(previewView.surfaceProvider)
                            }

                            val imageAnalysis = ImageAnalysis.Builder()
                                .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                                .build()

                            val qrcodeAnalyzer = QRCodeAnalyzer( onQRCodeScanned = {
                                if (!scannedQrcode.contains(it.text)) {
                                    scanResult.value = JSONObject(it.text)
                                    scannedQrcode.add(it.text)
                                    showDialog.value = true
                                }
                            })

                            imageAnalysis.setAnalyzer(
                                contextExecutor,
                                qrcodeAnalyzer
                            )

                            val cameraSelector = CameraSelector.Builder()
                                .requireLensFacing(LENS_FACING_BACK)
                                .build()

                            cameraProvider.unbindAll()
                            cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                cameraSelector,
                                preview,
                                imageAnalysis
                            )

                        }, contextExecutor)
                        previewView
                    },
                )
            }
            if (showDialog.value) {
                Dialog(onDismissRequest = { showDialog.value = false }) {
                    BuildDialog()
                }
            }
        }
    }

    @Composable
    private fun BuildDialog() {
        Card(
            modifier = Modifier
                .height(300.dp)
                .width(300.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Text(text = "add")
            }
        }
    }
}

class QRCodeAnalyzer(private val onQRCodeScanned: (Result) -> Unit): ImageAnalysis.Analyzer {
    private val reader = MultiFormatReader()
    override fun analyze(image: ImageProxy) {
        val buffer = image.planes[0].buffer
        val imageData = ByteArray(buffer.remaining())
        buffer.get(imageData)

        val source = PlanarYUVLuminanceSource(
            imageData,
            image.width,
            image.height,
            0,
            0,
            image.width,
            image.height,
            false
        )

        try {
            onQRCodeScanned(
                reader.decodeWithState(BinaryBitmap(HybridBinarizer(source)))
            )
        } catch (_: java.lang.Exception) {
        } catch (_: NotFoundException) {
        } finally {
            reader.reset()
            image.close()
        }
    }
}