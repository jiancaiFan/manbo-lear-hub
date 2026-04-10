package cn.heartbath.manbolearhub.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cn.heartbath.mambo_lear_hub.manbolearhub.components.BottomNavigationBar
import cn.heartbath.manbolearhub.ui.ManBoMainTabType
import cn.heartbath.manbolearhub.ui.TabDisplayItems
import cn.heartbath.mambo_lear_hub.manbolearhub.ui.circle.CircleScreen
import cn.heartbath.mambo_lear_hub.manbolearhub.ui.home.HomeScreen
import cn.heartbath.mambo_lear_hub.manbolearhub.ui.profile.ProfileScreen
import cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.ManBoLearHubViewModel
import org.koin.compose.koinInject

@Composable
internal fun MainScreen(
    viewModel: cn.heartbath.manbolearhub.viewmodel.ManBoLearHubViewModel = koinInject()
) {
    val selectedTab by viewModel.selectedTab.collectAsState()

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            _root_ide_package_.cn.heartbath.manbolearhub.components.BottomNavigationBar(
                items = TabDisplayItems,
                currentTab = selectedTab,
                onItemClick = { viewModel.onTabSelected(it.tab) }
            )
        }
    ) { innerPadding ->
        when (selectedTab) {
            ManBoMainTabType.HOME -> _root_ide_package_.cn.heartbath.manbolearhub.ui.home.HomeScreen(
                modifier = Modifier.padding(innerPadding)
            )

            ManBoMainTabType.CIRCLE -> _root_ide_package_.cn.heartbath.manbolearhub.ui.circle.CircleScreen()
            ManBoMainTabType.PROFILE -> _root_ide_package_.cn.heartbath.manbolearhub.ui.profile.ProfileScreen()
        }
    }
}