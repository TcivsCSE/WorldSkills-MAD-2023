package com.example.speedrun5.ui.page

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.speedrun5.R
import com.example.speedrun5.ui.widget.ViewPager
import com.example.speedrun5.ui.widget.ViewPagerItemData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class NewsData(
    val title: String,
    val author: String,
    val datetime: String,
    val content: String,
)

object HomePage {
    @Composable
    fun Build(navController: NavController) {
        val scaffoldState = rememberScaffoldState(
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        )
        val coroutineScope = rememberCoroutineScope()
        val newsData = arrayListOf(
            NewsData("test", "test", "test", "test"),
            NewsData("test", "test", "test", "test"),
            NewsData("test", "test", "test", "test"),
            NewsData("test", "test", "test", "test"),
            NewsData("test", "test", "test", "test"),
            NewsData("test", "test", "test", "test"),
            NewsData("test", "test", "test", "test"),
            NewsData("test", "test", "test", "test"),
            NewsData("test", "test", "test", "test"),
            NewsData("test", "test", "test", "test"),
        )
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { BuildTopBar(coroutineScope, drawerState = scaffoldState.drawerState) },
            drawerContent = { BuildDrawerContent(coroutineScope, drawerState = scaffoldState.drawerState, navController) },
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
            ) {
                ViewPager.Build(
                    vpItems = arrayListOf(
                        ViewPagerItemData(imageSource = R.drawable.mol),
                        ViewPagerItemData(imageSource = R.drawable.mol),
                        ViewPagerItemData(imageSource = R.drawable.mol),
                        ViewPagerItemData(imageSource = R.drawable.mol),
                    ),
                    modifier = Modifier
                        .padding(top = 20.dp)
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .height(300.dp)
                        .padding(horizontal = 40.dp)
                ) {
                    itemsIndexed(newsData) { itemIndex ,itemData ->
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .height(40.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(39.dp)
                            ) {
                                Text(text = (itemIndex + 1).toString())
                                Text(text = itemData.title)
                            }
                            Divider()
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun BuildTopBar(coroutineScope: CoroutineScope, drawerState: DrawerState) {
        TopAppBar(
            backgroundColor = Color.White,
            contentColor = Color.Black
        ) {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        if (drawerState.isOpen) drawerState.close() else drawerState.open()
                    }
                }
            ) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
            Text(
                "南港展覽館導覽",
                color = Color.Black,
                fontSize = 18.sp
            )
        }
    }
    
    @Composable
    private fun BuildDrawerContent(coroutineScope: CoroutineScope, drawerState: DrawerState, navController: NavController) {
        Column(
            modifier = Modifier
                .padding(4.dp)
        ) {
            Row() {
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            if (drawerState.isOpen) drawerState.close() else drawerState.open()
                        }
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }
            Button(
                onClick = {
                    navController.navigate("buyTicket")
                },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 56.dp)
            ) {
                Text(text = "buyTicket")
            }
            Button(
                onClick = {
                    navController.navigate("exhibitHallInfo")
                },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 56.dp)
            ) {
                Text(text = "exhibitHallInfo")
            }
        }
    }
}
