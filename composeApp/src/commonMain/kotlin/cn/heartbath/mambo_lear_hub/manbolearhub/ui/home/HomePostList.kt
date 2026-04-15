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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.svg.SvgDecoder

private val ColorPageBg = Color(0xFFF8FAFC)
private val ColorCard = Color.White
private val ColorTitle = Color(0xFF111827)
private val ColorSummary = Color(0xFF4B5563)
private val ColorMeta = Color(0xFF6B7280)
private val ColorPlaceholder = Color(0xFFE5E7EB)
private val ColorPrimary = Color(0xFF2563EB)

@Composable
internal fun HomePostList(postList: List<HomeUIModel.PostItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorPageBg),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(
            items = postList,
            key = { item ->
                "${item.title.orEmpty()}_${item.username.orEmpty()}_${item.postTime.orEmpty()}"
            }
        ) { item ->
            PostItemCard(item)
        }
    }
}

@Composable
private fun PostItemCard(item: HomeUIModel.PostItem) {
    val context = LocalPlatformContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components { add(SvgDecoder.Factory()) }
            .build()
    }

    val username = item.username.orEmpty().ifBlank { "匿名用户" }
    val postTime = item.postTime.orEmpty().ifBlank { "刚刚" }
    val forumName = item.forumName.orEmpty().ifBlank { "社区" }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) {},
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ColorCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
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
                val avatarUrl = item.avatarUrl.orEmpty()
                if (avatarUrl.isNotBlank()) {
                    AsyncImage(
                        model = ImageRequest.Builder(context).data(avatarUrl).build(),
                        imageLoader = imageLoader,
                        contentDescription = "${username}头像",
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(ColorPlaceholder),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(ColorPlaceholder)
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = username,
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = ColorTitle
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = postTime,
                        style = TextStyle(fontSize = 11.sp, color = ColorMeta),
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
                        text = forumName,
                        style = TextStyle(
                            fontSize = 11.sp,
                            color = ColorPrimary,
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
                        color = ColorTitle
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
                        color = ColorSummary
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            val images = item.imageList.take(9)
            if (images.isNotEmpty()) {
                when (images.size) {
                    1 -> {
                        AsyncImage(
                            model = images.first(),
                            contentDescription = "帖子图片",
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth()
                                .height(190.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(ColorPlaceholder),
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
                            val itemSize = (maxWidth - spacing) / 2

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(spacing)
                            ) {
                                images.forEach { imageUrl ->
                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .width(itemSize)
                                            .height(itemSize * 0.72f)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(ColorPlaceholder),
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
                            val columns = 3
                            val itemSize: Dp =
                                (maxWidth - spacing * (columns - 1)) / columns

                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(spacing),
                                verticalArrangement = Arrangement.spacedBy(spacing)
                            ) {
                                images.forEach { imageUrl ->
                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .width(itemSize)
                                            .height(itemSize)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(ColorPlaceholder),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                    }
                }
            }

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
                    style = TextStyle(fontSize = 12.sp, color = ColorMeta)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "回复 ${item.replyCount ?: 0}",
                    style = TextStyle(fontSize = 12.sp, color = ColorMeta)
                )
            }
        }
    }
}