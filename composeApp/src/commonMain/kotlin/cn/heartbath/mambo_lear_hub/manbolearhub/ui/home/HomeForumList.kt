package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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

    val borderColor = Color(0xFFDBEAFE)
    val shadowColor = Color(0x143B82F6)

    Row(
        modifier = Modifier.fillMaxSize().background(Color(0xFFF9FAFB))
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxHeight()
                .background(Color.White)
                .border(1.dp, borderColor),
        ) {
            itemsIndexed(forumCategories) { index, category ->

                val isSelected = selectedForumCategory == index
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable { onSelectedForumCategory(index) }
                        .then(
                            if (isSelected) Modifier.background(Color(0xFFEFF6FF))
                            else Modifier
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(4.dp, 40.dp)
                            .background(if (isSelected) Color(0xFF2563EB) else Color.Transparent)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = category.categoryName,
                        fontSize = 12.sp,
                        color = if (isSelected) Color(0xFF2563EB) else Color(0xFF9CA3AF),
                        fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            forumCategories.getOrNull(selectedForumCategory)?.forumList?.let {
                items(it) { forumItem ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clickable { }
                            .shadow(
                                elevation = 16.dp,
                                shape = RoundedCornerShape(16.dp),
                                clip = true,
                                ambientColor = shadowColor,
                                spotColor = shadowColor
                            )
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                                .weight(1f)
                                .fillMaxHeight()
                                .background(Color.White),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = forumItem.forumName,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "主题 ${forumItem.threadCount}",
                                    fontSize = 11.sp,
                                    color = Color.Gray,
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = "贴数 ${forumItem.postCount}",
                                    fontSize = 11.sp,
                                    color = Color.Gray,
                                )
                            }
                            Text(
                                text = forumItem.forumDescription.orEmpty(),
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.DarkGray,
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Color(0xFFD1D5DB)
                        )
                    }
                }
            }
        }
    }
}