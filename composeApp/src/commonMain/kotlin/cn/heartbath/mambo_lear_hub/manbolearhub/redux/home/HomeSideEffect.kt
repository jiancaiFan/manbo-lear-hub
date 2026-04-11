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
                    is HomeAction.HomeCategoriesFetch -> {
                        store.dispatch(HomeAction.HomeCategoriesLoading)
                        scope.launch {
                            runCatching { repository.fetchCategories() }
                                .onSuccess { categories ->
                                    store.dispatch(HomeAction.HomeCategoriesLoad(categories))
                                    if (categories.isNotEmpty()) {
                                        store.dispatch(HomeAction.HomeCategorySelect(0))
                                    }
                                }
                                .onFailure { e ->
                                    store.dispatch(HomeAction.HomeCategoriesError(e.message ?: "分类加载失败"))
                                }
                        }
                    }

                    is HomeAction.HomeCategorySelect -> {
                        val p = action.position
                        val state = store.state
                        val notLoaded = !state.uiModel.categoryDataMap.containsKey(p)
                        if (notLoaded && !state.isLoading) {
                            store.dispatch(HomeAction.HomeCategoryFetch(p))
                        }
                    }

                    is HomeAction.HomeCategoryFetch -> {
                        val p = action.position
                        val path = store.state.uiModel.categories.getOrNull(p)?.url
                        if (!path.isNullOrBlank()) {
                            store.dispatch(HomeAction.HomeCategoryLoading)
                            scope.launch {
                                runCatching { repository.fetchCategoryData(path) }
                                    .onSuccess { data ->
                                        store.dispatch(HomeAction.HomeCategoryLoad(p, data))
                                    }
                                    .onFailure { e ->
                                        // ✅ 不再传 position
                                        store.dispatch(HomeAction.HomeCategoryError(e.message ?: "加载失败"))
                                    }
                            }
                        }
                    }

                    else -> Unit
                }
            }
        }
    }
}