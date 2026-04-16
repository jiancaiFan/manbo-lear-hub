package cn.heartbath.mambo_lear_hub.manbolearhub.model.home

import cn.heartbath.mambo_lear_hub.manbolearhub.model.forumdetail.HomeUIModel

data class HomeUIModel(
    val categories: List<CategoryItem>,
    val selectedCategory: Int,
    val selectedForumCategory: Int,
    val categoryDataMap: Map<Int, List<PostItem>>,
    val forumCategories: List<ForumCategory>
) {
    companion object {
        val Empty = HomeUIModel(
            categories = emptyList(),
            selectedCategory = 0,
            selectedForumCategory = 0,
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
        val forumList: List<ForumItem>
    ) {
        data class ForumItem(
            val forumId: String,         // 版块 ID
            val forumName: String,       // 版块名称
            val forumDescription: String?, // 版块描述
            val forumUrl: String,        // 完整链接
            val iconUrl: String?,        // 版块图标
            val threadCount: Int,     // 主题数
            val postCount: Int,       // 回复数
            val lastPostTime: String?,   // 最后回复时间
            val lastPostAuthor: String?  // 最后回复作者
        )
    }
}