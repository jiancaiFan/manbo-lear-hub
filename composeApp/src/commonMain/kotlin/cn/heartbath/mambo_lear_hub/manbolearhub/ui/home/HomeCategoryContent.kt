package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cn.heartbath.mambo_lear_hub.manbolearhub.model.forumdetail.HomeUIModel.PostItem

@Composable
internal fun HomeCategoryContent(
    data: List<PostItem>,
    isLoading: Boolean,
    errorMessage: String?
) {
    if (!isLoading && errorMessage.isNullOrBlank() && data.isNotEmpty()) {
        HomePostList(postList = data)
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