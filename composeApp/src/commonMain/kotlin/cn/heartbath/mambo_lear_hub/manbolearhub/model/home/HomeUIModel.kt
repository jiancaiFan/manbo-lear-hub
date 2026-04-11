package cn.heartbath.mambo_lear_hub.manbolearhub.model.home

data class HomeUIModel(
    val categories: List<CategoryItem>,
    val selectedCategory: Int,
    val categoryDataMap: Map<Int, List<PostItem>>,
    val forumCategories: List<ForumCategory>
) {
    companion object {
        val Empty = HomeUIModel(
            categories = emptyList(),
            selectedCategory = 0,
            categoryDataMap = emptyMap(),
            forumCategories = emptyList()
        )
    }

    data class CategoryItem(
        val name: String,
        val url: String
    )

    data class PostItem(
        val title: String?,
        val summary: String?,
        val username: String?,
        val postTime: String?,
        val forumName: String?,
        val readCount: Int?,
        val replyCount: Int?,
        val imageList: List<String>,
        val detailUrl: String?,
        val avatarUrl: String?
    )

    data class ForumCategory(
        val categoryName: String,
        val forumList: List<ForumInfo>
    ) {
        data class ForumInfo(
            val forumName: String,
            val forumUrl: String,
            val iconSvg: String?,
            val todayThreads: Int?,
            val description: String?
        )
    }
}