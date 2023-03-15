package com.example.speedrun3.ui.page

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.example.speedrun3.R
import com.example.speedrun3.ui.widget.TopNavBar
import com.example.speedrun3.ui.widget.ViewPager
import com.example.speedrun3.ui.widget.ViewPagerItemData
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
            topBar = { BuildAppBar(drawerState = scaffoldState.drawerState, coroutineScope = coroutineScope) },
            drawerContent = { BuildDrawerContent(scaffoldState.drawerState, navController, coroutineScope) }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                 ViewPager.Build(
                     viewPagerItem = arrayListOf(
                        ViewPagerItemData(R.drawable.mol),
                        ViewPagerItemData(R.drawable.mol),
                        ViewPagerItemData(R.drawable.mol),
                        ViewPagerItemData(R.drawable.mol),
                    ),
                     modifier = Modifier
                         .padding(top = 20.dp)
                 )
                BuildNewsColumn(
                    arrayListOf(
                        NewsData("test", "test", "test", "test"),
                        NewsData("test", "test", "test", "test"),
                        NewsData("test", "test", "test", "test"),
                        NewsData("test", "test", "test", "test"),
                        NewsData("test", "test", "test", "test"),
                        NewsData("test", "test", "test", "test"),
                    )
                )
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
                Icon(
                    Icons.Default.Menu,
                    contentDescription = null,
                    tint = Color.Black,
                )
            }
            Text(
                text = "南港展覽館導覽",
                color = Color.Black,
                fontSize = 18.sp
            )
        }
    }

    @Composable
    private fun BuildDrawerContent(drawerState: DrawerState, navController: NavController, coroutineScope: CoroutineScope) {
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
                    .padding(horizontal = 40.dp)
                    .height(40.dp)
            ) {
                Text(text = "buyTicket")
            }
            Button(
                onClick = {
                    navController.navigate("exhibitHallInfo")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .padding(top = 10.dp)
                    .height(40.dp)
            ) {
                Text(text = "exhibitHallInfo")
            }
        }
    }

    @Composable
    private fun BuildNewsColumn(data: ArrayList<NewsData>) {
        LazyColumn() {
            itemsIndexed(data) { itemIndex, itemData ->
                Column {
                   Row() {
                       Text(text = (itemIndex + 1).toString())
                       Text(text = itemData.title)
                   }
                    Divider()
                }
            }
        }
    }
}
