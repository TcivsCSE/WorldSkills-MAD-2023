package com.example.speedrun1.ui.widget

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.speedrun1.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class ViewPagerItemData(
    val imageId: Int
)

object ViewPager {
    private fun CoroutineScope.handleSwipe(
        scrollableState: ScrollableState,
        lazyRowState: LazyListState,
        scrollDelta: Float,
        currentIndex: MutableState<Int>
    ) {
        launch {
            scrollableState.stopScroll(MutatePriority.UserInput)
            val visibleItems = lazyRowState.layoutInfo.visibleItemsInfo
            if (scrollDelta <= -10) {
                currentIndex.value = visibleItems.last().index
            } else if (scrollDelta >= 10) {
                currentIndex.value = visibleItems.first().index
            }
            lazyRowState.animateScrollToItem(currentIndex.value, -130)
        }
    }

    @Composable
    fun Build(viewPagerItems: ArrayList<ViewPagerItemData>, modifier: Modifier = Modifier) {
        val lazyRowState = rememberLazyListState()
        val scrollDelta = remember {
            mutableStateOf(0.0f)
        }
        val scrollableState = rememberScrollableState(consumeScrollDelta = {
            scrollDelta.value = it
            it
        })
        val coroutineScope = rememberCoroutineScope()
        val currentIndex = remember {
            mutableStateOf(0)
        }
        LazyRow(
            state = lazyRowState,
            userScrollEnabled = false,
            contentPadding = PaddingValues(horizontal = 0.dp),
            modifier = modifier
                .scrollable(
                    scrollableState,
                    orientation = Orientation.Horizontal
                )
                .width((360 * viewPagerItems.size + (LocalConfiguration.current.screenWidthDp - 300)).dp)
        ) {
            if (scrollableState.isScrollInProgress) {
                coroutineScope.handleSwipe(
                    scrollableState = scrollableState,
                    lazyRowState = lazyRowState,
                    scrollDelta = scrollDelta.value,
                    currentIndex = currentIndex
                )
            }
            itemsIndexed(viewPagerItems) { itemIndex, itemData ->
                Card(
                    backgroundColor = Color.Black,
                    modifier = Modifier
                        .height(
                            220.dp
                        )
                        .scale(
                            animateFloatAsState(
                                targetValue = if (currentIndex.value == itemIndex) 1.0f else 0.85f
                            ).value
                        )
                        .clickable {
                            currentIndex.value = itemIndex
                            coroutineScope.launch {
                                lazyRowState.animateScrollToItem(currentIndex.value, -130)
                            }
                        }
                        .width(
                            if (itemIndex == 0 || itemIndex == viewPagerItems.size - 1) (300 + ((LocalConfiguration.current.screenWidthDp - 300) / 2)).dp else 300.dp
                        )
                        .padding(
                            start = if (itemIndex == 0) ((LocalConfiguration.current.screenWidthDp - 300) / 2).dp else 0.dp,
                            end = if (itemIndex == viewPagerItems.size - 1) ((LocalConfiguration.current.screenWidthDp - 300) / 2).dp else 0.dp
                        )
                ) {
                    Image(
                        painter = painterResource(id = itemData.imageId),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                    )
                }
            }
        }
    }
}