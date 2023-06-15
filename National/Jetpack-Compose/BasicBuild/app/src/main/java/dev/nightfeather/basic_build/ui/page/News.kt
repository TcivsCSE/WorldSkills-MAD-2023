package dev.nightfeather.basic_build.ui.page

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import dev.nightfeather.basic_build.Constants
import dev.nightfeather.basic_build.Constants.baseUrl
import dev.nightfeather.basic_build.Constants.contentTypeJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

object NewsPage {
    @Composable
    fun Build(navController: NavController) {
        val sharedPrefs = LocalContext.current.getSharedPreferences("data", Context.MODE_PRIVATE)
        val userData = remember {
            JSONObject(sharedPrefs.getString("userData", null)!!)
        }
        val coroutineScope = rememberCoroutineScope()
        val httpClient = remember {
            OkHttpClient()
        }
        val newsData = remember {
            mutableStateListOf<JSONObject>()
        }


        val titleInputValue = remember {
            mutableStateOf("")
        }
        val contentInputValue = remember {
            mutableStateOf("")
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

        LaunchedEffect(Unit)  {
            coroutineScope.refreshNewsPosts(httpClient, newsData)
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
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                BuildTextField(
                    inputValue = titleInputValue,
                    iconVector = Icons.Default.Send,
                    placeholder = "標題",
                    modifier = Modifier
                )
                BuildTextField(
                    inputValue = contentInputValue,
                    iconVector = Icons.Default.Menu,
                    placeholder = "內容",
                    modifier = Modifier
                        .padding(top = 20.dp)
                )
                Button(
                    onClick = {
                        if (titleInputValue.value.isBlank() || contentInputValue.value.isBlank()) {
                            dialogIcon.value = Icons.Default.Close
                            dialogMessage.value = "輸入框不得為空"
                            showDialog.value = true
                            return@Button
                        }
                        val body = JSONObject()
                        body.put("email", userData["email"])
                        body.put("password", userData["password"])
                        body.put("data", JSONObject())
                        (body["data"] as JSONObject).put("title", titleInputValue.value)
                        (body["data"] as JSONObject).put("content", contentInputValue.value)
                        val req = Request.Builder()
                            .url("$baseUrl/guide/news/posts")
                            .post(body.toString().toRequestBody(contentTypeJson))
                            .build()
                        httpClient.newCall(req).enqueue(object: Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                TODO("Not yet implemented")
                            }

                            override fun onResponse(call: Call, response: Response) {
                                when (response.code) {
                                    201 -> {
                                        sharedPrefs
                                            .edit()
                                            .putString("userData", response.body!!.string())
                                            .apply()
                                        dialogIcon.value = Icons.Default.Check
                                        dialogMessage.value = "發布完成"
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
                                coroutineScope.refreshNewsPosts(httpClient, newsData)
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
                        text = "發布文章",
                        fontSize = 22.sp
                    )
                }
                LazyColumn(

                ) {
                    itemsIndexed(newsData) { idx, data ->
                       Card(
                           backgroundColor = Color.DarkGray,
                           contentColor = Color.White,
                           shape = RoundedCornerShape(10.dp),
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(horizontal = 30.dp, vertical = 10.dp)
                               .height(60.dp)
                       ) {
                         Row(
                             verticalAlignment = Alignment.CenterVertically,
                             modifier = Modifier
                                 .fillMaxSize()
                                 .padding(4.dp)
                                 .padding(horizontal = 8.dp)
                         ) {
                             Text(
                                 text = "${idx + 1}.",
                                 style = TextStyle(
                                     fontSize = 20.sp
                                 )
                             )
                             Column(
                                 modifier = Modifier
                                     .padding(horizontal = 12.dp)
                             ) {
                                 Text(text = data["title"] as String)
                                 Text(text = data["content"] as String)
                             }
                         }
                       }
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

    private fun CoroutineScope.refreshNewsPosts(httpClient: OkHttpClient, newsData: SnapshotStateList<JSONObject>) {
        val req = Request.Builder()
            .get()
            .url("$baseUrl/guide/news/posts")
            .build()

        launch {
            httpClient.newCall(req).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call, response: Response) {
                    newsData.clear()
                    val jsonData = JSONObject(response.body!!.string())
                    for (key in jsonData.keys()) {
                        newsData.add(jsonData.get(key) as JSONObject)
                    }
                }
            })
        }
    }

    @Composable
    private fun BuildTextField(
        inputValue: MutableState<String>,
        iconVector: ImageVector,
        placeholder: String,
        modifier: Modifier = Modifier,
        keyboardType: KeyboardType = KeyboardType.Text,
        isSecure: Boolean = false,
    ) {
        TextField(
            value = inputValue.value,
            onValueChange = {
                inputValue.value = it
            },
            leadingIcon = {
                Icon(
                    imageVector = iconVector,
                    contentDescription = null,
                    modifier = Modifier
                        .scale(1.15f)
                )
            },
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 20.sp
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            visualTransformation = if (isSecure) PasswordVisualTransformation() else VisualTransformation.None,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.DarkGray,
                unfocusedIndicatorColor = Color.DarkGray,
                cursorColor = Color.DarkGray,
                textColor = Color.DarkGray,
                leadingIconColor = Color.DarkGray,
                placeholderColor = Color.LightGray
            ),
            textStyle = TextStyle(
                fontSize = 20.sp
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
        )
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