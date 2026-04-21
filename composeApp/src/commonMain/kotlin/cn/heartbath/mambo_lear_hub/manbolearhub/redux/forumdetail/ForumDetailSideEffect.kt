package cn.heartbath.mambo_lear_hub.manbolearhub.redux.forumdetail

import cn.heartbath.mambo_lear_hub.manbolearhub.repository.forumdetail.ForumDetailRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.reduxkotlin.Middleware
import org.reduxkotlin.Store

object ForumDetailSideEffect {

    fun createForumDetailMiddleware(
        repository: ForumDetailRepository,
        scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
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
                                            store.dispatch(ForumDetailAction.ForumThreadsFetch(0)) // 直接拉数据
                                        }
                                    }
                                    .onFailure { e ->
                                        store.dispatch(
                                            ForumDetailAction.ForumDetailError(
                                                e.message ?: "板块详情加载失败"
                                            )
                                        )
                                    }
                            }
                        }
                    }

                    is ForumDetailAction.ForumTabSelect -> {
                        val targetPosition = action.position
                        val state = store.state
                        val tabList = state.forumDetailUIModel.header.tabList
                        val hasCache = state.forumDetailUIModel.threadDataMap.containsKey(targetPosition)

                        if (targetPosition in tabList.indices && !hasCache) {
                            store.dispatch(ForumDetailAction.ForumThreadsFetch(targetPosition))
                        }
                    }

                    is ForumDetailAction.ForumThreadsFetch -> {
                        val targetPosition = action.position
                        val tab = store.state.forumDetailUIModel.header.tabList.getOrNull(targetPosition)
                        val path = tab?.linkUrl

                        if (path.isNullOrBlank()) {
                            store.dispatch(ForumDetailAction.ForumThreadsLoad(targetPosition, emptyList()))
                        } else {
                            store.dispatch(ForumDetailAction.ForumThreadsLoading(targetPosition))
                            scope.launch {
                                runCatching { repository.fetchForumThreads(path) }
                                    .onSuccess { list ->
                                        store.dispatch(ForumDetailAction.ForumThreadsLoad(targetPosition, list))
                                    }
                                    .onFailure { e ->
                                        store.dispatch(
                                            ForumDetailAction.ForumThreadsError(
                                                position = targetPosition,
                                                message = e.message ?: "帖子列表加载失败"
                                            )
                                        )
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