package cn.heartbath.mambo_lear_hub.manbolearhub.repository.home

import cn.heartbath.mambo_lear_hub.manbolearhub.ui.home.CategoryItem

interface HomeRepository {
    suspend fun fetchCategories(): List<CategoryItem>
    suspend fun fetchCategoryData(position: Int): List<String>
}