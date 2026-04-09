package cn.heartbath.mambo_lear_hub.manbolearhub.redux.home

import cn.heartbath.mambo_lear_hub.manbolearhub.repository.home.HomeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.reduxkotlin.Middleware
import org.reduxkotlin.Store

object HomeSideEffect {

    fun createHomeMiddleware(
        repository: HomeRepository,
        scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    ): Middleware<HomeState> = { store: Store<HomeState> ->
        { next ->
            { action ->
                next(action)

                when (action) {
                    is HomeAction.SelectCategory -> {
                        val state = store.state
                        val p = action.position
                        val canLoad =
                            p in state.uiModel.categories.indices &&
                                    !state.uiModel.categoryDataMap.containsKey(p) &&
                                    !state.loadingCategories.contains(p)

                        if (canLoad) {
                            store.dispatch(HomeAction.LoadCategory(p))
                        }
                    }

                    is HomeAction.LoadCategory -> {
                        val p = action.position
                        store.dispatch(HomeAction.LoadCategoryStarted(p))

                        scope.launch {
                            runCatching { repository.fetchCategoryData(p) }
                                .onSuccess { data ->
                                    store.dispatch(HomeAction.LoadCategorySucceeded(p, data))
                                }
                                .onFailure { e ->
                                    store.dispatch(
                                        HomeAction.LoadCategoryFailed(
                                            position = p,
                                            message = e.message ?: "加载失败"
                                        )
                                    )
                                }
                        }
                    }
                }
            }
        }
    }
}