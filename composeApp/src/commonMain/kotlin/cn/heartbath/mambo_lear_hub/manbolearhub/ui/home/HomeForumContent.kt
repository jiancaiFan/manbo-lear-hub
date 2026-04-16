package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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
    errorMessage: String?
) {
    if (!isLoading && errorMessage.isNullOrBlank() && forumCategories.isNotEmpty()) {
        HomeForumList(
            forumCategories = forumCategories,
            selectedForumCategory = selectedForumCategory,
            onSelectedForumCategory = onSelectedForumCategory,
            navigateToForumDetail = navigateToForumDetail
        )
        return
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> CircularProgressIndicator()
            !errorMessage.isNullOrBlank() -> Text(text = errorMessage)
            else -> Text(text = "暂无数据")
        }
    }
}