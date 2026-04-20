package cn.heartbath.mambo_lear_hub.manbolearhub.di

import cn.heartbath.mambo_lear_hub.manbolearhub.network.KtorNetworkClient
import cn.heartbath.mambo_lear_hub.manbolearhub.network.NetworkClient
import cn.heartbath.mambo_lear_hub.manbolearhub.network.createKtorRawClient
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.forumdetail.ForumDetailRepositoryImpl
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.forumdetail.ForumDetailState
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.forumdetail.ForumDetailStoreProvider
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.home.HomeRepositoryImpl
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.home.HomeState
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.home.HomeStoreProvider
import cn.heartbath.mambo_lear_hub.manbolearhub.repository.forumdetail.ForumDetailRepository
import cn.heartbath.mambo_lear_hub.manbolearhub.repository.home.HomeRepository
import cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.ManBoLearHubViewModel
import cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.forumdetail.ForumDetailViewModel
import cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.home.HomeViewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.reduxkotlin.Store
import kotlin.math.sign

private const val BASE_URL = "https://43.139.98.90/"

val HOME_STORE = named("HOME_STORE")
val FORUM_DETAIL_STORE = named("FORUM_DETAIL_STORE")

val appModule = module {

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

    single<ForumDetailRepository> {
        ForumDetailRepositoryImpl(
            networkClient = get(),
            baseUrl = BASE_URL
        )
    }

    single<Store<HomeState>>(HOME_STORE) {
        HomeStoreProvider.create(repository = get())
    }

    factory<Store<ForumDetailState>>(FORUM_DETAIL_STORE) {
        ForumDetailStoreProvider.create(repository = get())
    }

    single { ManBoLearHubViewModel() }

    single {
        HomeViewModel(
            store = get(HOME_STORE)
        )
    }

    factory {
        ForumDetailViewModel(
            store = get(FORUM_DETAIL_STORE)
        )
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