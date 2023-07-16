package dev.nightfeather.speedrun1.ui.page

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import dev.nightfeather.speedrun1.Constants.baseUrl
import dev.nightfeather.speedrun1.Constants.contentTypeJson
import dev.nightfeather.speedrun1.R
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

object SignupPage {
    @Composable
    fun Build(navController: NavController) {
        val coroutineScope = rememberCoroutineScope()
        val httpClient = remember {
            OkHttpClient()
        }
        val activity = LocalContext.current as Activity
        val sharedPrefs = LocalContext.current.getSharedPreferences("data", Context.MODE_PRIVATE)

        val showDialog = remember {
            mutableStateOf(false)
        }
        val dialogIcon = remember {
            mutableStateOf(Icons.Default.Close)
        }
        val dialogMessage = remember {
            mutableStateOf("")
        }

        val nameInputValue = remember {
            mutableStateOf("")
        }
        val emailInputValue = remember {
            mutableStateOf("")
        }
        val passwordInputValue = remember {
            mutableStateOf("")
        }

        Scaffold(
            backgroundColor = Color.DarkGray
        ) { paddingValues ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.skillsweek_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 46.dp)
                        .padding(top = 40.dp)
                )

                BuildTextField(
                    inputValue = nameInputValue,
                    placeholder = "姓名",
                    iconVector = Icons.Default.AccountCircle,
                    keyboardType = KeyboardType.Email,
                    modifier = Modifier.padding(top = 60.dp)
                )
                BuildTextField(
                    inputValue = emailInputValue,
                    placeholder = "電子郵箱",
                    iconVector = Icons.Default.Email,
                    keyboardType = KeyboardType.Email
                )
                BuildTextField(
                    inputValue = passwordInputValue,
                    placeholder = "密碼",
                    iconVector = Icons.Default.Lock,
                    isSecure = true
                )

                Button(
                    onClick = {
                        if (nameInputValue.value.isBlank() || emailInputValue.value.isBlank() || passwordInputValue.value.isBlank()) {
                            dialogMessage.value = "輸入框不得為空"
                            showDialog.value = true
                            return@Button
                        }

                        coroutineScope.launch {
                            val body = JSONObject()
                            body.put("name", nameInputValue.value)
                            body.put("email", emailInputValue.value)
                            body.put("password", passwordInputValue.value)

                            val request = Request.Builder()
                                .url("$baseUrl/guide/account/signup")
                                .post(body.toString().toRequestBody(contentTypeJson))
                                .build()

                            httpClient.newCall(request).enqueue(object: Callback {
                                override fun onFailure(call: Call, e: IOException) {
                                    TODO("Not yet implemented")
                                }

                                override fun onResponse(call: Call, response: Response) {
                                    when (response.code) {
                                        201 -> {
                                            activity.runOnUiThread {
                                                navController.popBackStack()
                                            }
                                        }
                                        409 -> {
                                            dialogMessage.value = "帳號已存在"
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
                        .padding(top = 80.dp)
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 64.dp)
                ) {
                    Text(
                        text = "註冊",
                        fontSize = 18.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(top = 10.dp)
                ) {
                    Text(
                        text = "已經有帳號嗎？",
                        color = Color.White,
                    )
                    Text(
                        text = "返回登入",
                        color = Color.Cyan,
                        modifier = Modifier
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                }
            }

            if (showDialog.value) {
                Dialog(
                    onDismissRequest = {
                        showDialog.value = false
                    }
                ) {
                    BuildDialog(
                        showDialog,
                        iconVector = dialogIcon.value,
                        message = dialogMessage.value
                    )
                }
            }
        }
    }

    @Composable
    private fun BuildDialog(
        showDialog: MutableState<Boolean>,
        iconVector: ImageVector,
        message: String
    ) {
        Card(
            modifier = Modifier
                .size(300.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Icon(
                    imageVector = iconVector,
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                )
                Text(text = message)
            }
        }
    }

    @Composable
    private fun BuildTextField(
        inputValue: MutableState<String>,
        placeholder: String,
        iconVector: ImageVector,
        modifier: Modifier = Modifier,
        isSecure: Boolean = false,
        keyboardType: KeyboardType = KeyboardType.Text,
    ) {
        TextField(
            value = inputValue.value,
            onValueChange = {
                inputValue.value = it
            },
            visualTransformation = if (isSecure) PasswordVisualTransformation() else VisualTransformation.None,
            leadingIcon = {
                Icon(imageVector = iconVector, contentDescription = null)
            },
            placeholder = {
                Text(text = placeholder)
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.textFieldColors(
                leadingIconColor = Color.White,
                placeholderColor = Color.LightGray,
                backgroundColor = Color.Transparent,
                textColor = Color.White,
                cursorColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = modifier
        )
    }
}