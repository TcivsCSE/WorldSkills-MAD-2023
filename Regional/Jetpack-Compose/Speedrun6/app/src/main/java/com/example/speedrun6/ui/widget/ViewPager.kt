package com.example.speedrun6.ui.widget

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class ViewPagerItemData(
    val imageSource: Int
)

object ViewPager {
    private fun CoroutineScope.handleSwipe(
        scrollableState: ScrollableState,
        lazyListState: LazyListState,
        scrollDelta: MutableState<Float>,
        currentIndex: MutableState<Int>
    ) {
        launch {
            scrollableState.stopScroll(MutatePriority.PreventUserInput)
            val visibleItems = lazyListState.layoutInfo.visibleItemsInfo
            if (scrollDelta.value < -10) {
                currentIndex.value = visibleItems.last().index
                lazyListState.animateScrollToItem(visibleItems.last().index, -100)
            } else if (scrollDelta.value > 10) {
                currentIndex.value = visibleItems.first().index
                lazyListState.animateScrollToItem(visibleItems.first().index, -100)
            }
        }
    }

    @Composable
    fun Build(vpItems: ArrayList<ViewPagerItemData>, modifier: Modifier = Modifier) {
        val lazyListState = rememberLazyListState()
        val scrollDelta = remember {
            mutableStateOf(0.0f)
        }
        val scrollableState = rememberScrollableState(
            consumeScrollDelta = {
                scrollDelta.value = it
                it
            }
        )
        val currentIndex = remember {
            mutableStateOf(0)
        }
        val coroutineScope = rememberCoroutineScope()
        LazyRow(
            userScrollEnabled = false,
            state = lazyListState,
            modifier = modifier
                .scrollable(
                    scrollableState,
                    orientation = Orientation.Horizontal
                )
        ) {
            if (scrollableState.isScrollInProgress) {
                coroutineScope.handleSwipe(
                    scrollableState,
                    lazyListState,
                    scrollDelta,
                    currentIndex
                )
            }
            itemsIndexed(vpItems) { itemIndex, itemData ->
                Card(
                    backgroundColor = Color.Black,
                    modifier = Modifier
                        .height(200.dp)
                        .width(
                            if (itemIndex == 0 || itemIndex == vpItems.size - 1) (((LocalConfiguration.current.screenWidthDp - 320) / 2) + 320).dp else 320.dp
                        )
                        .padding(
                            start = if (itemIndex == 0) ((LocalConfiguration.current.screenWidthDp - 320) / 2).dp else 0.dp,
                            end = if (itemIndex == vpItems.size - 1) ((LocalConfiguration.current.screenWidthDp - 320) / 2).dp else 0.dp
                        )
                        .clickable {
                            coroutineScope.launch {
                                currentIndex.value = itemIndex
                                lazyListState.animateScrollToItem(itemIndex, -100)
                            }
                        }
                        .scale(
                            animateFloatAsState(
                                targetValue = if (itemIndex == currentIndex.value) 1.0f else 0.9f
                            ).value
                        )
                ) {
                    Image(
                        painter = painterResource(id = itemData.imageSource),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}