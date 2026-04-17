package cn.heartbath.mambo_lear_hub.manbolearhub.repository.forumdetail

import cn.heartbath.mambo_lear_hub.manbolearhub.model.forumdetail.ForumDetailUiModel
import cn.heartbath.mambo_lear_hub.manbolearhub.model.forumdetail.ForumDetailUiModel.ForumHeader

interface ForumDetailRepository {
    suspend fun fetchForumDetail(path: String): ForumHeader
    suspend fun fetchForumThreads(path: String): List<ForumDetailUiModel.ForumThreadItem>
    suspend fun favoriteForum(actionUrl: String)
}