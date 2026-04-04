package cn.heartbath.mambo_lear_hub.manbolearhub.ui.main

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    viewModel: ManBoLearHubViewModel = koinInject()
) {
    val selectedTab by viewModel.selectedTab.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = TabDisplayItems,
                currentTab = selectedTab,
                onItemClick = { viewModel.onTabSelected(it.tab) }
            )
        }
    ) {
        when (selectedTab) {
            ManBoMainTabType.HOME -> HomeScreen()
            ManBoMainTabType.CIRCLE -> CircleScreen()
            ManBoMainTabType.PROFILE -> ProfileScreen()
        }
    }
}