package cn.heartbath.mambo_lear_hub.manbolearhub.redux.forumdetail

import cn.heartbath.mambo_lear_hub.manbolearhub.network.NetworkClient
import cn.heartbath.mambo_lear_hub.manbolearhub.repository.forumdetail.ForumDetailRepository

class ForumDetailRepositoryImpl(
    private val networkClient: NetworkClient,
    private val baseUrl: String
) : ForumDetailRepository {
    override suspend fun fetchForumDetail(path: String): List<String> {
        return emptyList()
    }
}