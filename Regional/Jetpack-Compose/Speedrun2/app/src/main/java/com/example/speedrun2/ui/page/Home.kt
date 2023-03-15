package com.example.speedrun2.ui.page

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.Navigator
import androidx.navigation.compose.rememberNavController
import com.example.speedrun2.R
import com.example.speedrun2.ui.widget.TopNavBar
import com.example.speedrun2.ui.widget.ViewPager
import com.example.speedrun2.ui.widget.ViewPagerItemData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class NewsData(
    val title: String
)

object HomePage {
    @Composable
    fun Build(navController: NavController) {
        val coroutineScope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState(drawerState = rememberDrawerState(initialValue = DrawerValue.Closed))
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { BuildAppBar(drawerState = scaffoldState.drawerState, coroutineScope = coroutineScope) },
            drawerContent = { BuildDrawerContent(drawerState = scaffoldState.drawerState, coroutineScope = coroutineScope, navController = navController) }
        ) { sPadding ->
            Column(
                modifier = Modifier
                    .padding(sPadding)
            ) {
                ViewPager.Build(
                    viewPagerItems = arrayListOf(
                        ViewPagerItemData(imageSource = R.drawable.mol_logo),
                        ViewPagerItemData(imageSource = R.drawable.mol_logo),
                        ViewPagerItemData(imageSource = R.drawable.mol_logo),
                    ),
                    modifier = Modifier
                        .padding(top = 20.dp)
                )
                LazyColumn(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp)
                ) {
                    itemsIndexed(arrayListOf(
                        NewsData("test"),
                        NewsData("test"),
                        NewsData("test"),
                        NewsData("test"),
                        NewsData("test"),
                        NewsData("test"),
                        NewsData("test"),
                        NewsData("test"),
                    )) { itemIndex, itemData ->
                        Column {
                            Box(
                            ) {
                                Row(
                                    modifier = Modifier
                                        .height(60.dp)
                                        .align(Alignment.CenterStart)
                                ) {
                                    Text(text = (itemIndex + 1).toString())
                                    Text(text = itemData.title)
                                }
                            }
                            Divider()
                        }
                    }
                }
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
                    .padding(horizontal = 40.dp)
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
            ) {
                Text(text = "exhibitHallInfo")
            }
        }
    }
}