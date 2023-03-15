package com.example.speedrun2.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.speedrun2.service.SqliteHelper
import com.example.speedrun2.ui.widget.TopNavBar



object BuyTicketPage {
    @Composable
    fun Build(navController: NavController) {
        val nameInputValue = remember {
            mutableStateOf(Pair("", false))
        }
        val phoneInputValue = remember {
            mutableStateOf(Pair("", false))
        }
        val emailInputValue = remember {
            mutableStateOf(Pair("", false))
        }
        val typeDropdownValue = remember {
            mutableStateOf("N/A")
        }
        val exhibitDropdownValue = remember {
            mutableStateOf("N/A")
        }
        val showDialog = remember {
            mutableStateOf(false)
        }
        val isAddSuccess = remember {
            mutableStateOf(true)
        }
        val sqliteHelper = SqliteHelper(LocalContext.current)
        Scaffold(
            topBar = { TopNavBar.Build(title = "", navController = navController) }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
            ) {
                BuildTextInput(
                    textFieldValue = nameInputValue,
                    pattern = ".*",
                    placeholder = "Name",
                    modifier = Modifier
                        .padding(top = 20.dp)
                )
                BuildTextInput(
                    textFieldValue = phoneInputValue,
                    pattern = ".*",
                    placeholder = "Phone",
                    modifier = Modifier
                        .padding(top = 20.dp)
                )
                BuildTextInput(
                    textFieldValue = emailInputValue,
                    pattern = ".*",
                    placeholder = "Email",
                    modifier = Modifier
                        .padding(top = 20.dp)
                )
                BuildDropdown(
                    dropdownValue = typeDropdownValue,
                    options = arrayListOf(
                        "學生票", "全票", "半票"
                    ),
                    modifier = Modifier
                        .padding(top = 20.dp)
                )
                BuildDropdown(
                    dropdownValue = exhibitDropdownValue,
                    options = arrayListOf(
                        "展覽一", "展覽二", "展覽三"
                    ),
                    modifier = Modifier
                        .padding(top = 20.dp)
                )
                Button(
                    onClick = {
                        if (
                            !nameInputValue.value.second ||
                            !phoneInputValue.value.second ||
                            !emailInputValue.value.second ||
                            typeDropdownValue.value != "N/A" ||
                            exhibitDropdownValue.value != "N/A"
                        ) {
                            isAddSuccess.value = true
                            sqliteHelper.addTicket(
                                nameInputValue.value.first,
                                phoneInputValue.value.first,
                                emailInputValue.value.first,
                                typeDropdownValue.value,
                                exhibitDropdownValue.value
                            )
                        } else {
                            isAddSuccess.value = false
                        }
                        showDialog.value = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 50.dp)
                        .padding(top = 20.dp)
                ) {
                    Text(text = "Submit")
                }
                Button(
                    onClick = {
                        navController.navigate("importTicket")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 50.dp)
                        .padding(top = 20.dp)
                ) {
                    Text(text = "importTicket")
                }
                Button(
                    onClick = {
                        navController.navigate("viewTickets")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 50.dp)
                        .padding(top = 20.dp)
                ) {
                    Text(text = "viewTickets")
                }

            }
            if (showDialog.value) {
                Dialog(
                    onDismissRequest = {
                        showDialog.value = false
                    }
                ) {
                    BuildDialog(showDialog, isSuccess = isAddSuccess.value)
                }
            }
        }
    }

    @Composable
    private fun BuildTextInput(textFieldValue: MutableState<Pair<String, Boolean>>, pattern: String, placeholder: String, modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .background(
                    Color.White,
                    RoundedCornerShape(10.dp)
                )
                .border(
                    2.dp,
                    Color.DarkGray,
                    RoundedCornerShape(10.dp)
                )
        ) {
            TextField(
                value = textFieldValue.value.first,
                placeholder = { Text(placeholder) },
                singleLine = true,
                onValueChange = {
                    textFieldValue.value = Pair(it, it.matches(Regex(pattern)))
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.DarkGray
                ),
                modifier = Modifier
                    .align(Alignment.CenterStart)
            )
        }
    }

    @Composable
    private fun <T> BuildDropdown(dropdownValue: MutableState<T>, options: ArrayList<T>, modifier: Modifier = Modifier) {
        val isExpanded = remember {
            mutableStateOf(false)
        }
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 40.dp)
                .background(
                    Color.White,
                    RoundedCornerShape(10.dp)
                )
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
                text = dropdownValue.value.toString(),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
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
                            dropdownValue.value = option
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
    private fun BuildDialog(showDialog: MutableState<Boolean>, isSuccess: Boolean) {
        Card(
            modifier = Modifier
                .size(320.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                if (isSuccess) {
                    Icon(Icons.Default.Check, contentDescription = null)
                } else {
                    Icon(Icons.Default.Close, contentDescription = null)
                }
            }
        }
    }
}