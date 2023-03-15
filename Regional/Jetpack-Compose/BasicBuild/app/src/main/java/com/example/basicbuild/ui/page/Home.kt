package com.example.basicbuild.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.basicbuild.R
import com.example.basicbuild.ui.component.ViewPager
import com.example.basicbuild.ui.component.ViewPagerItemData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class ExhibitNewsData(
    val title: String,
    val date: String,
    val author: String
)

object HomePage {
    @Composable
    fun Build(navController: NavController) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val coroutineScope = rememberCoroutineScope()
        ModalDrawer(
            drawerState = drawerState,
            drawerContent = {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(60.dp)
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    if (drawerState.isOpen) drawerState.close() else drawerState.open()
                                }
                            },
                            modifier = Modifier
                        ) {
                            Icon(
                                if (drawerState.isOpen) Icons.Default.Close else Icons.Filled.Menu,
                                null,
                                tint = Color.Black
                            )
                        }
                        Image(
                            painter = painterResource(id = R.drawable.tainex_ico),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(end = 30.dp)
                                .scale(1.8f)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .padding(horizontal = 16.dp)
                            .clickable {
                                navController.navigate("exhibitHallInfo")
                            }
                    ) {
                       Image(
                           painter = painterResource(id = R.drawable.drawer_button_placeholder),
                           contentDescription = null,
                           contentScale = ContentScale.Crop,
                           colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.5f), blendMode = BlendMode.Overlay),
                           modifier = Modifier
                               .fillMaxSize()
                               .clip(RoundedCornerShape(16.dp))
                       )
                        Text(
                            text = "展館介紹",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 3.sp,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(15.dp)
                        )
                        Icon(
                            Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(42.dp)
                                .padding(end = 15.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .padding(horizontal = 16.dp)
                            .clickable {
                                navController.navigate("buyTicket")
                            }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.drawer_button_placeholder),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.5f), blendMode = BlendMode.Overlay),
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp))
                        )
                        Text(
                            text = "前往購票",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 3.sp,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(15.dp)
                        )
                        Icon(
                            Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(42.dp)
                                .padding(end = 15.dp)
                        )
                    }
                }
            }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppBar(drawerState, coroutineScope)
                Text(
                    text = "展館活動",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    letterSpacing = 3.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp)
                        .padding(top = 0.dp, bottom = 5.dp)
                )
                ViewPager.Build(items = arrayListOf(
                    ViewPagerItemData(
                        painterResource(id = R.drawable.event_image_placeholder)
                    ) {
                        println("Item 1")
                    },
                    ViewPagerItemData(
                        painterResource(id = R.drawable.event_image_placeholder)
                    ) {
                        println("Item 2")
                    },
                    ViewPagerItemData(
                        painterResource(id = R.drawable.event_image_placeholder)
                    ) {
                        println("Item 3")
                    },
                    ViewPagerItemData(
                        painterResource(id = R.drawable.event_image_placeholder)
                    ) {
                        println("Item 4")
                    }
                ))
                Text(
                    text = "最新消息",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    letterSpacing = 3.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp)
                        .padding(top = 20.dp, bottom = 5.dp)
                )
                ExhibitNews(news = arrayListOf(
                    ExhibitNewsData(
                        title = "TEST",
                        date = "2023/01/03",
                        author = "user"
                    ),
                    ExhibitNewsData(
                        title = "TEST",
                        date = "2023/01/03",
                        author = "user"
                    ),
                    ExhibitNewsData(
                        title = "TEST",
                        date = "2023/01/03",
                        author = "user"
                    ),
                ))
                ExhibitHallInfo(navController)
            }
        }
    }

    @Composable
    private fun AppBar(drawerState: DrawerState, coroutineScope: CoroutineScope) {
        TopAppBar(
            title = { Text(
                text = "南港展覽館導覽",
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp,
                fontSize = 22.sp
            ) },
            navigationIcon = {
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            if (drawerState.isOpen) drawerState.close() else drawerState.open()
                        }
                    }
                ) {
                    Icon(
                        if (drawerState.isOpen) Icons.Default.Close else Icons.Filled.Menu,
                        null,
                        tint = Color.Black
                    )
                }
            },
            backgroundColor = Color.White,
            elevation = 0.dp
        )
    }

    @Composable
    private fun ExhibitNews(news: ArrayList<ExhibitNewsData>) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(horizontal = 20.dp)
        ) {
            itemsIndexed(news) { newsIndex, newsData ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .height(50.dp)
                ) {
                    Text(
                        text = "${newsIndex + 1}.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        textAlign = TextAlign.End,
                        letterSpacing = (-1).sp,
                        modifier = Modifier
                            .width(30.dp)
                    )
                    Text(
                        text = newsData.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.5.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(start = 5.dp)
                    )
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .weight(weight = 2.0f, fill = true)
                    ) {
                        Text(
                            text = newsData.author,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            letterSpacing = 0.5.sp,
                        )
                        Text(
                            text = newsData.date,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            letterSpacing = 0.5.sp,
                        )
                    }
                }
                Divider()
            }
        }
    }

    @Composable
    private fun ExhibitHallInfo(navController: NavController) {
        Box(
            modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp)
                .padding(horizontal = 20.dp)
                .clickable {
                    navController.navigate("exhibitHallInfo")
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.exhibit_info_placeholder),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.5f), blendMode = BlendMode.Overlay),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
            )
            Text(
                text = "展館介紹",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(15.dp)
            )
        }
    }
}