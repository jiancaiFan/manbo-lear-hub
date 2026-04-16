package cn.heartbath.mambo_lear_hub.manbolearhub.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Scaffold
import cn.heartbath.mambo_lear_hub.manbolearhub.components.BottomNavigationBar
import cn.heartbath.mambo_lear_hub.manbolearhub.ui.ManBoMainTabType
import cn.heartbath.mambo_lear_hub.manbolearhub.ui.TabDisplayItems
import cn.heartbath.mambo_lear_hub.manbolearhub.ui.circle.CircleScreen
import cn.heartbath.mambo_lear_hub.manbolearhub.ui.home.HomeScreen
import cn.heartbath.mambo_lear_hub.manbolearhub.ui.profile.ProfileScreen
import cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.ManBoLearHubViewModel
import org.koin.compose.koinInject

@Composable
internal fun MainScreen(
    viewModel: ManBoLearHubViewModel = koinInject(),
    navigateToForumDetail: (Int) -> Unit,
) {
    val selectedTab by viewModel.selectedTab.collectAsState()
    var isBarVisible by remember { mutableStateOf(true) }

    Scaffold(
        containerColor = Color.White,
        contentWindowInsets = WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                ManBoMainTabType.HOME -> HomeScreen(
                    modifier = Modifier.fillMaxSize(),
                    navigateToForumDetail = navigateToForumDetail,
                    onScrollDirectionChanged = { scrollingDown ->
                        isBarVisible = !scrollingDown
                    }
                )

                ManBoMainTabType.CIRCLE -> CircleScreen()
                ManBoMainTabType.PROFILE -> ProfileScreen()
            }

            AnimatedVisibility(
                visible = isBarVisible,
                enter = slideInVertically { it / 2 } + fadeIn(),
                exit = slideOutVertically { it / 2 } + fadeOut(),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                BottomNavigationBar(
                    items = TabDisplayItems,
                    currentTab = selectedTab,
                    onItemClick = { viewModel.onTabSelected(it.tab) }
                )
            }
        }
    }
}