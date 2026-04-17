package cn.heartbath.mambo_lear_hub.manbolearhub.ui.forumdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cn.heartbath.mambo_lear_hub.manbolearhub.constants.CommonColors
import cn.heartbath.mambo_lear_hub.manbolearhub.model.forumdetail.ForumDetailUiModel
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.forumdetail.ForumDetailState

@Composable
internal fun ForumDetailContent(
    state: ForumDetailState,
    onTabSelected: (Int) -> Unit,
modifier: Modifier = Modifier
) {
    val ui = state.forumDetailUIModel
    val tabs = ui.header.tabList.map { it.title }.ifEmpty { listOf("最新", "精华", "热门") }
    val safeSelected = ui.selectedTabIndex.coerceIn(0, tabs.lastIndex.coerceAtLeast(0))
    val list = ui.threadList

    Column(modifier = modifier.fillMaxSize()) {
        // ----- Tabs -----
        if (tabs.isNotEmpty()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
                    tabs.forEachIndexed { index, tab ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(end = 28.dp)
                                .clickable { onTabSelected(index) }
                        ) {
                            Text(
                                text = tab,
                                color = if (index == safeSelected) {
                                    CommonColors.LeftTextSelected
                                } else {
                                    CommonColors.LeftTextNormal
                                },
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = if (index == safeSelected) FontWeight.Bold else FontWeight.SemiBold
                                )
                            )
                            Box(
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .width(52.dp)
                                    .height(3.dp)
                                    .background(
                                        if (index == safeSelected) CommonColors.LeftIndicator else Color.Transparent
                                    )
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(CommonColors.DividerSoft)
                )
            }
        }

        // ----- List -----
        when {
            state.isLoading && list.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.isError && list.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.errorMessage ?: "加载失败",
                        color = CommonColors.Meta
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 96.dp)
                ) {
                    items(
                        items = list,
                        key = { it.threadId ?: it.threadUrl }
                    ) { item ->
                        ForumThreadItemInternal(item = item)
                    }
                }
            }
        }
    }
}

@Composable
private fun ForumThreadItemInternal(
    item: ForumDetailUiModel.ForumThreadItem
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(CommonColors.IconHint)
            )

            Text(
                text = item.authorName ?: "用户",
                color = CommonColors.Meta,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(start = 10.dp)
            )

            Box(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(CommonColors.PrimaryBlueLightBg)
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "精华",
                    color = CommonColors.PrimaryBlue,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        }

        Text(
            text = item.title,
            color = CommonColors.Title,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 10.dp)
        )

        Row(
            modifier = Modifier.padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.publishTimeText ?: "2小时前",
                color = CommonColors.Meta,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "   ↥ ${item.replyCount ?: 48}",
                color = CommonColors.Meta,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "   ◌ ${item.viewCount ?: 1200}",
                color = CommonColors.Meta,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}