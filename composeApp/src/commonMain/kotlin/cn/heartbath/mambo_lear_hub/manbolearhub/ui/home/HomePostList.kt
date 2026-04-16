package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cn.heartbath.mambo_lear_hub.manbolearhub.constants.CommonColors
import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel
import coil3.ImageLoader
import coil3.compose.LocalPlatformContext
import coil3.svg.SvgDecoder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull

@Composable
internal fun HomePostList(
    postList: List<HomeUIModel.PostItem>,
    onScrollDirectionChanged: (Boolean) -> Unit = {}
) {
    val platformContext = LocalPlatformContext.current
    val imageLoader = remember {
        ImageLoader.Builder(platformContext)
            .components { add(SvgDecoder.Factory()) }
            .build()
    }

    val listState = rememberLazyListState()
    var lastIndex by remember { mutableIntStateOf(0) }
    var lastOffset by remember { mutableIntStateOf(0) }
    var acc by remember { mutableIntStateOf(0) }

    LaunchedEffect(listState) {
        snapshotFlow {
            Triple(
                listState.firstVisibleItemIndex,
                listState.firstVisibleItemScrollOffset,
                listState.isScrollInProgress
            )
        }.mapNotNull { (index, offset, scrolling) ->
            val delta = when {
                index > lastIndex -> HomeCommonConfig.POST_HIDE_TRIGGER_PX
                index < lastIndex -> -HomeCommonConfig.POST_HIDE_TRIGGER_PX
                else -> offset - lastOffset
            }

            lastIndex = index
            lastOffset = offset

            if (index == 0 && offset < HomeCommonConfig.POST_TOP_FORCE_SHOW_OFFSET) {
                acc = 0
                return@mapNotNull false
            }
            if (!scrolling || delta == 0) return@mapNotNull null

            acc += delta
            when {
                acc >= HomeCommonConfig.POST_HIDE_TRIGGER_PX -> {
                    acc = 0
                    true
                }
                acc <= -HomeCommonConfig.POST_SHOW_TRIGGER_PX -> {
                    acc = 0
                    false
                }
                else -> null
            }
        }
            .distinctUntilChanged()
            .collectLatest(onScrollDirectionChanged)
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .background(CommonColors.HomePageBg),
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(
            items = postList,
            key = { index, item -> item.stableKey(index) }
        ) { _, item ->
            HomePostItem(item = item, imageLoader = imageLoader)
        }
    }
}

private fun HomeUIModel.PostItem.stableKey(index: Int): String {
    detailUrl?.takeIf { it.isNotBlank() }?.let { return "detail:$it" }
    return buildString {
        append(title.orEmpty())
        append('|')
        append(username.orEmpty())
        append('|')
        append(postTime.orEmpty())
        append('|')
        append(forumName.orEmpty())
    }.ifBlank { "index:$index" }
}