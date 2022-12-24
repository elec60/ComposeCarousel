package com.hashem.mousavi.composecarousel.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hashem.mousavi.composecarousel.MyData
import kotlin.math.abs

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Carousel(
    list: List<MyData>,
    modifier: Modifier,
    itemsPerPage: Int,
    minScale:Float = 0.2f
) {
    var width by remember {
        mutableStateOf(1)
    }
    val itemWidthInDp =
        with(LocalDensity.current) { (width / itemsPerPage).toDp() }

    val lazyListState = rememberLazyListState()

    LazyRow(
        modifier = modifier.onSizeChanged {
            width = it.width
        },
        state = lazyListState,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(list) { index: Int, item: MyData ->

            var scale by remember {
                mutableStateOf(1f)
            }

            val paddingStart = if (index == 0) {
                with(LocalDensity.current) { (width / 2f).toDp() } - itemWidthInDp / 2
            } else {
               0.dp
            }

            val paddingEnd = if (index == list.size - 1) {
                with(LocalDensity.current) { (width / 2f).toDp() } - itemWidthInDp / 2
            } else {
                0.dp
            }

            CarouselItem(
                modifier = Modifier
                    .padding(start = paddingStart, end = paddingEnd)
                    .requiredWidth(itemWidthInDp)
                    .aspectRatio(1f),
                myData = item,
                scale = scale,
                boundInParentCallback = { boundsInParent: Rect ->
                    val x = boundsInParent.left + boundsInParent.width / 2f
                    scale = 2 * (minScale - 1)/width * abs(x - width / 2) + 1
                }
            )
        }
    }
}

@Composable
fun CarouselItem(
    modifier: Modifier,
    myData: MyData,
    scale: Float,
    boundInParentCallback: (Rect) -> Unit
) {
    Box(
        modifier = modifier
            .graphicsLayer {
                this.scaleX = scale
                this.scaleY = scale
            }
            .background(color = MaterialTheme.colors.background, shape = CircleShape)
            .onGloballyPositioned {
                boundInParentCallback(it.boundsInParent())
            },
        contentAlignment = Alignment.Center
    ) {
        Text(text = myData.text, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}