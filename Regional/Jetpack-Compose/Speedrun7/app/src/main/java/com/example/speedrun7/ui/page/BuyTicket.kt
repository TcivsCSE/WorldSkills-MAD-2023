package com.example.speedrun7.ui.page

import android.app.DatePickerDialog
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.speedrun7.service.SqliteHelper
import com.example.speedrun7.ui.widget.TopNavBar
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

object BuyTicketPage {
    @Composable
    fun Build(navController: NavController) {
        val nameInputValue = remember {
            mutableStateOf(Pair("", false))
        }
        val emailInputValue = remember {
            mutableStateOf(Pair("", false))
        }
        val phoneInputValue = remember {
            mutableStateOf(Pair("", false))
        }
        val typeInputValue = remember {
            mutableStateOf("N/A")
        }
        val exhibitInputValue = remember {
            mutableStateOf("N/A")
        }
        val dateInputValue = remember {
            mutableStateOf(SimpleDateFormat("yyyy/MM/dd", Locale.US).format(Calendar.getInstance().time))
        }
        val sqliteHelper = SqliteHelper(LocalContext.current)
        val showDialog = remember {
            mutableStateOf(false)
        }
        val isSuccess = remember {
            mutableStateOf(false)
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
                        .padding(top = 20.dp)
                )
                BuildTextField(
                    inputValue = emailInputValue,
                    pattern = ".*",
                    placeholder = "Email",
                    modifier = Modifier
                        .padding(top = 20.dp)
                )
                BuildTextField(
                    inputValue = phoneInputValue,
                    pattern = ".*",
                    placeholder = "Phone",
                    modifier = Modifier
                        .padding(top = 20.dp)
                )
                BuildDropdown(
                    selected = typeInputValue,
                    options = arrayListOf(
                        "全票", "學生票", "敬老票"
                    ),
                    modifier = Modifier
                        .padding(top = 20.dp)
                )
                BuildDropdown(
                    selected = exhibitInputValue,
                    options = arrayListOf(
                        "展覽一", "展覽二", "展覽三"
                    ),
                    modifier = Modifier
                        .padding(top = 20.dp)
                )
                BuildDatePicker(
                    inputValue = dateInputValue,
                    modifier = Modifier
                        .padding(top = 20.dp)
                )
                Button(
                    onClick = {
                        if (
                            nameInputValue.value.second &&
                            emailInputValue.value.second &&
                            phoneInputValue.value.second &&
                            typeInputValue.value != "N/A" &&
                            exhibitInputValue.value != "N/A"
                        ) {
                            sqliteHelper.addTicket(
                                nameInputValue.value.first,
                                emailInputValue.value.first,
                                phoneInputValue.value.first,
                                typeInputValue.value,
                                exhibitInputValue.value,
                                dateInputValue.value
                            )
                            isSuccess.value = true
                        } else {
                            isSuccess.value = false
                        }
                        showDialog.value = true
                    },
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 50.dp)
                ) {
                    Text(text = "Submit")
                }
                Button(
                    onClick = {
                        navController.navigate("importTicket")
                    },
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 50.dp)
                ) {
                    Text(text = "importTicket")
                }
                Button(
                    onClick = {
                        navController.navigate("viewTickets")
                    },
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 50.dp)
                ) {
                    Text(text = "viewTickets")
                }
            }
        }
        if (showDialog.value) {
            Dialog(
                onDismissRequest = {
                    showDialog.value = false
                }
            ) {
                BuildDialog(isSuccess = isSuccess.value)
            }
        }
    }

    @Composable
    private fun BuildDialog(isSuccess: Boolean) {
        Card(
            modifier = Modifier
                .size(300.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Icon(
                    imageVector = if (isSuccess) Icons.Default.Check else Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .scale(2.0f)
                )
            }
        }
    }

    @Composable
    private fun BuildTextField(
        inputValue: MutableState<Pair<String, Boolean>>,
        pattern: String,
        placeholder: String,
        modifier: Modifier = Modifier
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .height(60.dp)
                .border(
                    2.dp,
                    animateColorAsState(
                        targetValue = if (inputValue.value.second) Color.DarkGray else Color.Red
                    ).value,
                    RoundedCornerShape(10.dp)
                )
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
                isError = !inputValue.value.second,
                placeholder = { Text(text = placeholder) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.DarkGray
                )
            )
        }
    }

    @Composable
    private fun <T> BuildDropdown(selected: MutableState<T>, options: ArrayList<T>, modifier: Modifier = Modifier) {
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
                text = selected.value.toString(),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 14.dp)
            )
            DropdownMenu(
                expanded = isExpanded.value,
                onDismissRequest = { isExpanded.value = false },
                modifier = Modifier
                    .width((LocalConfiguration.current.screenWidthDp - 80).dp)
            ) {
                for (item in options) {
                    DropdownMenuItem(
                        onClick = {
                            selected.value = item
                            isExpanded.value = false
                        }
                    ) {
                        Text(text = item.toString())
                    }
                }
            }
        }
    }

    @Composable
    private fun BuildDatePicker(inputValue: MutableState<String>, modifier: Modifier = Modifier) {
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
                            inputValue.value =
                                SimpleDateFormat("yyyy/MM/dd", Locale.US).format(c.time)
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                    ).show()
                }
        ) {
            Text(
                text = inputValue.value,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 14.dp)
            )
        }
    }
}