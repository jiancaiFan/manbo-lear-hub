package cn.heartbath.mambo_lear_hub.manbolearhub.repository.home

import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel

interface HomeRepository {
    suspend fun fetchCategories(): List<HomeUIModel.CategoryItem>
    suspend fun fetchCategoryData(position: Int): List<String>
}