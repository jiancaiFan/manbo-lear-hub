package cn.heartbath.mambo_lear_hub.manbolearhub.repository.home

import cn.heartbath.mambo_lear_hub.manbolearhub.model.forumdetail.HomeUIModel.ForumCategory
import cn.heartbath.mambo_lear_hub.manbolearhub.model.forumdetail.HomeUIModel.PostItem
import cn.heartbath.mambo_lear_hub.manbolearhub.model.forumdetail.HomeUIModel.CategoryItem

interface HomeRepository {
    suspend fun fetchCategories(): List<CategoryItem>
    suspend fun fetchCategoryDetail(path: String): List<PostItem>
    suspend fun fetchForumList(path: String): List<ForumCategory>
}