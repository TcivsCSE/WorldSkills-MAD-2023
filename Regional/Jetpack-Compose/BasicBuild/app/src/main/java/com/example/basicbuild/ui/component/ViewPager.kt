package com.example.basicbuild.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.MutatePriority.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class ViewPagerItemData (
    val image: Painter,
    val onClick: () -> Unit
)

object ViewPager {
    private fun CoroutineScope.handleSwipe(scrollableState: ScrollableState, lazyListState: LazyListState, currentIndex: MutableState<Int>, lastDelta: Float, screenEdgeOffset: Int, itemsSize: Int) {
        launch {
            scrollableState.stopScroll(PreventUserInput)
            val itemsInfo = lazyListState.layoutInfo.visibleItemsInfo
            if (lastDelta < -10) {
                currentIndex.value = itemsInfo.last().index
                lazyListState.animateScrollToItem(itemsInfo.last().index, -100)
            } else if (lastDelta > 10) {
                currentIndex.value = itemsInfo.first().index
                lazyListState.animateScrollToItem(itemsInfo.first().index, -100)
            }
        }
    }

    @Composable
    fun Build(items: ArrayList<ViewPagerItemData>) {
        val lazyListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        val currentIndex = remember {
            mutableStateOf(0)
        }
        val lastDelta = remember {
            mutableStateOf(0.0f)
        }
        val scrollableState = rememberScrollableState {v -> lastDelta.value = v; v}
        val screenEdgeOffset = ((LocalConfiguration.current.screenWidthDp - 320) / 2)
        LaunchedEffect(Unit) {
            while(true) {
                delay(3500)
                scrollableState.stopScroll(UserInput)
                currentIndex.value = (currentIndex.value + 1) % items.size
                lazyListState.animateScrollToItem(currentIndex.value, -100)
            }
        }
        Box {
            LazyRow(
                state = lazyListState,
                contentPadding = PaddingValues(0.dp),
                userScrollEnabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .scrollable(
                        scrollableState,
                        Orientation.Horizontal
                    )
                    .padding(vertical = 5.dp)
            ) {
                itemsIndexed(items) { itemIndex, itemData ->
                    if (scrollableState.isScrollInProgress) {
                        coroutineScope.handleSwipe(
                            scrollableState,
                            lazyListState,
                            currentIndex,
                            lastDelta.value,
                            screenEdgeOffset,
                            items.size
                        )
                    }
                    Box(
                        modifier = Modifier
                            .scale(
                                animateFloatAsState(
                                    targetValue = if (currentIndex.value == itemIndex) 1.05f else 0.95f,
                                    animationSpec = tween(
                                        durationMillis = 200,
                                        delayMillis = 50,
                                        easing = Ease
                                    )
                                ).value
                            )
                            .height(180.dp)
                            .width(
                                if (itemIndex == 0 || itemIndex == items.size - 1) (screenEdgeOffset + 320).dp
                                else 320.dp
                            )
                            .padding(10.dp)
                            .padding(
                                start = if (itemIndex == 0) screenEdgeOffset.dp else 0.dp,
                                end = if (itemIndex == items.size - 1) screenEdgeOffset.dp else 0.dp
                            )

                    ) {
                        Image(
                            painter = itemData.image,
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                            modifier = Modifier
                                .shadow(10.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    coroutineScope.launch {
                                        currentIndex.value = itemIndex
                                        lazyListState.animateScrollToItem(itemIndex, -100)
                                    }
                                }
                        )
                        Button(
                            onClick = itemData.onClick,
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                            shape = RoundedCornerShape(5.dp),
                            contentPadding = PaddingValues(
                                horizontal = 5.dp,
                                vertical = 0.dp
                            ),
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(10.dp)
                                .shadow(10.dp)
                                .size(80.dp, 30.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "查看更多",
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp
                                )
                                Icon(
                                    Icons.Default.ArrowForward,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp, 16.dp)
                                )
                            }
                        }
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .width(((items.size * 7) + ((items.size - 1) * 4)).dp)
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
            ){
                for (index in 0 until items.size) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .background(
                                color = animateColorAsState(
                                    targetValue = if (currentIndex.value == index) Color.DarkGray else Color.Gray.copy(
                                        alpha = 0.5f
                                    ),
                                    animationSpec = tween(
                                        durationMillis = 180,
                                        delayMillis = 30,
                                        easing = Ease
                                    )
                                ).value,
                                shape = CircleShape
                            )
                            .shadow(
                                animateDpAsState(
                                    targetValue = if (currentIndex.value == index) 12.dp else 3.dp,
                                    animationSpec = tween(
                                        durationMillis = 180,
                                        delayMillis = 30,
                                        easing = Ease
                                    )
                                ).value
                            )
                    )
                }
            }
        }
    }
}