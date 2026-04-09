package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.home.HomeViewModel
import org.koin.compose.koinInject

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinInject()
) {
    val homeState by viewModel.homeState.collectAsState()
    val uiModel = homeState.uiModel
    val selectedCategory = uiModel.selectedCategory

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeTopActionBar(
            unreadCount = 8,
            onProfileClick = { },
            onSearchClick = { },
            onMessageClick = { },
            onQuickActionClick = { },
        )

        HomeCategoryNavBar(
            categories = uiModel.categories,
            selectedCategory = selectedCategory,
            onSelectedChange = viewModel::onCategorySelected
        )

        HomeCategoryContent(
            selectedCategoryId = uiModel.categories.getOrNull(selectedCategory)?.id.orEmpty(),
            data = uiModel.categoryDataMap[selectedCategory] ?: emptyList(),
            isLoading = homeState.isLoading,
            errorMessage = if (homeState.isError) homeState.errorMessage else null
        )
    }
}