package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.home.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinInject(),
    navigateToForumDetail: (Int) -> Unit
) {
    val homeState by viewModel.homeState.collectAsState()
    val homeUiModel = homeState.uiModel
    val categories = homeUiModel.categories
    val selectedCategory = homeUiModel.selectedCategory
    val selectedForumCategory = homeUiModel.selectedForumCategory

    val pagerState = rememberPagerState(
        initialPage = selectedCategory.coerceAtLeast(0),
        pageCount = { categories.size }
    )
    val scope = rememberCoroutineScope()

    // VM 状态 -> Pager（用于状态恢复或外部修改 selectedCategory 时同步）
    LaunchedEffect(selectedCategory, categories.size) {
        if (categories.isEmpty()) return@LaunchedEffect
        val safeIndex = selectedCategory.coerceIn(0, categories.lastIndex)
        if (pagerState.currentPage != safeIndex && pagerState.targetPage != safeIndex) {
            pagerState.animateScrollToPage(safeIndex)
        }
    }

    // Pager -> VM（关键：使用 settledPage，避免动画经过中间页导致选错）
    LaunchedEffect(pagerState.settledPage, categories.size) {
        if (categories.isEmpty()) return@LaunchedEffect
        val settled = pagerState.settledPage.coerceIn(0, categories.lastIndex)
        if (settled != selectedCategory) {
            viewModel.onCategorySelected(settled)
        }
    }

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
            categories = categories,
            selectedCategory = pagerState.currentPage,
            onSelectedCategory = { index ->
                if (index !in categories.indices) return@HomeCategoryNavBar
                if (index == pagerState.currentPage) return@HomeCategoryNavBar
                scope.launch {
                    pagerState.animateScrollToPage(index)
                }
            }
        )

        if (categories.isNotEmpty()) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                if (page == categories.lastIndex) {
                    HomeForumContent(
                        forumCategories = homeUiModel.forumCategories,
                        selectedForumCategory = selectedForumCategory,
                        onSelectedForumCategory = viewModel::onForumCategorySelected,
                        navigateToForumDetail = navigateToForumDetail,
                        isLoading = homeState.isLoading,
                        errorMessage = homeState.errorMessage.takeIf { homeState.isError }
                    )
                } else {
                    val currentCategoryPostList = homeUiModel.categoryDataMap[page].orEmpty()
                    HomeCategoryContent(
                        data = currentCategoryPostList,
                        isLoading = homeState.isLoading,
                        errorMessage = homeState.errorMessage.takeIf { homeState.isError }
                    )
                }
            }
        } else {
            HomeCategoryContent(
                data = emptyList(),
                isLoading = homeState.isLoading,
                errorMessage = homeState.errorMessage.takeIf { homeState.isError }
            )
        }
    }
}