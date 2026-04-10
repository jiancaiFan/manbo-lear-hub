package cn.heartbath.manbolearhub.home

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