package cn.heartbath.mambo_lear_hub.manbolearhub

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cn.heartbath.mambo_lear_hub.manbolearhub.di.initKoin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "manbolearhub",
    ) {
        initKoin()
        App()
    }
}