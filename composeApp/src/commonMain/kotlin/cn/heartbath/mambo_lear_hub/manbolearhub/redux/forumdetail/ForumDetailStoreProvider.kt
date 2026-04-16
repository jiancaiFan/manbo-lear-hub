package cn.heartbath.mambo_lear_hub.manbolearhub.redux.forumdetail

import cn.heartbath.mambo_lear_hub.manbolearhub.repository.forumdetail.ForumDetailRepository
import org.reduxkotlin.Store
import org.reduxkotlin.applyMiddleware
import org.reduxkotlin.threadsafe.createThreadSafeStore

object ForumDetailStoreProvider {
    fun create(repository: ForumDetailRepository): Store<ForumDetailState> {
        val middleware = ForumDetailSideEffect.createForumDetailMiddleware(repository)

        return createThreadSafeStore(
            reducer = forumDetailReducer,
            preloadedState = ForumDetailState(),
            enhancer = applyMiddleware(middleware)
        )
    }
}