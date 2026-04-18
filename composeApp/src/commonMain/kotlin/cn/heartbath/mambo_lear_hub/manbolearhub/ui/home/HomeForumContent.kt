package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.home.HomeState

@Composable
fun HomeForumContent(
    homeState: HomeState,
    onSelectedForumCategory: (Int) -> Unit,
    navigateToForumDetail: (Int) -> Unit,
    onScrollDirectionChanged: (Boolean) -> Unit
) {
    val uiModel = homeState.uiModel
    val forumCategories = uiModel.forumCategories
    val selectedForumCategory = uiModel.selectedForumCategory

    when {
        homeState.isSuccess && forumCategories.isNotEmpty() -> {
            HomeForumList(
                forumCategories = forumCategories,
                selectedForumCategory = selectedForumCategory,
                onSelectedForumCategory = onSelectedForumCategory,
                navigateToForumDetail = navigateToForumDetail,
                onScrollDirectionChanged = onScrollDirectionChanged
            )
        }

        homeState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        homeState.isError -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = homeState.errorMessage ?: "加载失败")
            }
        }

        else -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "暂无数据")
            }
        }
    }
}