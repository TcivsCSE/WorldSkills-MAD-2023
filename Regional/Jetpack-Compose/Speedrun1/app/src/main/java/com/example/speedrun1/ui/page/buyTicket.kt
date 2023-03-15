package com.example.speedrun1.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.speedrun1.service.SqliteHelper
import com.example.speedrun1.ui.widget.TopNavBar

object BuyTicketPage {
    @Composable
    fun Build(navController: NavController) {
        val sqliteHelper = SqliteHelper(LocalContext.current)

        val emailInput = remember {
            mutableStateOf("")
        }
        val nameInput = remember {
            mutableStateOf("")
        }
        val phoneInput = remember {
            mutableStateOf("")
        }
        val exhibitInput = remember {
            mutableStateOf("N/A")
        }
        val ticketTypeInput = remember {
            mutableStateOf("N/A")
        }
        Scaffold(
            topBar = { TopNavBar.Build(title = "購票系統", navController = navController) }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
            ) {
                BuildTextInput(
                    inputValue = nameInput,
                    placeholder = "Name",
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
                BuildTextInput(
                    inputValue = emailInput,
                    placeholder = "Email",
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
                BuildTextInput(
                    inputValue = phoneInput,
                    placeholder = "Phone",
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
                BuildDropDown(
                    selectedItem = exhibitInput,
                    items = arrayListOf(
                        "展覽一", "展覽二", "展覽三"
                    ),
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
                BuildDropDown(
                    selectedItem = ticketTypeInput,
                    items = arrayListOf(
                        "敬老票", "半票", "全票"
                    ),
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
                Button(
                    onClick = {
                        sqliteHelper.addTicket(
                            nameInput.value,
                            emailInput.value,
                            phoneInput.value,
                            exhibitInput.value,
                            ticketTypeInput.value
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 30.dp)
                        .padding(top = 16.dp)
                ) {
                    Text(text = "購買")
                }
            }
        }
    }

    @Composable
    private fun BuildTextInput(inputValue: MutableState<String>, placeholder: String, modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                .border(width = 2.dp, color = Color.DarkGray, shape = RoundedCornerShape(10.dp))
        ) {
            TextField(
                value = inputValue.value,
                placeholder = { Text(text = placeholder) },
                onValueChange = {
                    inputValue.value = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.DarkGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }

    @Composable
    private fun <E> BuildDropDown(selectedItem: MutableState<E>, items: ArrayList<E>, modifier: Modifier = Modifier) {
        val expanded = remember {
            mutableStateOf(false)
        }
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                .border(width = 2.dp, color = Color.DarkGray, shape = RoundedCornerShape(10.dp))
                .height(60.dp)
                .clickable {
                    expanded.value = !expanded.value
                }
        ) {
            Text(
                text = selectedItem.value.toString(),
                fontSize = 16.sp,
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
                    .width((LocalConfiguration.current.screenWidthDp - 60).dp)
            ) {
                for (item in items) {
                    DropdownMenuItem(
                        onClick = {
                            selectedItem.value = item
                            expanded.value = false
                        }
                    ) {
                        Text(text = item.toString())
                    }
                }
            }
        }
    }
}