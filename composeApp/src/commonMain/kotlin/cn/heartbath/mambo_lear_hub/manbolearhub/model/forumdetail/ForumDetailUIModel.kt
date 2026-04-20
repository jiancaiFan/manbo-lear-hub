package cn.heartbath.mambo_lear_hub.manbolearhub.model.forumdetail

data class ForumDetailUiModel(
    val header: ForumHeader,
    val selectedTabIndex: Int,
    val threadList: List<ForumThreadItem>,
    val threadDataMap: Map<Int, List<ForumThreadItem>>
) {
    companion object {
        val Empty = ForumDetailUiModel(
            header = ForumHeader(
                forumId = null,
                forumName = "",
                forumIconUrl = "",
                todayPostCount = 0,
                totalThreadCount = 0,
                forumRank = 0,
                tabList = emptyList()
            ),
            selectedTabIndex = 0,
            threadList = emptyList(),
            threadDataMap = emptyMap()
        )
    }

    data class ForumHeader(
        val forumId: String?,
        val forumName: String,
        val forumIconUrl: String?,
        val todayPostCount: Int?,
        val totalThreadCount: Int?,
        val forumRank: Int?,
        val tabList: List<ForumTabUiModel>
    )

    data class ForumTabUiModel(
        val title: String,
        val linkUrl: String
    )

    data class ForumThreadItem(
        val threadId: String?,
        val threadUrl: String,
        val title: String,
        val summary: String?,
        val authorName: String?,
        val authorUrl: String?,
        val authorAvatarUrl: String?,
        val publishTimeText: String?,
        val imageUrls: List<String>,
        val viewCount: Int?,
        val replyCount: Int?
    )
}