package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel

@Composable
fun HomeForumContent(
    forumCategories: List<HomeUIModel.ForumCategory>,
    isLoading: Boolean,
    errorMessage: String?
) {
    val (selectedForumCategoryIndex, setSelectedForumCategory) = rememberSaveable { mutableStateOf(0) }
    if (!isLoading && errorMessage.isNullOrBlank() && forumCategories.isNotEmpty()) {
        Row(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.width(120.dp).fillMaxHeight().background(Color(0xFFF7F7F7))
            ) {
                forumCategories.forEachIndexed { index, category ->
                    val selected = index == selectedForumCategoryIndex
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(if (selected) Color(0xFFFFFFFF) else Color(0xFFF7F7F7))
                            .padding(vertical = 16.dp)
                            .clickable { setSelectedForumCategory(index) },
                        text = category.categoryName,
                        color = if (selected) Color(0xFFE53935) else Color(0xFF333333)
                    )
                }
            }
            // 右侧：分类下板块列表
            val forumList = forumCategories.getOrNull(selectedForumCategoryIndex)?.forumList.orEmpty()
            Column(
                modifier = Modifier.weight(1f).fillMaxHeight().padding(16.dp)
            ) {
                forumList.forEach { forum ->
                    ForumBlockItem(forum)
                }
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> CircularProgressIndicator()
                !errorMessage.isNullOrBlank() -> Text(text = errorMessage)
                else -> Text(text = "暂无板块")
            }
        }
    }
}

@Composable
fun ForumBlockItem(forum: HomeUIModel.ForumCategory.ForumInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // SVG可选实现：如无可略
        Column(Modifier.weight(1f)) {
            Text(forum.forumName, fontWeight = FontWeight.Bold)
            Text("今日: ${forum.todayThreads ?: 0}", fontSize = 12.sp)
            forum.description?.let { Text(it, fontSize = 12.sp) }
        }
    }
}