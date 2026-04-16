package cn.heartbath.mambo_lear_hub.manbolearhub.model.forumdetail

data class ForumDetailUiModel(
    val header: ForumHeader,
    val selectedTabIndex: Int
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
                favoriteAction = null,
                tabList = emptyList()
            ),
            selectedTabIndex = 0
        )
    }

    data class ForumHeader(
        val forumId: String?,
        val forumName: String,
        val forumIconUrl: String?,
        val todayPostCount: Int?,
        val totalThreadCount: Int?,
        val forumRank: Int?,
        val favoriteAction: FavoriteAction?,
        val tabList: List<ForumTabUiModel>
    )

    data class FavoriteAction(
        val actionUrl: String,
        val formHash: String?,
        val handleKey: String?,
        val displayText: String,
        val countText: String?
    )

    data class ForumTabUiModel(
        val title: String,
        val linkUrl: String
    )
}