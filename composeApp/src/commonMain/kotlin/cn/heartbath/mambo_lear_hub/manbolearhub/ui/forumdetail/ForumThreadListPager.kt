package cn.heartbath.mambo_lear_hub.manbolearhub.ui.forumdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Reply
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cn.heartbath.mambo_lear_hub.manbolearhub.constants.CommonColors
import cn.heartbath.mambo_lear_hub.manbolearhub.model.forumdetail.ForumDetailUiModel
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.forumdetail.ForumDetailState
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
internal fun ForumThreadListPager(
    state: ForumDetailState,
    pagerState: PagerState,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier
) {
    val forumDetailUiModel = state.forumDetailUIModel

    when {
        state.isThreadsLoading && forumDetailUiModel.threadList.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = CommonColors.PrimaryBlue)
            }
        }

        state.isError && forumDetailUiModel.threadList.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.errorMessage ?: "加载失败",
                    color = CommonColors.Meta,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        else -> {
            HorizontalPager(
                state = pagerState,
                modifier = modifier.fillMaxSize()
            ) { pageIndex ->
                val forumThreadListForPage = forumDetailUiModel.threadDataMap[pageIndex]
                    ?: if (pageIndex == forumDetailUiModel.selectedTabIndex) forumDetailUiModel.threadList else emptyList()

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        items = forumThreadListForPage,
                        key = { forumThreadItem -> "${pageIndex}_${forumThreadItem.threadId ?: forumThreadItem.threadUrl}" }
                    ) { forumThreadItem ->
                        ForumThreadItem(
                            forumThreadItem = forumThreadItem,
                            imageLoader = imageLoader
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ForumThreadItem(
    forumThreadItem: ForumDetailUiModel.ForumThreadItem,
    imageLoader: ImageLoader
) {
    val forumThreadCoverImageUrl = forumThreadItem.imageUrls.firstOrNull()
    val platformContext = LocalPlatformContext.current
    val forumThreadCardShape = RoundedCornerShape(16.dp)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, forumThreadCardShape, clip = false)
            .clip(forumThreadCardShape)
            .background(CommonColors.BackgroundWhite)
            .border(
                width = 1.dp,
                color = CommonColors.DividerSoft.copy(alpha = 0.65f),
                shape = forumThreadCardShape
            )
            .clickable { }
            .semantics(mergeDescendants = true) {}
            .padding(horizontal = 12.dp, vertical = 11.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = ImageRequest.Builder(platformContext)
                    .data(forumThreadItem.authorAvatarUrl)
                    .crossfade(false)
                    .build(),
                imageLoader = imageLoader,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(CommonColors.Placeholder),
                contentScale = ContentScale.Crop
            )

            Text(
                text = forumThreadItem.authorName ?: "用户",
                color = CommonColors.Meta,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(72.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = forumThreadItem.title,
                    color = CommonColors.Title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    maxLines = if (forumThreadCoverImageUrl == null) 2 else 3,
                    overflow = TextOverflow.Ellipsis
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = forumThreadItem.publishTimeText ?: "",
                        color = CommonColors.Meta,
                        style = MaterialTheme.typography.labelMedium
                    )

                    Row(
                        modifier = Modifier.padding(start = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.RemoveRedEye,
                            contentDescription = null,
                            tint = CommonColors.Meta,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = " ${forumThreadItem.viewCount ?: 0}",
                            color = CommonColors.Meta,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }

                    Row(
                        modifier = Modifier.padding(start = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Reply,
                            contentDescription = null,
                            tint = CommonColors.Meta,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = " ${forumThreadItem.replyCount ?: 0}",
                            color = CommonColors.Meta,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }

            if (forumThreadCoverImageUrl != null) {
                Spacer(modifier = Modifier.width(10.dp))
                AsyncImage(
                    model = ImageRequest.Builder(platformContext)
                        .data(forumThreadCoverImageUrl)
                        .crossfade(false)
                        .build(),
                    imageLoader = imageLoader,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = 100.dp, height = 78.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(CommonColors.Placeholder)
                )
            }
        }
    }
}