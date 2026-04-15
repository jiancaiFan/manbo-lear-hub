package cn.heartbath.mambo_lear_hub.manbolearhub.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            BottomNavigationBar(
                items = TabDisplayItems,
                currentTab = selectedTab,
                onItemClick = { viewModel.onTabSelected(it.tab) }
            )
        }
    ) { innerPadding ->
        when (selectedTab) {
            ManBoMainTabType.HOME -> HomeScreen(
                modifier = Modifier.padding(innerPadding),
                navigateToForumDetail = navigateToForumDetail
            )

            ManBoMainTabType.CIRCLE -> CircleScreen()
            ManBoMainTabType.PROFILE -> ProfileScreen()
        }
    }
}