package com.example.speedrun4.ui.page

import androidx.compose.animation.animateColorAsState
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
import com.example.speedrun4.service.SqliteHelper
import com.example.speedrun4.ui.widget.TopNavBar

object BuyTicketPage {
    @Composable
    fun Build(navController: NavController) {
        val nameInputValue = remember {
            mutableStateOf(Pair("", true))
        }
        val phoneInputValue = remember {
            mutableStateOf(Pair("", true))
        }
        val emailInputValue = remember {
            mutableStateOf(Pair("", true))
        }
        val typeInputValue = remember {
            mutableStateOf("N/A")
        }
        val exhibitInputValue = remember {
            mutableStateOf("N/A")
        }
        val isSuccess = remember {
            mutableStateOf(false)
        }
        val showDialog = remember {
            mutableStateOf(false)
        }
        val sqliteHelper = SqliteHelper(LocalContext.current)

        if (showDialog.value) {
            Dialog(
                onDismissRequest = {
                    showDialog.value = false
                }
            ) {
                BuildDialog(showDialog = showDialog, isSuccess = isSuccess.value)
            }
        }

        Scaffold(
            topBar = { TopNavBar.Build(title = "buyTicket", navController = navController) }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
            ) {
                BuildTextField(
                    inputValue = nameInputValue,
                    pattern = ".*",
                    placeholder = "Name",
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
                BuildTextField(
                    inputValue = phoneInputValue,
                    pattern = ".*",
                    placeholder = "Phone",
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
                BuildTextField(
                    inputValue = emailInputValue,
                    pattern = ".*",
                    placeholder = "Email",
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
                BuildDropdown(
                    selectedValue = typeInputValue,
                    options = arrayListOf(
                        "全票", "學生票", "敬老票"
                    ),
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
                BuildDropdown(
                    selectedValue = exhibitInputValue,
                    options = arrayListOf(
                        "展覽一", "展覽二", "展覽三"
                    ),
                    modifier = Modifier
                        .padding(top = 10.dp)
                )

                Button(
                    onClick = {
                       if (
                           nameInputValue.value.second &&
                           phoneInputValue.value.second &&
                           emailInputValue.value.second &&
                           typeInputValue.value != "N/A" &&
                           exhibitInputValue.value != "N/A"
                       ) {
                           sqliteHelper.addTicket(
                               nameInputValue.value.first,
                               phoneInputValue.value.first,
                               emailInputValue.value.first,
                               typeInputValue.value,
                               exhibitInputValue.value
                           )
                           isSuccess.value = true
                       } else {
                           isSuccess.value = false
                       }
                        showDialog.value = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 50.dp)
                        .padding(top = 10.dp)
                ) {
                    Text(text = "Submit")
                }

                Button(
                    onClick = {
                        navController.navigate("viewTickets")
                    },
                    modifier = Modifier
                        .padding(top = 60.dp)
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 50.dp)
                ) {
                    Text(text = "viewTickets")
                }
                Button(
                    onClick = {
                        navController.navigate("importTicket")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 50.dp)
                        .padding(top = 10.dp)
                ) {
                    Text(text = "importTicket")
                }
            }
        }
    }

    @Composable
    private fun BuildTextField(inputValue: MutableState<Pair<String, Boolean>>, placeholder: String, pattern: String, modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 40.dp)
                .border(
                    2.dp,
                    animateColorAsState(
                        targetValue = if (inputValue.value.second) Color.DarkGray else Color.Red
                    ).value,
                    RoundedCornerShape(10.dp)
                )
                .background(Color.Transparent)
        ) {
            TextField(
                value = inputValue.value.first,
                onValueChange = {
                    inputValue.value = Pair(
                        it.replace("\n", ""),
                        Regex(pattern).matches(it.replace("\n", ""))
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black,
                    cursorColor = Color.DarkGray,
                    placeholderColor = Color.LightGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                placeholder = { Text(text = placeholder) },
                modifier = Modifier
                    .align(Alignment.CenterStart)
            )
        }
    }

    @Composable
    private fun <T> BuildDropdown(selectedValue: MutableState<T>, options: ArrayList<T>, modifier: Modifier = Modifier) {
        val expanded = remember {
            mutableStateOf(false)
        }
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 40.dp)
                .border(
                    2.dp,
                    Color.DarkGray,
                    RoundedCornerShape(10.dp)
                )
                .background(Color.Transparent)
                .clickable {
                    expanded.value = !expanded.value
                }
        ) {
            Text(
                text = selectedValue.value.toString(),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
            )
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = {
                    expanded.value = false
                },
                modifier = Modifier
                    .width((LocalConfiguration.current.screenWidthDp - 80).dp)
            ) {
                for (item in options) {
                    DropdownMenuItem(
                        onClick = {
                            selectedValue.value = item
                            expanded.value = false
                        }
                    ) {
                        Text(text = item.toString())
                    }
                }
            }
        }
    }

    @Composable
    private fun BuildDialog(showDialog: MutableState<Boolean>, isSuccess: Boolean) {
        Card(
            modifier = Modifier
                .size(300.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Icon(
                    if (isSuccess) Icons.Default.Check else Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
}
