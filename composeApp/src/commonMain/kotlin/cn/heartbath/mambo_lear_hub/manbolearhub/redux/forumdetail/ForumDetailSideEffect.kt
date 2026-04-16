package cn.heartbath.mambo_lear_hub.manbolearhub.redux.forumdetail

import cn.heartbath.mambo_lear_hub.manbolearhub.repository.forumdetail.ForumDetailRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

                    else -> Unit
                }
            }
        }
    }
}