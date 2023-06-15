package dev.nightfeather.basic_build.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import dev.nightfeather.basic_build.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object JobPage {
    @Composable
    fun Build(navController: NavController) {
        val imageIds = arrayListOf(
            R.drawable.job_img_0,
            R.drawable.job_img_1,
            R.drawable.job_img_2,
            R.drawable.job_img_3,
            R.drawable.job_img_4,
        )

        val showDialog = remember {
            mutableStateOf(false)
        }
        val lazyListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        Scaffold(
            topBar = {
                Row() {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                    Spacer(Modifier.fillMaxWidth())
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                LazyColumn(
                ) {
                    itemsIndexed(imageIds) { idx, imageId ->
                        Image(
                            painter = painterResource(id = imageId),
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    coroutineScope.launch {
                                        lazyListState.scrollToItem(idx)
                                    }
                                    showDialog.value = true
                                }
                        )
                        Text(
                            text = """
                            ajdfskhiujoafklngajireldsfdfsadfwertdagsdsfad
                            fsadfwertdagsdsfadfsadfwertdagsdsfa
                            sadfsadfwertdagsdsfadfsadfwertdagsdsfadfsadfwertdagsdsfadfs
                            adfwertdagsdsfadfsadfwertdagsdsfadfsadfwertdagsdsf
                            adfsadfwertdagsdsfa
                        """.trimIndent()
                        )
                    }
                }
            }
            if (showDialog.value) {
                Dialog(
                    onDismissRequest = {
                        showDialog.value = false
                    },
                    properties = DialogProperties(usePlatformDefaultWidth = false)
                ) {
                    BuildImageDialog(imageIds, lazyListState, coroutineScope)
                }
            }
        }
    }

    @Composable
    private fun BuildImageDialog(imageIds: ArrayList<Int>, lazyListState: LazyListState, coroutineScope: CoroutineScope) {
        Box(
        ) {
            LazyRow(
                state = lazyListState,
                userScrollEnabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                items(imageIds) { imageId ->
                    Image(
                        painter = painterResource(id = imageId),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem((lazyListState.firstVisibleItemIndex - 1).mod(lazyListState.layoutInfo.totalItemsCount))
                        }
                    },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black.copy(alpha = 0.35f),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .height(220.dp)
                        .width(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .scale(1.8f)
                    )
                }
                Button(
                    onClick = {
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem((lazyListState.firstVisibleItemIndex + 1).mod(lazyListState.layoutInfo.totalItemsCount))
                        }
                    },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black.copy(alpha = 0.35f),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .height(220.dp)
                        .width(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier
                            .scale(1.8f)
                    )
                }
            }
        }
    }
}