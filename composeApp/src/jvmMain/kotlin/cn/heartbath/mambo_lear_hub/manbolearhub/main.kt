package cn.heartbath.mambo_lear_hub.manbolearhub

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "manbolearhub",
    ) {
        App()
    }
}