package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel
import coil3.compose.AsyncImage

@Composable
internal fun HomeForumList(
    forumCategories: List<HomeUIModel.ForumCategory>,
    selectedForumCategory: Int,
    onSelectedForumCategory: (Int) -> Unit,
) {

    Row(modifier = Modifier.fillMaxSize().padding(end = 16.dp)) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.White),
        ) {
            itemsIndexed(forumCategories) { index, category ->

                val isSelected = selectedForumCategory == index
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectedForumCategory(index) }
                        .background(if (isSelected) Color(0xFFF5F5F5) else Color.White),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(4.dp, 40.dp)
                            .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = category.categoryName,
                        color = if (isSelected) Color(0xFF333333) else Color(0xFF666666),
                        fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        LazyColumn(
            modifier = Modifier
                .weight(3f)
                .fillMaxHeight()
        ) {
            forumCategories.getOrNull(selectedForumCategory)?.forumList?.let {
                items(it) { forumItem ->
                    HomeForumItem(
                        forumItem = forumItem,
                        onClick = {}
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeForumItem(
    forumItem: HomeUIModel.ForumCategory.ForumItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = forumItem.iconUrl.orEmpty(),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(6.dp)),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxHeight()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = forumItem.forumName,
                fontSize = 12.sp,
                color = Color(0xFF333333),
                fontWeight = FontWeight.Medium
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "主题 ${forumItem.threadCount}",
                    fontSize = 8.sp,
                    color = Color(0xFF666666),
                    fontWeight = FontWeight.Normal
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "贴数 ${forumItem.postCount}",
                    fontSize = 8.sp,
                    color = Color(0xFF666666),
                    fontWeight = FontWeight.Normal
                )
            }
            Text(
                text = forumItem.forumDescription.orEmpty(),
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color(0xFF333333),
                fontWeight = FontWeight.Medium
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "最后发表 ${forumItem.lastPostTime ?: "--"}",
                fontSize = 8.sp,
                maxLines = 1,
                color = Color(0xFF666666),
                fontWeight = FontWeight.Normal
            )
            Text(
                text = "来自 ${forumItem.lastPostAuthor ?: "--"}",
                fontSize = 8.sp,
                maxLines = 1,
                color = Color(0xFF666666),
                fontWeight = FontWeight.Normal
            )
        }
    }
}