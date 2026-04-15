package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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

private val PageBg = Color(0xFFF8FAFC)
private val CardBg = Color.White
private val TitleColor = Color(0xFF111827)
private val SummaryColor = Color(0xFF4B5563)
private val MetaColor = Color(0xFF6B7280)
private val PlaceholderColor = Color(0xFFE5E7EB)
private val PrimaryColor = Color(0xFF2563EB)

@Composable
internal fun HomePostList(postList: List<HomeUIModel.PostItem>) {
    val context = LocalPlatformContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components { add(SvgDecoder.Factory()) }
            .build()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBg),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
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

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) {},
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp)
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
                        contentDescription = "${item.username.orEmpty().ifBlank { "匿名用户" }}头像",
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(PlaceholderColor),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(PlaceholderColor)
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = item.username.orEmpty().ifBlank { "匿名用户" },
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = TitleColor
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = item.postTime.orEmpty().ifBlank { "刚刚" },
                        style = TextStyle(fontSize = 11.sp, color = MetaColor),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(Color(0xFFEFF6FF))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = item.forumName.orEmpty().ifBlank { "社区" },
                        style = TextStyle(
                            fontSize = 11.sp,
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
                    modifier = Modifier.padding(top = 10.dp),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
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
                    modifier = Modifier.padding(top = 6.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 21.sp,
                        color = SummaryColor
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            PostImages(
                imageUrls = item.imageList.take(9),
                imageLoader = imageLoader
            )

            HorizontalDivider(
                modifier = Modifier.padding(top = 10.dp),
                color = Color(0xFFF1F5F9),
                thickness = 1.dp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "阅读 ${item.readCount ?: 0}",
                    style = TextStyle(fontSize = 12.sp, color = MetaColor)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "回复 ${item.replyCount ?: 0}",
                    style = TextStyle(fontSize = 12.sp, color = MetaColor)
                )
            }
        }
    }
}

@Composable
private fun PostImages(
    imageUrls: List<String>,
    imageLoader: ImageLoader
) {
    if (imageUrls.isEmpty()) return
    val context = LocalPlatformContext.current

    when (imageUrls.size) {
        1 -> {
            AsyncImage(
                model = ImageRequest.Builder(context).data(imageUrls.first()).crossfade(false).build(),
                imageLoader = imageLoader,
                contentDescription = "帖子图片",
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .height(190.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(PlaceholderColor),
                contentScale = ContentScale.Crop
            )
        }

        2 -> {
            BoxWithConstraints(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
            ) {
                val spacing = 8.dp
                val itemWidth = (maxWidth - spacing) / 2
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(spacing)
                ) {
                    imageUrls.forEach { url ->
                        AsyncImage(
                            model = ImageRequest.Builder(context).data(url).crossfade(false).build(),
                            imageLoader = imageLoader,
                            contentDescription = null,
                            modifier = Modifier
                                .width(itemWidth)
                                .height(itemWidth * 0.72f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(PlaceholderColor),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

        else -> {
            BoxWithConstraints(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
            ) {
                val spacing = 8.dp
                val itemSize = (maxWidth - spacing * 2) / 3
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(spacing),
                    verticalArrangement = Arrangement.spacedBy(spacing)
                ) {
                    imageUrls.forEach { url ->
                        AsyncImage(
                            model = ImageRequest.Builder(context).data(url).crossfade(false).build(),
                            imageLoader = imageLoader,
                            contentDescription = null,
                            modifier = Modifier
                                .size(itemSize)
                                .clip(RoundedCornerShape(10.dp))
                                .background(PlaceholderColor),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
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
    if (t.isNotBlank() || u.isNotBlank() || p.isNotBlank() || f.isNotBlank()) {
        return "combo:$t|$u|$p|$f"
    }

    return "index:$index"
}