package cn.heartbath.mambo_lear_hub.manbolearhub.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry

private const val NAV_DURATION = 220
private val LayerEasing = CubicBezierEasing(0.22f, 0.0f, 0.0f, 1.0f)

object AppNavTransitions {

    val enter: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        slideInHorizontally(
            animationSpec = tween(durationMillis = NAV_DURATION, easing = LayerEasing),
            initialOffsetX = { it }
        )
    }

    val exit: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        slideOutHorizontally(
            animationSpec = tween(durationMillis = NAV_DURATION, easing = LayerEasing),
            targetOffsetX = { -(it * 0.04f).toInt() }
        )
    }

    val popEnter: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        slideInHorizontally(
            animationSpec = tween(durationMillis = NAV_DURATION, easing = LayerEasing),
            initialOffsetX = { -(it * 0.04f).toInt() }
        )
    }

    val popExit: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        slideOutHorizontally(
            animationSpec = tween(durationMillis = NAV_DURATION, easing = LayerEasing),
            targetOffsetX = { it }
        )
    }
}