package cn.heartbath.manbolearhub.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
internal fun ManBoLearHubNavGraph() {

}

enum class ManBoMainTabType(title: String) {
    HOME("home"),
    CIRCLE("circle"),
    PROFILE("profile")
}

data class TabDisplayItem(
    val tab: ManBoMainTabType,
    val label: String,
    val icon: ImageVector
)

val TabDisplayItems = listOf(
    TabDisplayItem(ManBoMainTabType.HOME, "首页", Icons.Filled.Home),
    TabDisplayItem(ManBoMainTabType.CIRCLE, "圈子", Icons.Filled.Explore),
    TabDisplayItem(ManBoMainTabType.PROFILE, "我的", Icons.Filled.Person)
)