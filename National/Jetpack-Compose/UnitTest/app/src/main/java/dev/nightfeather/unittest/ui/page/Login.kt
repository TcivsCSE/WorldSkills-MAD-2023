package dev.nightfeather.unittest.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController

object LoginPage {
    @Composable
    fun Build(navController: NavController) {
        val emailInput = remember {
            mutableStateOf("")
        }
        val passwordInput = remember {
            mutableStateOf("")
        }

        Scaffold() { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                TextField(
                    value = emailInput.value,
                    onValueChange = {
                        emailInput.value = it
                    },
                    placeholder = { Text(text = "Email") },
                    modifier = Modifier
                        .testTag("login:emailTextField")
                )
                TextField(
                    value = passwordInput.value,
                    onValueChange = {
                        passwordInput.value = it
                    },
                    placeholder = { Text(text = "Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .testTag("login:passwordTextField")
                )
                Button(
                    onClick = {
                        if (emailInput.value == "test@mail.com" && passwordInput.value == "password") {
                            navController.navigate("home") {
                                this.popUpTo(0)
                            }
                        }
                    },
                    modifier = Modifier
                        .testTag("login:loginButton")
                ) {
                    Text(text = "Login")
                }
            }
        }
    }
}