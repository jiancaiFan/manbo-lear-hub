package cn.heartbath.mambo_lear_hub.manbolearhub.di

import cn.heartbath.mambo_lear_hub.manbolearhub.redux.home.HomeRepositoryImpl
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.home.HomeState
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.home.HomeStoreProvider
import cn.heartbath.mambo_lear_hub.manbolearhub.repository.home.HomeRepository
import cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.ManBoLearHubViewModel
import cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.home.HomeViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.reduxkotlin.Store

val appModule = module {
    single { ManBoLearHubViewModel() }

    single<HomeRepository> { HomeRepositoryImpl() }

    single<Store<HomeState>> { HomeStoreProvider.create(repository = get()) }

    factory { HomeViewModel(store = get()) }
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