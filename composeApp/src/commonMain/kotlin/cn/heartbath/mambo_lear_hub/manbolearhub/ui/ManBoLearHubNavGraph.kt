package cn.heartbath.mambo_lear_hub.manbolearhub.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import cn.heartbath.mambo_lear_hub.manbolearhub.ui.forumdetail.ForumDetailScreen
import cn.heartbath.mambo_lear_hub.manbolearhub.ui.login.LoginScreen
import cn.heartbath.mambo_lear_hub.manbolearhub.ui.main.MainScreen
import cn.heartbath.mambo_lear_hub.manbolearhub.ui.register.RegisterScreen
import kotlinx.serialization.Serializable

@Composable
internal fun ManBoLearHubNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoute.Main,
        enterTransition = AppNavTransitions.enter,
        exitTransition = AppNavTransitions.exit,
        popEnterTransition = AppNavTransitions.popEnter,
        popExitTransition = AppNavTransitions.popExit
    ) {
        composable<AppRoute.Main> {
            MainScreen(
                navigateToForumDetail = { id ->
                    navController.navigate(AppRoute.ForumDetail(id))
                },
                navigateToLoginScreen = {
                    navController.navigate(AppRoute.LoginScreen)
                }
            )
        }

        composable<AppRoute.ForumDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<AppRoute.ForumDetail>()
            ForumDetailScreen(
                forumId = route.id,
                onBackToHome = { navController.popBackStack() }
            )
        }

        composable<AppRoute.LoginScreen> {
            LoginScreen(
                onBackClick = { navController.popBackStack() },
                onRegisterClick = { navController.navigate(AppRoute.RegisterScreen) }
            )
        }

        composable<AppRoute.RegisterScreen> {
            RegisterScreen(
                onBackClick = { navController.popBackStack() },
                onGoLoginClick = { navController.popBackStack() }
            )
        }
    }
}

@Serializable
sealed interface AppRoute {
    @Serializable
    data object Main : AppRoute

    @Serializable
    data class ForumDetail(val id: Int) : AppRoute

    @Serializable
    data object LoginScreen : AppRoute

    @Serializable
    data object RegisterScreen : AppRoute
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