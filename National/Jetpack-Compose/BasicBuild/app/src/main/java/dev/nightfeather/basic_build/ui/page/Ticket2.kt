package dev.nightfeather.basic_build.ui.page

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Environment
import android.widget.TimePicker
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.window.Dialog
import androidx.core.graphics.get
import androidx.core.graphics.set
import androidx.glance.LocalContext
import androidx.navigation.NavController
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.google.zxing.qrcode.encoder.Encoder
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object Ticket2Page {
    @Composable
    fun Build(navController: NavController) {
        LaunchedEffect(Unit) {

        }
        val sharedPref = LocalContext.current.getSharedPreferences("data", Context.MODE_PRIVATE)
        val httpClient = OkHttpClient()
        val request = Request.Builder()
            .url("123")
            .post("".toString().toRequestBody("application/json".toMediaType()))
            .build()
        httpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                TODO("Not yet implemented")
            }
        })
        val qrcodeBitmap = Encoder.encode("123", ErrorCorrectionLevel.H).matrix
        val imageBitmap = Bitmap.createBitmap(qrcodeBitmap.width * 4, qrcodeBitmap.height * 4, Bitmap.Config.RGB_565)
        for (x in 0 until qrcodeBitmap.width) {
            for (y in 0 until qrcodeBitmap.height) {
                for (ix in x * 4 until x * 4 + 4) {
                    for (iy in y * 4 until y * 4 + 4) {
                        imageBitmap[ix, iy] = if (qrcodeBitmap[x, y].toInt() == 0) Color.BLACK else Color.WHITE
                    }
                }
            }
        }
        val fs = FileOutputStream(
            File(
                "${
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
                }/name.png",
            )
        )
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fs)
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            LocalContext.current,
            { _, y, m, d ->
                calendar.set(y, m, d)
                SimpleDateFormat("EEEE yyyy/MM/dd", Locale.US).format(calendar.time).toString()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        TimePickerDialog(
            LocalContext.current,
            { _, h, m ->
                calendar.set(Calendar.HOUR_OF_DAY, h)
                calendar.set(Calendar.MINUTE, m)
                SimpleDateFormat("hh:mm tt", Locale.US).format(calendar.time).toString()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
        sharedPref
            .edit()
            .putString("userData", "{}")
            .apply()
        sharedPref
            .getString("userData", "")
        LocalConfiguration.current.screenWidthDp
        if (true) {
            Dialog(onDismissRequest = { /*TODO*/ }) {
                Card() {
                    
                }
            }
        }
    }
}