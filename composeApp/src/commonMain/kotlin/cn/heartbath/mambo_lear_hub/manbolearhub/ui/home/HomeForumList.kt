package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectableGroup
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull

@Composable
internal fun HomeForumList(
    forumCategories: List<HomeUIModel.ForumCategory>,
    selectedForumCategory: Int,
    onSelectedForumCategory: (Int) -> Unit,
    navigateToForumDetail: (Int) -> Unit,
    onScrollDirectionChanged: (Boolean) -> Unit = {},
) {
    val rightListState = rememberLazyListState()

    var lastIndex by remember { mutableIntStateOf(0) }
    var lastOffset by remember { mutableIntStateOf(0) }
    var acc by remember { mutableIntStateOf(0) }

    LaunchedEffect(rightListState, selectedForumCategory) {
        snapshotFlow {
            Triple(
                rightListState.firstVisibleItemIndex,
                rightListState.firstVisibleItemScrollOffset,
                rightListState.isScrollInProgress
            )
        }
            .mapNotNull { (index, offset, scrolling) ->
                val delta = when {
                    index > lastIndex -> HomeCommonConfig.FORUM_HIDE_TRIGGER_PX
                    index < lastIndex -> -HomeCommonConfig.FORUM_HIDE_TRIGGER_PX
                    else -> offset - lastOffset
                }

                lastIndex = index
                lastOffset = offset

                // 顶部附近固定显示
                if (index == 0 && offset < HomeCommonConfig.FORUM_TOP_FORCE_SHOW_OFFSET) {
                    acc = 0
                    return@mapNotNull false
                }

                // 非滚动中不触发，防回弹抖动
                if (!scrolling || delta == 0) return@mapNotNull null

                acc += delta
                when {
                    acc >= HomeCommonConfig.FORUM_HIDE_TRIGGER_PX -> {
                        acc = 0
                        true
                    }
                    acc <= -HomeCommonConfig.FORUM_SHOW_TRIGGER_PX -> {
                        acc = 0
                        false
                    }
                    else -> null
                }
            }
            .distinctUntilChanged()
            .collectLatest(onScrollDirectionChanged)
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(CommonColors.BackgroundWhite)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(0.30f)
                .fillMaxHeight()
                .selectableGroup()
        ) {
            itemsIndexed(
                items = forumCategories,
                key = { index, category -> "${category.categoryName}_$index" }
            ) { index, category ->
                HomeForumCategoryItem(
                    categoryName = category.categoryName,
                    selected = selectedForumCategory == index,
                    onClick = { onSelectedForumCategory(index) }
                )
            }
        }

        LazyColumn(
            state = rightListState,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(CommonColors.HomePageBg),
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = forumCategories.getOrNull(selectedForumCategory)?.forumList.orEmpty(),
                key = { forum -> "${forum.forumId}_${forum.forumName}" }
            ) { forum ->
                HomeForumItem(
                    forum = forum,
                    onClick = { navigateToForumDetail(forum.forumId.toIntOrNull() ?: 0) }
                )
            }
        }
    }
}