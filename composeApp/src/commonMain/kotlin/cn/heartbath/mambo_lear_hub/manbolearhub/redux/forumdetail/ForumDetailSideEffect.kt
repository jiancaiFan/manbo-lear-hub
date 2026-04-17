package cn.heartbath.mambo_lear_hub.manbolearhub.redux.forumdetail

import cn.heartbath.mambo_lear_hub.manbolearhub.repository.forumdetail.ForumDetailRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.reduxkotlin.Middleware
import org.reduxkotlin.Store

object ForumDetailSideEffect {

    fun createForumDetailMiddleware(
        repository: ForumDetailRepository,
        scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    ): Middleware<ForumDetailState> = { store: Store<ForumDetailState> ->
        { next ->
            { action ->
                next(action)

                when (action) {
                    is ForumDetailAction.ForumDetailFetch -> {
                        val path = action.path
                        if (path.isNotBlank()) {
                            store.dispatch(ForumDetailAction.ForumDetailLoading)
                            scope.launch {
                                runCatching { repository.fetchForumDetail(path) }
                                    .onSuccess { header ->
                                        store.dispatch(ForumDetailAction.ForumDetailLoad(header))
                                        if (header.tabList.isNotEmpty()) {
                                            store.dispatch(ForumDetailAction.ForumTabSelect(0))
                                        }
                                    }
                                    .onFailure { e ->
                                        store.dispatch(ForumDetailAction.ForumDetailError(e.message ?: "板块详情加载失败"))
                                    }
                            }
                        }
                    }

                    is ForumDetailAction.ForumTabSelect -> {
                        val p = action.position
                        val state = store.state
                        val changed = p != state.forumDetailUIModel.selectedTabIndex
                        if (changed && !state.isLoading) {
                            store.dispatch(ForumDetailAction.ForumThreadsFetch(p))
                        }
                    }

                    is ForumDetailAction.ForumThreadsFetch -> {
                        val p = action.position
                        val path = store.state.forumDetailUIModel.header.tabList.getOrNull(p)?.linkUrl
                        if (!path.isNullOrBlank()) {
                            store.dispatch(ForumDetailAction.ForumDetailLoading)
                            scope.launch {
                                runCatching { repository.fetchForumThreads(path) }
                                    .onSuccess { list ->
                                        store.dispatch(ForumDetailAction.ForumThreadsLoad(list))
                                    }
                                    .onFailure { e ->
                                        store.dispatch(ForumDetailAction.ForumThreadsError(e.message ?: "帖子列表加载失败"))
                                    }
                            }
                        }
                    }

                    is ForumDetailAction.ForumFavoriteToggle -> {
                        val actionUrl = action.action.actionUrl
                        if (actionUrl.isNotBlank()) {
                            store.dispatch(ForumDetailAction.ForumFavoriteLoading)
                            scope.launch {
                                runCatching { repository.favoriteForum(actionUrl) }
                                    .onSuccess {
                                        store.dispatch(ForumDetailAction.ForumFavoriteResult(true, "收藏成功"))
                                    }
                                    .onFailure { e ->
                                        store.dispatch(ForumDetailAction.ForumFavoriteResult(false, e.message ?: "收藏失败"))
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