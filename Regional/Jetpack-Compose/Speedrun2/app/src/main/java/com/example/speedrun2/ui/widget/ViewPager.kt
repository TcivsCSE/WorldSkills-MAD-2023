package com.example.speedrun2.ui.widget

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class ViewPagerItemData(
    val imageSource: Int
)

object ViewPager {
    private fun CoroutineScope.handleSwipe(
        lazyListState: LazyListState,
        scrollableState: ScrollableState,
        lastScrollDelta: MutableState<Float>,
        currentIndex: MutableState<Int>
    ) {
        launch {
            scrollableState.stopScroll(MutatePriority.PreventUserInput)
            val visibleItems = lazyListState.layoutInfo.visibleItemsInfo
            if (lastScrollDelta.value > 10) {
                currentIndex.value = visibleItems.first().index
                lazyListState.animateScrollToItem(visibleItems.first().index, -100)
            } else if (lastScrollDelta.value < -10) {
                currentIndex.value = visibleItems.last().index
                lazyListState.animateScrollToItem(visibleItems.last().index, -100)
            }
        }
    }

    @Composable
    fun Build(viewPagerItems: ArrayList<ViewPagerItemData>, modifier: Modifier = Modifier) {
        val coroutineScope = rememberCoroutineScope()
        val lazyListState = rememberLazyListState()
        val localConfiguration = LocalConfiguration.current
        val currentIndex = remember {
            mutableStateOf(0)
        }
        val lastScrollDelta = remember {
            mutableStateOf(0.0f)
        }
        val scrollableState = rememberScrollableState(
            consumeScrollDelta = {
                lastScrollDelta.value = it
                it
            }
        )
        LazyRow(
            state = lazyListState,
            userScrollEnabled = false,
            modifier = modifier
                .scrollable(
                    scrollableState,
                    orientation = Orientation.Horizontal
                )
                .height(260.dp)
                .width(((320 * viewPagerItems.size) + localConfiguration.screenWidthDp).dp)
        ) {
            if (scrollableState.isScrollInProgress) {
                coroutineScope.handleSwipe(
                    lazyListState,
                    scrollableState,
                    lastScrollDelta,
                    currentIndex
                )
            }
            itemsIndexed(viewPagerItems) { itemIndex, itemData ->
                Card(
                    backgroundColor = Color.Black,
                    modifier = Modifier
                        .clickable {
                            currentIndex.value = itemIndex
                            coroutineScope.launch {
                                lazyListState.animateScrollToItem(currentIndex.value, -100)
                            }
                        }
                        .width(
                            if (itemIndex == 0 || itemIndex == viewPagerItems.size - 1) (320 + ((localConfiguration.screenWidthDp - 320) / 2)).dp else 320.dp
                        )
                        .scale(
                            animateFloatAsState(
                                targetValue = if (currentIndex.value == itemIndex) 1.0f else 0.9f
                            ).value
                        )
                        .padding(
                            start = if (itemIndex == 0) ((localConfiguration.screenWidthDp - 320) / 2).dp else 0.dp,
                            end = if (itemIndex == viewPagerItems.size - 1) ((localConfiguration.screenWidthDp - 320) / 2).dp else 0.dp
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