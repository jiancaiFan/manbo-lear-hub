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
internal fun HomeCategoryContent(
    homeState: HomeState,
    page: Int,
    onScrollDirectionChanged: (Boolean) -> Unit
) {
    val data = homeState.uiModel.categoryDataMap[page].orEmpty()

    when {
        homeState.isSuccess && data.isNotEmpty() -> {
            HomePostList(
                postList = data,
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