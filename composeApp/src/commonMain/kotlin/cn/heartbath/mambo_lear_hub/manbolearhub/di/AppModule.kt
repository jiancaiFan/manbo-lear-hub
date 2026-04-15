package cn.heartbath.mambo_lear_hub.manbolearhub.di

import cn.heartbath.mambo_lear_hub.manbolearhub.network.KtorNetworkClient
import cn.heartbath.mambo_lear_hub.manbolearhub.network.NetworkClient
import cn.heartbath.mambo_lear_hub.manbolearhub.network.createKtorRawClient
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.home.HomeRepositoryImpl
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.home.HomeState
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.home.HomeStoreProvider
import cn.heartbath.mambo_lear_hub.manbolearhub.repository.home.HomeRepository
import cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.ManBoLearHubViewModel
import cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.home.HomeViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.reduxkotlin.Store

private const val BASE_URL = "https://43.139.98.90/"

val appModule = module {
    single { ManBoLearHubViewModel() }

    single { createKtorRawClient() }

    single<NetworkClient> {
        KtorNetworkClient(
            baseUrl = BASE_URL,
            client = get()
        )
    }

    single<HomeRepository> {
        HomeRepositoryImpl(
            networkClient = get(),
            baseUrl = BASE_URL
        )
    }

    single<Store<HomeState>> {
        HomeStoreProvider.create(repository = get())
    }

    single {
        HomeViewModel(store = get())
    }
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