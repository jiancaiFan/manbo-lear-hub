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

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeTopBar(
            unreadCount = 8,
            onProfileClick = { /* TODO */ },
            onSearchClick = { /* TODO */ },
            onMessageClick = { /* TODO */ },
            onQuickActionClick = { /* TODO */ },
        )

        HomeCategoryNavBar(
            categories = homeState.categories,
            selectedCategoryId = homeState.selectedCategoryId,
            onSelectedChange = { viewModel.onCategorySelected(it.id) }
        )

        HomeCategoryContent(
            selectedCategoryId = homeState.selectedCategoryId,
            data = homeState.categoryDataMap[homeState.selectedCategoryId].orEmpty(),
            isLoading = homeState.loadingCategoryIds.contains(homeState.selectedCategoryId),
            errorMessage = homeState.errorMessage
        )
    }
}