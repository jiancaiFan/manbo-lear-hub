package cn.heartbath.mambo_lear_hub.manbolearhub.repository.forumdetail

interface ForumDetailRepository {
    suspend fun fetchForumDetail(path: String): List<String>
}