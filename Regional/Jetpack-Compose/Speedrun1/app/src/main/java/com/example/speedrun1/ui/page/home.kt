package com.example.speedrun1.ui.page

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
import androidx.navigation.NavController
import com.example.speedrun1.R
import com.example.speedrun1.ui.widget.ViewPager
import com.example.speedrun1.ui.widget.ViewPagerItemData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class NewsData(
    val title: String,
    val author: String,
    val datetime: String
)


object HomePage {
    @Composable
    fun Build(navController: NavController) {
        val coroutineScope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState(
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        )
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { BuildAppBar(scaffoldState.drawerState, coroutineScope) },
            drawerContent = { BuildDrawerContent(scaffoldState.drawerState, coroutineScope, navController) }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
            ) {
                ViewPager.Build(
                    viewPagerItems = arrayListOf(
                        ViewPagerItemData(
                            R.drawable.tainex_ico
                        ),
                        ViewPagerItemData(
                            R.drawable.tainex_ico
                        ),
                        ViewPagerItemData(
                            R.drawable.tainex_ico
                        ),
                    ),
                    modifier = Modifier
                        .padding(top = 20.dp)
                )
                BuildNewsList(newsData = arrayListOf(
                    NewsData("test", "test", "test"),
                    NewsData("test", "test", "test"),
                    NewsData("test", "test", "test"),
                    NewsData("test", "test", "test"),
                    NewsData("test", "test", "test"),
                    NewsData("test", "test", "test"),
                    NewsData("test", "test", "test"),
                    NewsData("test", "test", "test"),
                    NewsData("test", "test", "test"),
                    NewsData("test", "test", "test"),
                ))
            }
        }
    }

    @Composable
    private fun BuildAppBar(drawerState: DrawerState, coroutineScope: CoroutineScope) {
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
                Icon(Icons.Default.Menu, contentDescription = null, tint = Color.Black)
            }
            Text(
                text = "南港展覽館導覽",
                color = Color.Black
            )
        }
    }

    @Composable
    private fun BuildDrawerContent(drawerState: DrawerState, coroutineScope: CoroutineScope, navController: NavController) {
        Column() {
            Row() {
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            if (drawerState.isOpen) drawerState.close() else drawerState.open()
                        }
                    },
                    modifier = Modifier
                        .padding(4.dp)
                ) {
                    Icon(Icons.Default.Close, contentDescription = null, tint = Color.Black)
                }
            }
            Button(
                onClick = {
                    navController.navigate("buyTicket")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ) {
                Text(text = "購票系統")
            }
            Button(
                onClick = {
                    navController.navigate("")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .padding(top = 10.dp)
            ) {
                Text(text = "asdasdas")
            }
        }
    }
    
    @Composable
    private fun BuildNewsList(newsData: ArrayList<NewsData>) {
        LazyColumn(
            modifier = Modifier
                .height(280.dp)
        ) {
            itemsIndexed(newsData) { newsIndex, newsData ->
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .height(50.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = (newsIndex + 1).toString())
                        Text(text = newsData.title)
                    }
                    Divider()
                }
            }
        }
    } 
}