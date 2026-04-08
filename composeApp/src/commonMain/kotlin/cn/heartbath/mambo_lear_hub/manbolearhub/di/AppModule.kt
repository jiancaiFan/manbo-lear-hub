package cn.heartbath.mambo_lear_hub.manbolearhub.di

import cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.ManBoLearHubViewModel
import cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.home.HomeViewModel
import org.koin.dsl.module
import org.koin.core.context.startKoin

val appModule = module {
    single { ManBoLearHubViewModel() }
    single { HomeViewModel() }
}

private var started = false

internal fun initKoin() {
    if (!started) {
        startKoin {
            modules(appModule)
        }
        started = true
    }
}