package dev.nightfeather.basic_build.ui.page

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
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
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

object SettingsPage {
    @Composable
    fun Build(navController: NavController) {
        val coroutineScope = rememberCoroutineScope()
        val httpClient = OkHttpClient()
        val sharedPrefs = LocalContext.current.getSharedPreferences("data", Context.MODE_PRIVATE)
        val userData = remember {
            JSONObject(sharedPrefs.getString("userData", null)!!)
        }

        val nameInputValue = remember {
            mutableStateOf(userData["name"] as String)
        }
        val emailInputValue = remember {
            mutableStateOf(userData["email"] as String)
        }
        val passwordInputValue = remember {
            mutableStateOf(userData["password"] as String)
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
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                BuildTextField(
                    inputValue = nameInputValue,
                    iconVector = Icons.Default.Person,
                    placeholder = "姓名",
                    modifier = Modifier
                        .padding(top = 50.dp)
                )
                BuildTextField(
                    inputValue = emailInputValue,
                    iconVector = Icons.Default.Email,
                    placeholder = "電子郵箱",
                    keyboardType = KeyboardType.Email,
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
                BuildTextField(
                    inputValue = passwordInputValue,
                    iconVector = Icons.Default.Lock,
                    placeholder = "密碼",
                    keyboardType = KeyboardType.Ascii,
                    isSecure = true,
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
                Button(
                    onClick = {
                        if (emailInputValue.value.isBlank() || passwordInputValue.value.isBlank() || nameInputValue.value.isBlank()) {
                            dialogIcon.value = Icons.Default.Close
                            dialogMessage.value = "輸入框不得為空"
                            showDialog.value = true
                            return@Button
                        }
                        coroutineScope.launch {
                            val body = JSONObject()
                            body.put("email", userData["email"])
                            body.put("password", userData["password"])
                            body.put("data", JSONObject())
                            (body["data"] as JSONObject).put("name", nameInputValue.value)
                            (body["data"] as JSONObject).put("email", emailInputValue.value)
                            (body["data"] as JSONObject).put("password", passwordInputValue.value)

                            val req = Request.Builder()
                                .url("$baseUrl/guide/account/edit")
                                .post(body.toString().toRequestBody(contentTypeJson))
                                .build()

                            httpClient.newCall(req).enqueue(object: Callback {
                                override fun onFailure(call: Call, e: IOException) {
                                    TODO("Not yet implemented")
                                }

                                override fun onResponse(call: Call, response: Response) {
                                    when (response.code) {
                                        202 -> {
                                            sharedPrefs
                                                .edit()
                                                .putString("userData", response.body!!.string())
                                                .apply()
                                            dialogIcon.value = Icons.Default.Check
                                            dialogMessage.value = "修改完成"
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
                                }
                            })
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp)
                        .padding(top = 60.dp)
                        .height(60.dp)
                ) {
                    Text(
                        text = "更改個人資料",
                        fontSize = 22.sp
                    )
                }
                Text(
                    text = "登出",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .clickable {
                            sharedPrefs
                                .edit()
                                .remove("userData")
                                .apply()
                            navController.navigate("login") {
                                popUpTo(0)
                            }
                        }
                )
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
