package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel

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
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
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
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* TODO: 添加点击事件 */ }
                            .padding(16.dp)
                            .background(MaterialTheme.colorScheme.surface),
                    ) {
                        Text(
                            text = forumItem.forumName,
                            color = Color(0xFF333333),
                            fontWeight = FontWeight.Medium
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            thickness = DividerDefaults.Thickness,
                            color = Color(0xFFE0E0E0)
                        )
                    }
                }
            }
        }
    }
}