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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import dev.nightfeather.speedrun1.Constants
import dev.nightfeather.speedrun1.R
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

object LoginPage {
    @Composable
    fun Build(navController: NavController) {
        val emailInputValue = remember {
            mutableStateOf("")
        }

        Scaffold(

        ) {
            Column(
                modifier = Modifier
                    .padding(it)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.skillsweek_icon),
                    contentDescription = null
                )
                BuildTextField(
                    inputValue = emailInputValue,
                    iconVector = Icons.Default.Email,
                    placeholder = "電子郵箱"
                )
            }
        }
    }

    @Composable
    fun BuildTextField(
        inputValue: MutableState<String>,
        iconVector: ImageVector,
        modifier: Modifier = Modifier,
        placeholder: String = "",
        isSecure: Boolean = false,
    ) {
        TextField(
            value = inputValue.value,
            onValueChange = {
                inputValue.value = it
            },
            placeholder = {
                Text(text = placeholder)
            },
            modifier = modifier
                .padding(horizontal = 10.dp),
            leadingIcon = {
                Icon(
                    imageVector = iconVector,
                    contentDescription = null
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.DarkGray,
                backgroundColor = Color.Transparent,
                textColor = Color.DarkGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                placeholderColor = Color.LightGray
            ),
            visualTransformation = if (isSecure) PasswordVisualTransformation() else VisualTransformation.None
        )
    }
}