package cn.heartbath.mambo_lear_hub.manbolearhub.redux.home

import cn.heartbath.mambo_lear_hub.manbolearhub.repository.home.HomeRepository
import org.reduxkotlin.Store
import org.reduxkotlin.applyMiddleware
import org.reduxkotlin.threadsafe.createThreadSafeStore

object HomeStoreProvider {

    fun create(repository: HomeRepository): Store<HomeState> {
        val middleware = HomeSideEffect.createHomeMiddleware(repository)

        return createThreadSafeStore(
            reducer = homeReducer,
            preloadedState = HomeState(),
            enhancer = applyMiddleware(middleware)
        )
    }
}