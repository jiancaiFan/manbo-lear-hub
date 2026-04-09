package cn.heartbath.mambo_lear_hub.manbolearhub.redux.home

import cn.heartbath.mambo_lear_hub.manbolearhub.repository.home.HomeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.reduxkotlin.Middleware
import org.reduxkotlin.Store
import kotlin.time.Duration.Companion.milliseconds

object HomeSideEffect {

    private const val MIN_LOADING_MS = 400L

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
                            // 最小展示时长：先等 400ms，再请求（开发调试最稳）
                            delay(MIN_LOADING_MS.milliseconds)

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