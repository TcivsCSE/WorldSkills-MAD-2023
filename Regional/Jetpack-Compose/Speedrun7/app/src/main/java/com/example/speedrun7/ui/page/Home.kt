package com.example.speedrun7.ui.page

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
import com.example.speedrun7.R
import com.example.speedrun7.ui.widget.ViewPager
import com.example.speedrun7.ui.widget.ViewPagerItemData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class NewsData(
    val title: String,
)

object HomePage {
    @Composable
    fun Build(navController: NavController) {
        val scaffoldState = rememberScaffoldState(
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        )
        val coroutineScope = rememberCoroutineScope()
        val newsData = arrayListOf(
            NewsData("test title"),
            NewsData("test title"),
            NewsData("test title"),
            NewsData("test title"),
            NewsData("test title"),
            NewsData("test title"),
        )
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { BuildTopBar(coroutineScope, scaffoldState.drawerState) },
            drawerContent = { BuildDrawerContent(
                coroutineScope,
                scaffoldState.drawerState,
                navController
            ) }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
            ) {
                ViewPager.Build(
                    vpItems = arrayListOf(
                        ViewPagerItemData(R.drawable.mol),
                        ViewPagerItemData(R.drawable.mol),
                        ViewPagerItemData(R.drawable.mol),
                        ViewPagerItemData(R.drawable.mol),
                    ),
                    modifier = Modifier
                        .padding(top = 20.dp)
                )
                LazyColumn(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp)
                        .height(200.dp)
                ) {
                    itemsIndexed(newsData) { itemIndex, itemData ->
                        Column(
                            modifier = Modifier
                                .height(60.dp)
                                .fillMaxWidth()
                                .padding(horizontal = 40.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .height(59.dp)
                            ) {
                                Text(text = (itemIndex + 1).toString())
                                Text(
                                    text = itemData.title,
                                    modifier = Modifier
                                        .padding(start = 10.dp)
                                )
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
                onClick =  {
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
                color = Color.Black
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
                    onClick =  {
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
                    .padding(horizontal = 40.dp)
                    .height(60.dp)
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
                    .padding(horizontal = 40.dp)
                    .height(60.dp)
            ) {
                Text(text = "exhibitHallInfo")
            }
        }
    }
}