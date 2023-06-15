package dev.nightfeather.basic_build.ui.page

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.graphics.get
import androidx.core.graphics.set
import androidx.navigation.NavController
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.google.zxing.qrcode.encoder.Encoder
import dev.nightfeather.basic_build.Constants
import dev.nightfeather.basic_build.Constants.contentTypeJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object TicketPage {
    @Composable
    fun Build(navController: NavController) {
        val coroutineScope = rememberCoroutineScope()
        val httpClient = OkHttpClient()
        val sharedPrefs = LocalContext.current.getSharedPreferences("data", Context.MODE_PRIVATE)
        val userData = remember {
            JSONObject(sharedPrefs.getString("userData", null)!!)
        }
        val ticketData = remember {
            mutableStateListOf<JSONObject>()
        }

        val exhibitionNameValue = remember {
            mutableStateOf("N/A")
        }
        val ticketTypeValue = remember {
            mutableStateOf("N/A")
        }
        val dateValue = remember {
            mutableStateOf(SimpleDateFormat("yyyy/MM/dd", Locale.US).format(Date()))
        }

        val showDialog = remember {
            mutableStateOf(false)
        }
        val dialogIcon = remember {
            mutableStateOf(Icons.Default.Warning)
        }
        val dialogMessage = remember {
            mutableStateOf("N/A")
        }

        LaunchedEffect(Unit) {
            coroutineScope.refreshTickets(httpClient, userData, ticketData)
        }

        Scaffold(
            topBar = {
                Row() {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                    Spacer(Modifier.fillMaxWidth())
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null
                        )
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                BuildDropdown(
                    selectedValue = exhibitionNameValue,
                    options = arrayListOf("展覽A", "展覽B", "展覽C"),
                )
                BuildDropdown(
                    selectedValue = ticketTypeValue,
                    options = arrayListOf("全票", "學生票", "敬老票"),
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
                BuildDatePicker(
                    dateValue = dateValue,
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
                Button(
                    onClick = {
                        if (exhibitionNameValue.value == "N/A" || ticketTypeValue.value == "N/A") {
                            dialogIcon.value = Icons.Default.Close
                            dialogMessage.value = "輸入值無效"
                            showDialog.value = true
                            return@Button
                        }
                        val body = JSONObject()
                        body.put("email", userData["email"])
                        body.put("password", userData["password"])
                        body.put("data", JSONObject())
                        (body["data"] as JSONObject).put("name", exhibitionNameValue.value)
                        (body["data"] as JSONObject).put("expired_at", dateValue.value)
                        (body["data"] as JSONObject).put("type", ticketTypeValue.value)

                        val req = Request.Builder()
                            .url("${Constants.baseUrl}/guide/tickets")
                            .post(body.toString().toRequestBody(contentTypeJson))
                            .build()
                        httpClient.newCall(req).enqueue(object: Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                TODO("Not yet implemented")
                            }

                            override fun onResponse(call: Call, response: Response) {
                                when (response.code) {
                                    200 -> {
                                        dialogIcon.value = Icons.Default.Check
                                        dialogMessage.value = "購買完成"
                                        showDialog.value = true
                                    }
                                    404 -> {
                                        dialogIcon.value = Icons.Default.Close
                                        dialogMessage.value = "帳號不存在"
                                        showDialog.value = true
                                    }
                                    401 -> {
                                        dialogIcon.value = Icons.Default.Close
                                        dialogMessage.value = "密碼錯誤"
                                        showDialog.value = true
                                    }
                                }
                                coroutineScope.refreshTickets(httpClient, userData, ticketData)
                            }
                        })
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp)
                        .padding(vertical = 20.dp)
                        .height(60.dp)
                ) {
                    Text(
                        text = "購買票券",
                        fontSize = 22.sp
                    )
                }
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxSize()
                ) {
                    items(ticketData) {data ->
                        val qrcodeBitmap = Encoder.encode(data["id"] as String, ErrorCorrectionLevel.H).matrix
                        val imageBitmap = Bitmap.createBitmap(qrcodeBitmap.width * 4, qrcodeBitmap.height * 4, Bitmap.Config.RGB_565)
                        for (x in 0 until  qrcodeBitmap.width) {
                            for (y in 0 until qrcodeBitmap.height) {
                                for (ix in x * 4 until x * 4 + 4) {
                                    for (iy in y * 4 until y * 4 + 4) {
                                        imageBitmap[ix, iy] = if (qrcodeBitmap.get(x, y).toInt() == 0) 0xFFFFFF else 0x000000
                                    }
                                }
                            }
                        }
                        Image(
                            imageBitmap.asImageBitmap(),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(200.dp)
                                .clickable {
                                    val fs = FileOutputStream(
                                        File(
                                            "${
                                                Environment.getExternalStoragePublicDirectory(
                                                    Environment.DIRECTORY_DOWNLOADS
                                                ).absolutePath
                                            }/${data.get("id")}.png"
                                        )
                                    )
                                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fs)
                                }
                        )
                    }
                }
            }

            if (showDialog.value) {
                Dialog(
                    onDismissRequest = {
                        showDialog.value = false
                    }
                ) {
                    BuildDialog(iconVector = dialogIcon.value, message = dialogMessage.value, showDialog)
                }
            }
        }
    }

    private fun CoroutineScope.refreshTickets(httpClient: OkHttpClient, userData: JSONObject, ticketData: SnapshotStateList<JSONObject>) {
        val body = JSONObject()
        body.put("email", userData["email"])
        body.put("password", userData["password"])

        val req = Request.Builder()
            .post(body.toString().toRequestBody(contentTypeJson))
            .url("${Constants.baseUrl}/guide/tickets")
            .build()

        launch {
            httpClient.newCall(req).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call, response: Response) {
                    ticketData.clear()
                    val jsonData = JSONObject(response.body!!.string())
                    for (key in jsonData.keys()) {
                        ticketData.add(jsonData.get(key) as JSONObject)
                    }
                }
            })
        }
    }

    @Composable
    private fun <T> BuildDropdown(selectedValue: MutableState<T>, options: ArrayList<T>, modifier: Modifier = Modifier) {
        val isExpanded = remember {
            mutableStateOf(false)
        }
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .height(60.dp)
                .border(
                    2.dp,
                    Color.DarkGray,
                    RoundedCornerShape(10.dp)
                )
                .clickable {
                    isExpanded.value = !isExpanded.value
                }
        ) {
            Text(
                text = selectedValue.value.toString(),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 14.dp)
            )
            DropdownMenu(
                expanded = isExpanded.value,
                onDismissRequest = {
                    isExpanded.value = false
                },
                modifier = Modifier
                    .width((LocalConfiguration.current.screenWidthDp - 80).dp)
            ) {
                for (option in options) {
                    DropdownMenuItem(
                        onClick = {
                            selectedValue.value = option
                            isExpanded.value = false
                        }
                    ) {
                        Text(text = option.toString())
                    }
                }
            }
        }
    }

    @Composable
    private fun BuildDatePicker(dateValue: MutableState<String>, modifier: Modifier = Modifier) {
        val context = LocalContext.current
        val calendar = Calendar.getInstance()
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .height(60.dp)
                .border(
                    2.dp,
                    Color.DarkGray,
                    RoundedCornerShape(10.dp)
                )
                .clickable {
                    DatePickerDialog(
                        context,
                        { _, y, m, d ->
                            val c = Calendar.getInstance()
                            c.set(y, m, d)
                            dateValue.value =
                                SimpleDateFormat("yyyy/MM/dd", Locale.US).format(c.time)
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                    ).show()
                }
        ) {
            Text(
                text = dateValue.value,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 14.dp)
            )
        }
    }
    @Composable
    private fun BuildDialog(
        iconVector: ImageVector,
        message: String,
        showDialog: MutableState<Boolean>
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .size(300.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    imageVector = iconVector,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(86.dp)
                )
                Text(
                    text = message,
                    fontSize = 22.sp
                )
                Button(
                    onClick = {
                        showDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .size(width = 100.dp, height = 40.dp)
                ) {
                    Text(
                        text = "確定"
                    )
                }
            }
        }
    }
}
