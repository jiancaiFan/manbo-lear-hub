@file:Suppress("DuplicatedCode")

package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.svg.SvgDecoder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull

private val PageBg = Color(0xFFF8FAFC)
private val CardBg = Color.White
private val CardBorder = Color(0xFFEFF2F6)
private val TitleColor = Color(0xFF111827)
private val SummaryColor = Color(0xFF4B5563)
private val MetaColor = Color(0xFF6B7280)
private val PlaceholderColor = Color(0xFFE5E7EB)
private val PrimaryColor = Color(0xFF2563EB)

private const val SCROLL_TRIGGER_PX = 24

@Composable
internal fun HomePostList(
    postList: List<HomeUIModel.PostItem>,
    onScrollDirectionChanged: (Boolean) -> Unit = {}
) {
    val context = LocalPlatformContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context).components { add(SvgDecoder.Factory()) }.build()
    }

    val listState = rememberLazyListState()
    var lastIndex by remember { mutableIntStateOf(0) }
    var lastOffset by remember { mutableIntStateOf(0) }
    var accumulatedDelta by remember { mutableIntStateOf(0) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
            .mapNotNull { (index, offset) ->
                val delta = when {
                    index > lastIndex -> SCROLL_TRIGGER_PX
                    index < lastIndex -> -SCROLL_TRIGGER_PX
                    else -> offset - lastOffset
                }

                lastIndex = index
                lastOffset = offset
                if (delta == 0) return@mapNotNull null

                accumulatedDelta += delta
                when {
                    accumulatedDelta >= SCROLL_TRIGGER_PX -> {
                        accumulatedDelta = 0
                        true
                    }
                    accumulatedDelta <= -SCROLL_TRIGGER_PX -> {
                        accumulatedDelta = 0
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
            .background(PageBg),
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(
            items = postList,
            key = { index, item -> item.stableKey(index) }
        ) { _, item ->
            PostItemCard(item = item, imageLoader = imageLoader)
        }
    }
}

@Composable
private fun PostItemCard(
    item: HomeUIModel.PostItem,
    imageLoader: ImageLoader
) {
    val context = LocalPlatformContext.current
    val username = item.username.orEmpty().ifBlank { "匿名用户" }
    val postTime = item.postTime.orEmpty().ifBlank { "刚刚" }
    val forumName = item.forumName.orEmpty().ifBlank { "社区" }
    val images = item.imageList.take(9)

    val a11yText = buildString {
        item.title?.takeIf { it.isNotBlank() }?.let { append(it).append("。") }
        item.summary?.takeIf { it.isNotBlank() }?.let { append(it).append("。") }
        append("作者$username。发布时��$postTime。阅读${item.readCount ?: 0}，回复${item.replyCount ?: 0}。")
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) { contentDescription = a11yText },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(0.6.dp, CardBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 9.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val avatar = item.avatarUrl.orEmpty()
                if (avatar.isNotBlank()) {
                    AsyncImage(
                        model = ImageRequest.Builder(context).data(avatar).crossfade(false).build(),
                        imageLoader = imageLoader,
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(PlaceholderColor),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(PlaceholderColor)
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = username,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = TitleColor
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = postTime,
                        style = TextStyle(fontSize = 10.sp, color = MetaColor),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(Color(0xFFEFF6FF))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = forumName,
                        style = TextStyle(
                            fontSize = 10.sp,
                            color = PrimaryColor,
                            fontWeight = FontWeight.Medium
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            if (!item.title.isNullOrBlank()) {
                Text(
                    text = item.title,
                    modifier = Modifier.padding(top = 7.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 19.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TitleColor
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (!item.summary.isNullOrBlank()) {
                Text(
                    text = item.summary,
                    modifier = Modifier.padding(top = 4.dp),
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = SummaryColor
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            when (images.size) {
                1 -> {
                    AsyncImage(
                        model = ImageRequest.Builder(context).data(images.first()).crossfade(false).build(),
                        imageLoader = imageLoader,
                        contentDescription = "帖子配图",
                        modifier = Modifier
                            .padding(top = 7.dp)
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(9.dp))
                            .background(PlaceholderColor),
                        contentScale = ContentScale.Crop
                    )
                }

                2 -> {
                    BoxWithConstraints(
                        modifier = Modifier
                            .padding(top = 7.dp)
                            .fillMaxWidth()
                    ) {
                        val spacing = 6.dp
                        val itemWidth = (maxWidth - spacing) / 2
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(spacing)
                        ) {
                            images.forEach { url ->
                                AsyncImage(
                                    model = ImageRequest.Builder(context).data(url).crossfade(false).build(),
                                    imageLoader = imageLoader,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(itemWidth)
                                        .height(itemWidth * 0.72f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(PlaceholderColor),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }

                in 3..9 -> {
                    BoxWithConstraints(
                        modifier = Modifier
                            .padding(top = 7.dp)
                            .fillMaxWidth()
                    ) {
                        val spacing = 6.dp
                        val itemSize = (maxWidth - spacing * 2) / 3
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(spacing),
                            verticalArrangement = Arrangement.spacedBy(spacing)
                        ) {
                            images.forEach { url ->
                                AsyncImage(
                                    model = ImageRequest.Builder(context).data(url).crossfade(false).build(),
                                    imageLoader = imageLoader,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(itemSize)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(PlaceholderColor),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(top = 7.dp),
                color = Color(0xFFF1F5F9),
                thickness = 1.dp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "阅读 ${item.readCount ?: 0}",
                    style = TextStyle(fontSize = 10.sp, color = MetaColor)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "回复 ${item.replyCount ?: 0}",
                    style = TextStyle(fontSize = 10.sp, color = MetaColor)
                )
            }
        }
    }
}

private fun HomeUIModel.PostItem.stableKey(index: Int): String {
    detailUrl?.takeIf { it.isNotBlank() }?.let { return "detail:$it" }
    val t = title.orEmpty()
    val u = username.orEmpty()
    val p = postTime.orEmpty()
    val f = forumName.orEmpty()
    return if (t.isNotBlank() || u.isNotBlank() || p.isNotBlank() || f.isNotBlank()) {
        "combo:$t|$u|$p|$f"
    } else {
        "index:$index"
    }
}