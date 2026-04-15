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
                            if (p == state.uiModel.categories.lastIndex) {
                                store.dispatch(HomeAction.HomeForumFetch(p))
                            } else {
                                store.dispatch(HomeAction.HomeCategoryFetch(p))
                            }
                        }
                    }

                    is HomeAction.HomeCategoryFetch -> {
                        val p = action.position
                        val path = store.state.uiModel.categories.getOrNull(p)?.url
                        if (!path.isNullOrBlank()) {
                            store.dispatch(HomeAction.HomeCategoryLoading)
                            scope.launch {
                                runCatching { repository.fetchCategoryDetail(path) }
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

                    is HomeAction.HomeForumFetch -> {
                        val p = action.position
                        val path = store.state.uiModel.categories.getOrNull(p)?.url
                        if (!path.isNullOrBlank()) {
                            store.dispatch(HomeAction.HomeForumLoading)
                            scope.launch {
                                runCatching { repository.fetchForumList("forum.php?forumlist=1&mobile=no") }
                                    .onSuccess { data ->
                                        store.dispatch(HomeAction.HomeForumLoad(p, data))
                                    }
                                    .onFailure { e ->
                                        store.dispatch(HomeAction.HomeForumError(e.message ?: "板块加载失败"))
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