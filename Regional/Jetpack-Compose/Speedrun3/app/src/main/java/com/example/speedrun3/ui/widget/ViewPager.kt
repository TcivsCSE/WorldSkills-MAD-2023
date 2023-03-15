package com.example.speedrun3.ui.widget

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.*
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
        lastDelta: MutableState<Float>,
        currentIndex: MutableState<Int>
    ) {
        launch {
            scrollableState.stopScroll(MutatePriority.PreventUserInput)
            val visibleItems = lazyListState.layoutInfo.visibleItemsInfo
            if (lastDelta.value < -10) {
                currentIndex.value = visibleItems.last().index
                lazyListState.animateScrollToItem(visibleItems.last().index, -100)
            } else if (lastDelta.value > 10) {
                currentIndex.value = visibleItems.first().index
                lazyListState.animateScrollToItem(visibleItems.first().index, -100)
            }
        }
    }

    @Composable
    fun Build(viewPagerItem: ArrayList<ViewPagerItemData>, modifier: Modifier = Modifier) {
        val lazyListState = rememberLazyListState()
        val currentIndex = remember {
            mutableStateOf(0)
        }
        val localConfiguration = LocalConfiguration.current
        val coroutineScope = rememberCoroutineScope()
        val lastDelta = remember {
            mutableStateOf(0.0f)
        }
        val scrollableState = rememberScrollableState(
            consumeScrollDelta = { delta ->
                lastDelta.value = delta
                delta
            }
        )
        LazyRow(
            userScrollEnabled = false,
            state = lazyListState,
            modifier = modifier
                .width(((320 * viewPagerItem.size) + (localConfiguration.screenWidthDp - 320)).dp)
                .scrollable(scrollableState, orientation = Orientation.Horizontal)
        ) {
            if (scrollableState.isScrollInProgress) {
                coroutineScope.handleSwipe(
                    scrollableState,
                    lazyListState,
                    lastDelta,
                    currentIndex
                )
            }
            itemsIndexed(viewPagerItem) { itemIndex, itemData ->
                Card(
                    backgroundColor = Color.Black,
                    modifier = Modifier
                        .width(
                            if (itemIndex == 0 || itemIndex == viewPagerItem.size - 1) (((localConfiguration.screenWidthDp - 320) / 2) + 320).dp else 320.dp
                        )
                        .padding(
                            start = if (itemIndex == 0) ((localConfiguration.screenWidthDp - 320) / 2).dp else 0.dp,
                            end = if (itemIndex == viewPagerItem.size - 1) ((localConfiguration.screenWidthDp - 320) / 2).dp else 0.dp
                        )
                        .scale(
                            animateFloatAsState(
                                targetValue = if (currentIndex.value == itemIndex) 1.0f else 0.9f
                            ).value
                        )
                        .clickable {
                            coroutineScope.launch {
                                currentIndex.value = itemIndex
                                lazyListState.animateScrollToItem(itemIndex, -100)
                            }
                        }
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