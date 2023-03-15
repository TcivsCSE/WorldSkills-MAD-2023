package com.example.speedrun3.ui.page

import android.os.Build
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
import com.example.speedrun3.service.SqliteHelper
import com.example.speedrun3.ui.widget.TopNavBar

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
        val showDialog = remember {
            mutableStateOf(false)
        }
        val isSuccess = remember {
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
                BuildTextInput(
                    inputValue = nameInputValue,
                    pattern = ".*",
                    placeholder = "Name",
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
                BuildTextInput(
                    inputValue = phoneInputValue,
                    pattern = ".*",
                    placeholder = "Phone",
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
                BuildTextInput(
                    inputValue = emailInputValue,
                    pattern = ".*",
                    placeholder = "Email",
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
                BuildDropdown(
                    selectedItem = typeInputValue,
                    options = arrayListOf(
                        "全票", "學生票", "敬老票"
                    ),
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
                BuildDropdown(
                    selectedItem = exhibitInputValue,
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
                            isSuccess.value = true
                            sqliteHelper.addTicket(
                                nameInputValue.value.first,
                                phoneInputValue.value.first,
                                emailInputValue.value.first,
                                typeInputValue.value,
                                exhibitInputValue.value
                            )
                        } else {
                            isSuccess.value = false
                        }
                        showDialog.value = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp)
                        .padding(top = 14.dp)
                        .height(60.dp)
                ) {
                    Text(text = "Submit")
                }
                Button(
                    onClick = {
                        navController.navigate("viewTickets")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp)
                        .padding(top = 40.dp)
                        .height(60.dp)
                ) {
                    Text(text = "viewTickets")
                }
                Button(
                    onClick = {
                        navController.navigate("importTicket")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp)
                        .padding(top = 10.dp)
                        .height(60.dp)
                ) {
                    Text(text = "importTicket")
                }
            }
        }
    }

    @Composable
    private fun BuildTextInput(
        inputValue: MutableState<Pair<String, Boolean>>, 
        pattern: String, 
        placeholder: String, 
        modifier: Modifier = Modifier
    ) {
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
                singleLine = true,
                placeholder = { Text(text = placeholder) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black,
                    cursorColor = Color.DarkGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .align(Alignment.CenterStart)
            )
        }
    }
    
    @Composable
    private fun <T> BuildDropdown(
        selectedItem: MutableState<T>, 
        options: ArrayList<T>, 
        modifier: Modifier = Modifier
    ) {
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
                text = selectedItem.value.toString(),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(horizontal = 16.dp)
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
                            selectedItem.value = item
                            expanded.value = false
                        }
                    ) {
                        Text(
                            text = item.toString()
                        )
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
            Icon(
                if (isSuccess) Icons.Default.Check else Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(60.dp)
            )
        }
    }
}
