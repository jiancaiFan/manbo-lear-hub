package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel

@Composable
fun HomeForumContent(
    forumCategories: List<HomeUIModel.ForumCategory>,
    selectedForumCategory: Int,
    onSelectedForumCategory: (Int) -> Unit,
    navigateToForumDetail: (Int) -> Unit,
    isLoading: Boolean,
    errorMessage: String?,
    onScrollDirectionChanged: (Boolean) -> Unit
) {
    if (!isLoading && errorMessage.isNullOrBlank() && forumCategories.isNotEmpty()) {
        HomeForumList(
            forumCategories = forumCategories,
            selectedForumCategory = selectedForumCategory,
            onSelectedForumCategory = onSelectedForumCategory,
            navigateToForumDetail = navigateToForumDetail,
            onScrollDirectionChanged = onScrollDirectionChanged
        )
        return
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            isLoading -> CircularProgressIndicator()
            !errorMessage.isNullOrBlank() -> Text(text = errorMessage)
            else -> Text(text = "暂无数据")
        }
    }
}