package com.example.basicbuild.ui.page

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.basicbuild.service.SqliteHelper
import com.example.basicbuild.ui.component.TopNavBar

object BuyTicketPage {
    @Composable
    fun Build(navController: NavController) {
        val exhibitName = remember {
            mutableStateOf("N/A")
        }
        val ticketType = remember {
            mutableStateOf(Pair("N/A", 0))
        }
        val ticketData: LinkedHashMap<String, Int> = LinkedHashMap()
        ticketData["全票"] = 500
        ticketData["學生票"] = 400
        ticketData["兒童票"] = 250
        ticketData["敬老票"] = 100

        val nameTextFieldValue = remember {
            mutableStateOf(Pair("", false))
        }
        val emailTextFieldValue = remember {
            mutableStateOf(Pair("", false))
        }
        val phoneTextFieldValue = remember {
            mutableStateOf(Pair("", false))
        }


        val openDialog = remember {
            mutableStateOf(false)
        }
        val dialogStatus = remember {
            mutableStateOf(false)
        }
        val dialogMessage = remember {
            mutableStateOf("")
        }

        val sqliteHelper = SqliteHelper(LocalContext.current, null)

        Column(
            Modifier
                .fillMaxHeight()
        ) {
            TopNavBar.Build(navController, "購票系統")
            ExhibitDropdownMenu(
                exhibitName,
                listOf("展覽一", "展覽二", "展覽三", "展覽四", "展覽五"),
                modifier = Modifier
                    .padding(top = 10.dp)
            )
            TicketTypeDropdownMenu(
                ticketType,
                ticketData.toList(),
                modifier = Modifier
                    .padding(top = 10.dp)
            )
            CustomTextField(
                textFieldValue = nameTextFieldValue,
                placeholder = "姓名",
                regexPattern = Regex(".*"),
                modifier = Modifier
                    .padding(top = 10.dp)
            )
            CustomTextField(
                textFieldValue = emailTextFieldValue,
                placeholder = "電子郵箱",
                regexPattern = Regex(""".+@.+\..+"""),
                modifier = Modifier
                    .padding(top = 10.dp)
            )
            CustomTextField(
                textFieldValue = phoneTextFieldValue,
                placeholder = "行動電話",
                regexPattern = Regex("""\d{10}"""),
                modifier = Modifier
                    .padding(top = 10.dp)
            )
            Button(
                onClick = {
                    if (exhibitName.value != "N/A" &&
                        ticketType.value != Pair("N/A", 0) &&
                        !nameTextFieldValue.value.second &&
                        !emailTextFieldValue.value.second &&
                        !phoneTextFieldValue.value.second
                    ) {
                        dialogStatus.value = true
                        dialogMessage.value = "購買成功"
                        openDialog.value = true
                        sqliteHelper.addTicket(
                            exhibitName.value,
                            ticketType.value.first,
                            ticketData[ticketType.value.first]!!,
                            nameTextFieldValue.value.first,
                            emailTextFieldValue.value.first,
                            phoneTextFieldValue.value.first
                        )
                        exhibitName.value = "N/A"
                        ticketType.value = Pair("N/A", 0)
                        nameTextFieldValue.value = Pair("", false)
                        emailTextFieldValue.value = Pair("", false)
                        phoneTextFieldValue.value = Pair("", false)
                    } else {
                        dialogStatus.value = false
                        dialogMessage.value = "購買失敗"
                        openDialog.value = true
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.DarkGray,

                    ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 70.dp)
                    .padding(top = 10.dp)
            ) {
                Text(
                    text = "提交",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
            if (openDialog.value) {
                Dialog(
                    onDismissRequest = { openDialog.value = false },
                    properties = DialogProperties(
                        dismissOnBackPress = false,
                        dismissOnClickOutside = false
                    )
                ) {
                    SubmitDialog(isSuccess = dialogStatus.value, message = dialogMessage.value, openDialog = openDialog)
                }
            }
            Button(
                onClick = {
                    navController.navigate("viewTickets")
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.LightGray,
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 160.dp)
                    .padding(horizontal = 40.dp)
                    .height(60.dp)
            ) {
                Text(
                    text = "查看已擁有票券",
                    fontSize = 18.sp
                )
            }
            Button(
                onClick = {
                    navController.navigate("scanQrcode")
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.LightGray,
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .padding(horizontal = 40.dp)
                    .height(60.dp)
            ) {
                Text(
                    text = "掃描 QRCode 匯入票券",
                    fontSize = 18.sp
                )
            }
        }
    }

    @Composable
    private fun ExhibitDropdownMenu(selectedValue: MutableState<String>, options: List<String>, modifier: Modifier = Modifier) {
        val dropdownToggle = remember {
            mutableStateOf(false)
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .padding(horizontal = 30.dp)
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .border(1.5.dp, Color.Gray, RoundedCornerShape(10.dp))
                    .clickable {
                        dropdownToggle.value = !dropdownToggle.value
                    }
            ) {
                Text(
                    text = selectedValue.value,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 12.dp)
                ) {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier
                            .rotate(
                                animateFloatAsState(
                                    targetValue = if (dropdownToggle.value) 0.0f else 180.0f
                                ).value
                            )
                    )
                }
            }
            DropdownMenu(
                expanded = dropdownToggle.value,
                onDismissRequest = {
                    dropdownToggle.value = false
                },
                offset = DpOffset(x = 36.dp, y = 0.dp),
                modifier = Modifier
                    .width((LocalConfiguration.current.screenWidthDp - (36 * 2)).dp)
            ) {
                options.forEach {
                    DropdownMenuItem(
                        onClick = {
                            selectedValue.value = it
                            dropdownToggle.value = false
                        },
                        modifier = Modifier
                            .background(if (selectedValue.value == it) Color.LightGray else Color.White)
                    ) {
                        Text(text = it)
                    }
                    Divider()
                }
            }
        }
    }

    @Composable
    private fun TicketTypeDropdownMenu(selectedValue: MutableState<Pair<String, Int>>, options: List<Pair<String, Int>>, modifier: Modifier = Modifier) {
        val dropdownToggle = remember {
            mutableStateOf(false)
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .padding(horizontal = 30.dp)
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .border(1.5.dp, Color.Gray, RoundedCornerShape(10.dp))
                    .clickable {
                        dropdownToggle.value = !dropdownToggle.value
                    }
            ) {
                Text(
                    text = "${selectedValue.value.first} - ${selectedValue.value.second}元",
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 12.dp)
                ) {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier
                            .rotate(
                                animateFloatAsState(
                                    targetValue = if (dropdownToggle.value) 0.0f else 180.0f
                                ).value
                            )
                    )
                }
            }
            DropdownMenu(
                expanded = dropdownToggle.value,
                onDismissRequest = {
                    dropdownToggle.value = false
                },
                offset = DpOffset(x = 36.dp, y = 0.dp),
                modifier = Modifier
                    .width((LocalConfiguration.current.screenWidthDp - (36 * 2)).dp)
            ) {
                options.forEach {
                    DropdownMenuItem(
                        onClick = {
                            selectedValue.value = it
                            dropdownToggle.value = false
                        },
                        modifier = Modifier
                            .background(if (selectedValue.value == it) Color.LightGray else Color.White)
                    ) {
                        Text(text = "${it.first} - ${it.second}元")
                    }
                    Divider()
                }
            }
        }
    }

    @Composable
    private fun CustomTextField(textFieldValue: MutableState<Pair<String, Boolean>>, placeholder: String, regexPattern: Regex, modifier: Modifier = Modifier) {
        TextField(
            value = textFieldValue.value.first,
            isError = textFieldValue.value.second,
            onValueChange = {
                textFieldValue.value = Pair(it, !regexPattern.matches(it))
            },
            shape = RoundedCornerShape(10.dp),
            placeholder = { Text(text = placeholder) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                cursorColor = Color.DarkGray
            ),
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .height(54.dp)
                .background(Color.Transparent)
                .border(
                    1.5.dp,
                    animateColorAsState(
                        targetValue = if (textFieldValue.value.second) Color.Red else Color.Gray,
                        tween(durationMillis = 350)
                    ).value,
                    RoundedCornerShape(10.dp)
                )
        )
    }

    @Composable
    private fun SubmitDialog(isSuccess: Boolean, message: String, openDialog: MutableState<Boolean>) {
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .size(260.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(vertical = 30.dp)
            ) {
                Icon(
                    if (isSuccess) Icons.Default.Check else Icons.Default.Close,
                    null,
                    modifier = Modifier
                        .size(90.dp)
                )
                Text(
                    text = message,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
                Box(
                    modifier = Modifier
                        .size(84.dp, 40.dp)
                        .border(1.5.dp, Color.DarkGray, RoundedCornerShape(10.dp))
                        .clickable {
                            openDialog.value = false
                        }
                ) {
                    Text(
                        "確認",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}