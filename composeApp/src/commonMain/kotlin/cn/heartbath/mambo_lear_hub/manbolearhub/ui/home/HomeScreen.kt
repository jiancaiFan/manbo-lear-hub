package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.home.HomeViewModel
import org.koin.compose.koinInject

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinInject()
) {
    val homeState by viewModel.homeState.collectAsState()
    val homeUiModel = homeState.uiModel
    val selectedCategory = homeUiModel.selectedCategory
    val isLastCategory = selectedCategory == homeUiModel.categories.lastIndex
    val currentCategoryPostList = homeUiModel.categoryDataMap[selectedCategory].orEmpty()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        HomeTopActionBar(
            unreadCount = 8,
            onProfileClick = {},
            onSearchClick = {},
            onMessageClick = {},
            onQuickActionClick = {}
        )

        HomeCategoryNavBar(
            categories = homeUiModel.categories,
            selectedCategory = selectedCategory,
            onSelectedChange = viewModel::onCategorySelected
        )

        if (isLastCategory) {
            HomeForumContent(
                forumCategories = homeUiModel.forumCategories,
                isLoading = homeState.isLoading,
                errorMessage = homeState.errorMessage.takeIf { homeState.isError }
            )
        } else {
            HomeCategoryContent(
                data = currentCategoryPostList,
                isLoading = homeState.isLoading,
                errorMessage = homeState.errorMessage.takeIf { homeState.isError }
            )
        }
    }
}