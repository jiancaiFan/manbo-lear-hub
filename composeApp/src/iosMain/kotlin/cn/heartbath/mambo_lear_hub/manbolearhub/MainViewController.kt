package cn.heartbath.mambo_lear_hub.manbolearhub

import androidx.compose.ui.window.ComposeUIViewController
import cn.heartbath.mambo_lear_hub.manbolearhub.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}