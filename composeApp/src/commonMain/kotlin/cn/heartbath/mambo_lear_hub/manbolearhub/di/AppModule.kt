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
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.login.LoginState
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.login.LoginStoreProvider
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.auth.AuthRepositoryImpl
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.login.LoginRepositoryImpl
import cn.heartbath.mambo_lear_hub.manbolearhub.repository.auth.AuthRepository
import cn.heartbath.mambo_lear_hub.manbolearhub.repository.forumdetail.ForumDetailRepository
import cn.heartbath.mambo_lear_hub.manbolearhub.repository.home.HomeRepository
import cn.heartbath.mambo_lear_hub.manbolearhub.repository.login.LoginRepository
import cn.heartbath.mambo_lear_hub.manbolearhub.utils.auth.InMemoryTokenProvider
import cn.heartbath.mambo_lear_hub.manbolearhub.utils.auth.TokenProvider
import cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.ManBoLearHubViewModel
import cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.forumdetail.ForumDetailViewModel
import cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.home.HomeViewModel
import cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.login.LoginViewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.reduxkotlin.Store

private const val BASE_URL = "https://43.139.98.90/"

val HOME_STORE = named("HOME_STORE")
val FORUM_DETAIL_STORE = named("FORUM_DETAIL_STORE")
val LOGIN_STORE = named("LOGIN_STORE")

val appModule = module {

    // token provider
    single<TokenProvider> { InMemoryTokenProvider() }

    single { createKtorRawClient(tokenProvider = get()) }

    single<NetworkClient> {
        KtorNetworkClient(
            baseUrl = BASE_URL,
            client = get()
        )
    }

    // repositories
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

    single<AuthRepository> {
        AuthRepositoryImpl(
            networkClient = get(),
            tokenProvider = get()
        )
    }

    single<LoginRepository> {
        LoginRepositoryImpl(
            networkClient = get(),
            authRepository = get()
        )
    }

    // stores
    single<Store<HomeState>>(HOME_STORE) {
        HomeStoreProvider.create(repository = get())
    }

    factory<Store<ForumDetailState>>(FORUM_DETAIL_STORE) {
        ForumDetailStoreProvider.create(repository = get())
    }

    single<Store<LoginState>>(LOGIN_STORE) {
        LoginStoreProvider.create(
            loginRepository = get(),
            authRepository = get()
        )
    }

    // viewModels
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

    single {
        LoginViewModel(
            store = get(LOGIN_STORE)
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