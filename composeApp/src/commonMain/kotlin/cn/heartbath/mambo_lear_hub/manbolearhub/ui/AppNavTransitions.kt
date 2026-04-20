package cn.heartbath.mambo_lear_hub.manbolearhub.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry

private const val NAV_DURATION = 380
private val LayerEasing = CubicBezierEasing(0.22f, 0.0f, 0.0f, 1.0f)

object AppNavTransitions {

    // 前进：新页在上层推进
    val enter: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        slideInHorizontally(
            animationSpec = tween(durationMillis = NAV_DURATION, easing = LayerEasing),
            initialOffsetX = { fullWidth -> fullWidth }
        ) + fadeIn(
            animationSpec = tween(durationMillis = NAV_DURATION, easing = LayerEasing),
            initialAlpha = 0.96f
        ) + scaleIn(
            animationSpec = tween(durationMillis = NAV_DURATION, easing = LayerEasing),
            initialScale = 0.985f
        )
    }

    // 前进：旧页退到底层（明显，但不过重）
    val exit: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        slideOutHorizontally(
            animationSpec = tween(durationMillis = NAV_DURATION, easing = LayerEasing),
            targetOffsetX = { fullWidth -> -(fullWidth * 0.10f).toInt() }
        ) + fadeOut(
            animationSpec = tween(durationMillis = NAV_DURATION, easing = LayerEasing),
            targetAlpha = 0.55f
        ) + scaleOut(
            animationSpec = tween(durationMillis = NAV_DURATION, easing = LayerEasing),
            targetScale = 0.88f
        )
    }

    // 返回：底层页恢复
    val popEnter: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        slideInHorizontally(
            animationSpec = tween(durationMillis = NAV_DURATION, easing = LayerEasing),
            initialOffsetX = { fullWidth -> -(fullWidth * 0.10f).toInt() }
        ) + fadeIn(
            animationSpec = tween(durationMillis = NAV_DURATION, easing = LayerEasing),
            initialAlpha = 0.55f
        ) + scaleIn(
            animationSpec = tween(durationMillis = NAV_DURATION, easing = LayerEasing),
            initialScale = 0.88f
        )
    }

    // 返回：前景页完整离场
    val popExit: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        slideOutHorizontally(
            animationSpec = tween(durationMillis = NAV_DURATION, easing = LayerEasing),
            targetOffsetX = { fullWidth -> fullWidth }
        ) + fadeOut(
            animationSpec = tween(durationMillis = NAV_DURATION, easing = LayerEasing),
            targetAlpha = 0.96f
        ) + scaleOut(
            animationSpec = tween(durationMillis = NAV_DURATION, easing = LayerEasing),
            targetScale = 0.985f
        )
    }
}