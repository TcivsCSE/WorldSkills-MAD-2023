package com.example.speedrun4.ui.page

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
import com.example.speedrun4.R
import com.example.speedrun4.ui.widget.ViewPager
import com.example.speedrun4.ui.widget.ViewPagerItemData
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
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { BuildTopAppBar(drawerState = scaffoldState.drawerState, coroutineScope, navController) },
            drawerContent = { BuildDrawerContent(
                drawerState = scaffoldState.drawerState,
                coroutineScope = coroutineScope,
                navController = navController
            ) }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
            ) {
                ViewPager.Build(
                    viewPagerItems = arrayListOf(
                        ViewPagerItemData(imageSource = R.drawable.mol),
                        ViewPagerItemData(imageSource = R.drawable.mol),
                        ViewPagerItemData(imageSource = R.drawable.mol),
                        ViewPagerItemData(imageSource = R.drawable.mol),
                    ),
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
                BuildNewsList(newsData = arrayListOf(
                    NewsData("test", "test", "test", "test"),
                    NewsData("test", "test", "test", "test"),
                    NewsData("test", "test", "test", "test"),
                    NewsData("test", "test", "test", "test"),
                    NewsData("test", "test", "test", "test"),
                    NewsData("test", "test", "test", "test"),
                    NewsData("test", "test", "test", "test"),
                ), modifier = Modifier.padding(top = 10.dp))
            }
        }
    }

    @Composable
    private fun BuildTopAppBar(drawerState: DrawerState, coroutineScope: CoroutineScope, navController: NavController) {
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
                text = "南港展覽館導覽",
                color = Color.Black
            )
        }
    }

    @Composable
    private fun BuildDrawerContent(drawerState: DrawerState, coroutineScope: CoroutineScope, navController: NavController) {
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
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 40.dp)
                    .padding(top = 10.dp)
            ) {
                Text(text = "buyTicket")
            }
            Button(
                onClick = {
                    navController.navigate("exhibitHallInfo")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 40.dp)
                    .padding(top = 10.dp)
            ) {
                Text(text = "exhibitHallInfo")
            }
        }
    }

    @Composable
    private fun  BuildNewsList(newsData: ArrayList<NewsData>, modifier: Modifier = Modifier) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .height(240.dp)
                .padding(horizontal = 40.dp)
        ) {
            itemsIndexed(newsData) { itemIndex, itemData ->
                Column(
                    modifier = Modifier
                        .height(40.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
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