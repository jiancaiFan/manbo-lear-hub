package cn.heartbath.mambo_lear_hub.manbolearhub.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cn.heartbath.mambo_lear_hub.manbolearhub.ui.ManBoMainTab
import cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.ManBoLearHubViewModel
import org.koin.compose.koinInject

@Composable
internal fun MainScreen(
    viewModel: ManBoLearHubViewModel = koinInject()
) {
    val selectedTab by viewModel.selectedTab.collectAsState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                ManBoMainTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { viewModel.onTabSelected(tab) },
                        icon = { },
                        label = { }
                    )
                }
            }
        }
    ) {
        when (selectedTab) {
            ManBoMainTab.TAB_TYPE__HOME -> HomeScreen()
            ManBoMainTab.TAB_TYPE_CIRCLE -> CircleScreen()
            ManBoMainTab.TAB_TYPE_PROFILE -> ProfileScreen()
        }
    }
}

@Composable
fun HomeScreen() {
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("首页内容")
    }
}

@Composable
fun CircleScreen() {
    Box(modifier = Modifier.fillMaxSize()) { Text("发现内容") }
}

@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize()) { Text("我的内容") }
}