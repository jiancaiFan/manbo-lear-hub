package cn.heartbath.mambo_lear_hub.manbolearhub.ui.profile

import androidx.compose.runtime.Composable

@Composable
internal fun ProfileScreen(
    isLoggedIn: Boolean = false,
    onLoginClick: () -> Unit = {},
    onGuestClick: () -> Unit = {}
) {
    if (isLoggedIn) {
        ProfileAuthedScreen()
    } else {
        ProfileGuestScreen(
            onLoginClick = onLoginClick,
            onGuestClick = onGuestClick
        )
    }
}

@Composable
private fun ProfileAuthedScreen() {
    // TODO: 已登录页面
}