package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.heartbath.mambo_lear_hub.manbolearhub.constants.CommonColors
import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
internal fun HomePostItem(
    item: HomeUIModel.PostItem,
    imageLoader: ImageLoader
) {
    val username = item.username.orEmpty().ifBlank { "匿名用户" }
    val postTime = item.postTime.orEmpty().ifBlank { "刚刚" }
    val forumName = item.forumName.orEmpty().ifBlank { "社区" }
    val images = item.imageList.take(9)

    val accessibilityText = buildString {
        item.title?.takeIf { it.isNotBlank() }?.let { append(it).append('。') }
        item.summary?.takeIf { it.isNotBlank() }?.let { append(it).append('。') }
        append("作者$username。发布时间$postTime。阅读${item.readCount ?: 0}，回复${item.replyCount ?: 0}。")
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) { contentDescription = accessibilityText },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CommonColors.BackgroundWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(0.6.dp, CommonColors.CardBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 9.dp)
        ) {
            PostHeader(
                username = username,
                postTime = postTime,
                forumName = forumName,
                avatarUrl = item.avatarUrl.orEmpty(),
                imageLoader = imageLoader
            )

            item.title?.takeIf { it.isNotBlank() }?.let { title ->
                Text(
                    text = title,
                    modifier = Modifier.padding(top = 7.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 19.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = CommonColors.Title
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            item.summary?.takeIf { it.isNotBlank() }?.let { summary ->
                Text(
                    text = summary,
                    modifier = Modifier.padding(top = 4.dp),
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = CommonColors.Summary
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            PostImages(images = images, imageLoader = imageLoader)

            HorizontalDivider(
                modifier = Modifier.padding(top = 7.dp),
                color = CommonColors.DividerSoft,
                thickness = 1.dp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("阅读 ${item.readCount ?: 0}", style = TextStyle(fontSize = 10.sp, color = CommonColors.Meta))
                Spacer(modifier = Modifier.width(10.dp))
                Text("回复 ${item.replyCount ?: 0}", style = TextStyle(fontSize = 10.sp, color = CommonColors.Meta))
            }
        }
    }
}

@Composable
private fun PostHeader(
    username: String,
    postTime: String,
    forumName: String,
    avatarUrl: String,
    imageLoader: ImageLoader
) {
    val platformContext = LocalPlatformContext.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (avatarUrl.isNotBlank()) {
            AsyncImage(
                model = ImageRequest.Builder(platformContext).data(avatarUrl).crossfade(false).build(),
                imageLoader = imageLoader,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(CommonColors.Placeholder),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(CommonColors.Placeholder)
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        ) {
            Text(
                text = username,
                style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium, color = CommonColors.Title),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = postTime,
                style = TextStyle(fontSize = 10.sp, color = CommonColors.Meta),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(CommonColors.PrimaryBlueLightBg)
                .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
            Text(
                text = forumName,
                style = TextStyle(fontSize = 10.sp, color = CommonColors.PrimaryBlue, fontWeight = FontWeight.Medium),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun PostImages(
    images: List<String>,
    imageLoader: ImageLoader
) {
    val platformContext = LocalPlatformContext.current

    when (images.size) {
        0 -> Unit
        1 -> AsyncImage(
            model = ImageRequest.Builder(platformContext).data(images.first()).crossfade(false).build(),
            imageLoader = imageLoader,
            contentDescription = "帖子配图",
            modifier = Modifier
                .padding(top = 7.dp)
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(9.dp))
                .background(CommonColors.Placeholder),
            contentScale = ContentScale.Crop
        )

        2 -> BoxWithConstraints(
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
                        model = ImageRequest.Builder(platformContext).data(url).crossfade(false).build(),
                        imageLoader = imageLoader,
                        contentDescription = null,
                        modifier = Modifier
                            .width(itemWidth)
                            .height(itemWidth * 0.72f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(CommonColors.Placeholder),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        else -> BoxWithConstraints(
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
                images.take(9).forEach { url ->
                    AsyncImage(
                        model = ImageRequest.Builder(platformContext).data(url).crossfade(false).build(),
                        imageLoader = imageLoader,
                        contentDescription = null,
                        modifier = Modifier
                            .size(itemSize)
                            .clip(RoundedCornerShape(8.dp))
                            .background(CommonColors.Placeholder),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}