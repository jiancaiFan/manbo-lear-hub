package cn.heartbath.mambo_lear_hub.manbolearhub.ui.forumdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cn.heartbath.mambo_lear_hub.manbolearhub.constants.CommonColors
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.forumdetail.ForumDetailState
import cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.forumdetail.ForumDetailViewModel
import coil3.ImageLoader
import coil3.compose.LocalPlatformContext
import coil3.svg.SvgDecoder
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
internal fun ForumDetailScreen(
    forumId: Int?,
    onBackToHome: () -> Unit,
    viewModel: ForumDetailViewModel = koinInject(),
) {
    val state by viewModel.forumDetailState.collectAsState()
    val forumDetailUiModel = state.forumDetailUIModel
    val platformContext = LocalPlatformContext.current
    val imageLoader = remember {
        ImageLoader.Builder(platformContext)
            .components { add(SvgDecoder.Factory()) }
            .build()
    }

    val forumTabTitleList = forumDetailUiModel.header.tabList.map { it.title }
        .ifEmpty { listOf("最新", "精华", "热门") }

    val safeSelectedTabIndex = forumDetailUiModel.selectedTabIndex
        .coerceIn(0, forumTabTitleList.lastIndex.coerceAtLeast(0))

    val pagerState = rememberPagerState(
        initialPage = safeSelectedTabIndex,
        pageCount = { forumTabTitleList.size }
    )
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(forumId) {
        viewModel.fetchForumDetail(forumId)
    }

    LaunchedEffect(safeSelectedTabIndex, forumTabTitleList.size) {
        if (forumTabTitleList.isEmpty()) return@LaunchedEffect
        if (pagerState.currentPage != safeSelectedTabIndex && pagerState.targetPage != safeSelectedTabIndex) {
            pagerState.scrollToPage(safeSelectedTabIndex)
        }
    }

    LaunchedEffect(pagerState.settledPage, forumTabTitleList.size) {
        if (forumTabTitleList.isEmpty()) return@LaunchedEffect
        val settledPageIndex = pagerState.settledPage.coerceIn(0, forumTabTitleList.lastIndex)
        if (settledPageIndex != forumDetailUiModel.selectedTabIndex) {
            viewModel.onTabSelected(settledPageIndex)
        }
    }

    Scaffold(
        containerColor = CommonColors.HomePageBg,
        topBar = {
            ForumTopActionBar(
                title = forumDetailUiModel.header.forumName.ifBlank { "Forum" },
                onBack = onBackToHome,
                onSearchClick = {},
                onShareClick = {}
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO */ },
                shape = CircleShape,
                containerColor = CommonColors.PrimaryBlue
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Create post",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ForumDetailHeader(
                header = forumDetailUiModel.header,
                onFollowClick = viewModel::onFavoriteClick
            )

            ForumDetailContent(
                state = state,
                forumTabTitleList = forumTabTitleList,
                selectedTabIndex = pagerState.currentPage,
                onTabSelected = { tabIndex ->
                    if (tabIndex !in forumTabTitleList.indices) return@ForumDetailContent
                    if (tabIndex == pagerState.currentPage) return@ForumDetailContent
                    viewModel.onTabSelected(tabIndex)
                    coroutineScope.launch { pagerState.animateScrollToPage(tabIndex) }
                },
                pagerState = pagerState,
                imageLoader = imageLoader,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
internal fun ForumDetailContent(
    state: ForumDetailState,
    forumTabTitleList: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    pagerState: androidx.compose.foundation.pager.PagerState,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        ForumDetailTabList(
            forumTabTitleList = forumTabTitleList,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected
        )

        ForumThreadListPager(
            state = state,
            pagerState = pagerState,
            imageLoader = imageLoader,
            modifier = Modifier.weight(1f)
        )
    }
}