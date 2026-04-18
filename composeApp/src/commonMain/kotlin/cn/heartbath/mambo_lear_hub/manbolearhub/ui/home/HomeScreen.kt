package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.home.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinInject(),
    navigateToForumDetail: (Int) -> Unit,
    onScrollDirectionChanged: (Boolean) -> Unit = {}
) {
    val homeState by viewModel.homeState.collectAsState()
    val uiModel = homeState.uiModel
    val categories = uiModel.categories
    val selectedCategory = uiModel.selectedCategory

    val pagerState = rememberPagerState(
        initialPage = selectedCategory.coerceAtLeast(0),
        pageCount = { categories.size }
    )
    val scope = rememberCoroutineScope()
    var collapseTopCategoryBar by remember { mutableStateOf(false) }

    LaunchedEffect(selectedCategory, categories.size) {
        if (categories.isEmpty()) return@LaunchedEffect
        val safeIndex = selectedCategory.coerceIn(0, categories.lastIndex)
        if (pagerState.currentPage != safeIndex && pagerState.targetPage != safeIndex) {
            pagerState.animateScrollToPage(safeIndex)
        }
    }

    LaunchedEffect(pagerState.settledPage, categories.size) {
        if (categories.isEmpty()) return@LaunchedEffect
        val settled = pagerState.settledPage.coerceIn(0, categories.lastIndex)
        if (settled != selectedCategory) viewModel.onCategorySelected(settled)
    }

    val onListScroll: (Boolean) -> Unit = { scrollingDown ->
        collapseTopCategoryBar = scrollingDown
        onScrollDirectionChanged(scrollingDown)
    }

    Column(modifier = modifier.fillMaxSize()) {
        HomeTopActionBar(
            unreadCount = 8,
            onProfileClick = {},
            onSearchClick = {},
            onMessageClick = {},
            onQuickActionClick = {}
        )

        AnimatedVisibility(
            visible = !collapseTopCategoryBar,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            HomeCategoryNavBar(
                categories = categories,
                selectedCategory = pagerState.currentPage,
                onSelectedCategory = { index ->
                    if (index !in categories.indices || index == pagerState.currentPage) {
                        return@HomeCategoryNavBar
                    }
                    scope.launch { pagerState.animateScrollToPage(index) }
                }
            )
        }

        if (categories.isNotEmpty()) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                if (page == categories.lastIndex) {
                    HomeForumContent(
                        homeState = homeState,
                        onSelectedForumCategory = viewModel::onForumCategorySelected,
                        navigateToForumDetail = navigateToForumDetail,
                        onScrollDirectionChanged = onListScroll
                    )
                } else {
                    HomeCategoryContent(
                        homeState = homeState,
                        page = page,
                        onScrollDirectionChanged = onListScroll
                    )
                }
            }
        }
    }
}