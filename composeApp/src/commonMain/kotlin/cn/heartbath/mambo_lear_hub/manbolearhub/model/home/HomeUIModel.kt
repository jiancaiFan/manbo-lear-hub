package cn.heartbath.mambo_lear_hub.manbolearhub.model.home

data class HomeUIModel(
    val categories: List<CategoryItem>,
    val selectedCategory: Int,
    val categoryDataMap: Map<Int, List<PostItem>>
) {
    companion object {
        val Empty = HomeUIModel(
            categories = emptyList(),
            selectedCategory = 0,
            categoryDataMap = emptyMap()
        )
    }

    data class CategoryItem(
        val name: String,
        val url: String
    )

    data class PostItem(
        val title: String?,               // 帖子标题
        val summary: String?,             // 帖子简介
        val username: String?,            // 发帖人
        val postTime: String?,            // 发帖时间
        val forumName: String?,           // 版块名称
        val readCount: Int?,              // 阅读数
        val replyCount: Int?,             // 回复数
        val imageList: List<String>,      // 图片列表（多图）
        val detailUrl: String?,           // 帖子详情链接
        val avatarUrl: String?            // 用户头像
    )
}