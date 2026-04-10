package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import coil3.svg.SvgDecoder

private val ColorBg = Color(0xFFF5F6F7)
private val ColorCard = Color.White
private val ColorTitle = Color(0xFF1F2329)
private val ColorSummary = Color(0xFF4E5969)
private val ColorMeta = Color(0xFF86909C)
private val ColorPlaceholder = Color(0xFFDFE1E6)

@Composable
internal fun HomePostList(postList: List<HomeUIModel.PostItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBg),
        contentPadding = PaddingValues(top = 8.dp, bottom = 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(postList) { item ->
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
            .padding(horizontal = 10.dp)
            .semantics(mergeDescendants = true) {},
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = ColorCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                val avatarUrl = item.avatarUrl.orEmpty()
                if (avatarUrl.isNotBlank()) {
                    AsyncImage(
                        model = ImageRequest.Builder(context).data(avatarUrl).build(),
                        imageLoader = imageLoader,
                        contentDescription = "${username}头像",
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(ColorPlaceholder),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(ColorPlaceholder)
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
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = ColorTitle
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = postTime,
                        style = TextStyle(fontSize = 12.sp, color = ColorMeta),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            if (!item.title.isNullOrBlank()) {
                Text(
                    text = item.title,
                    modifier = Modifier.padding(top = 8.dp),
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

            if (item.imageList.isNotEmpty()) {
                if (item.imageList.size == 1) {
                    AsyncImage(
                        model = item.imageList.first(),
                        contentDescription = "图片",
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(ColorPlaceholder),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    FlowRow(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        item.imageList.take(9).forEach { imageUrl ->
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .width(104.dp)
                                    .height(78.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(ColorPlaceholder),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "阅读 ${item.readCount ?: 0}  回复 ${item.replyCount ?: 0}",
                    style = TextStyle(fontSize = 12.sp, color = ColorMeta)
                )
                Text(
                    text = forumName,
                    style = TextStyle(fontSize = 12.sp, color = ColorMeta),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}