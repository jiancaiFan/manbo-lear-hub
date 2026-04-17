package cn.heartbath.mambo_lear_hub.manbolearhub.ui.forumdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.unit.dp
import cn.heartbath.mambo_lear_hub.manbolearhub.constants.CommonColors
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
    val uiModel = state.forumDetailUIModel

    val platformContext = LocalPlatformContext.current
    val imageLoader = remember {
        ImageLoader.Builder(platformContext)
            .components { add(SvgDecoder.Factory()) }
            .build()
    }

    val tabTitles = uiModel.header.tabList.map { it.title }
        .ifEmpty { listOf("最新", "精华", "热门") }

    val initialPage = uiModel.selectedTabIndex.coerceIn(0, (tabTitles.size - 1).coerceAtLeast(0))
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { tabTitles.size }
    )
    val scope = rememberCoroutineScope()

    LaunchedEffect(forumId) {
        viewModel.fetchForumDetail(forumId)
    }

    LaunchedEffect(uiModel.selectedTabIndex, tabTitles.size) {
        if (tabTitles.isEmpty()) return@LaunchedEffect
        val targetPage = uiModel.selectedTabIndex.coerceIn(0, tabTitles.lastIndex)
        if (pagerState.currentPage != targetPage) {
            pagerState.scrollToPage(targetPage)
        }
    }

    LaunchedEffect(pagerState.settledPage, tabTitles.size) {
        if (tabTitles.isEmpty()) return@LaunchedEffect
        viewModel.onTabSelected(pagerState.settledPage)
    }

    Scaffold(
        containerColor = CommonColors.HomePageBg,
        // 关键：去掉 Scaffold 默认底部 inset，让内容可延伸到导航栏区域
        contentWindowInsets = WindowInsets(top = 0.dp),
        topBar = {
            ForumTopActionBar(
                title = uiModel.header.forumName.ifBlank { "Forum" },
                onBack = onBackToHome,
                onSearchClick = {},
                onShareClick = {}
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO */ },
                shape = CircleShape,
                containerColor = CommonColors.PrimaryBlue,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .navigationBarsPadding()
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
                header = uiModel.header,
                onFollowClick = viewModel::onFavoriteClick
            )

            ForumDetailTabList(
                forumTabTitleList = tabTitles,
                selectedTabIndex = pagerState.currentPage,
                onTabSelected = { index ->
                    viewModel.onTabSelected(index)
                    scope.launch { pagerState.animateScrollToPage(index) }
                }
            )

            ForumThreadListPager(state, pagerState = pagerState, imageLoader = imageLoader, Modifier.weight(1f))
        }
    }
}