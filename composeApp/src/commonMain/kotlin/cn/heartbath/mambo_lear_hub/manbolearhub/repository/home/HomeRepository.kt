package cn.heartbath.mambo_lear_hub.manbolearhub.repository.home

import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel.PostItem
import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel.CategoryItem

interface HomeRepository {
    suspend fun fetchCategories(): List<CategoryItem>
    suspend fun fetchCategoryData(path: String): List<PostItem>
}