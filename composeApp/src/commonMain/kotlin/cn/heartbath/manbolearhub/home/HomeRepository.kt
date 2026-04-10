package cn.heartbath.manbolearhub.home

import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel.PostItem
import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel.CategoryItem
import cn.heartbath.manbolearhub.model.home.HomeUIModel

interface HomeRepository {
    suspend fun fetchCategories(): List<HomeUIModel.CategoryItem>
    suspend fun fetchCategoryData(path: String): List<HomeUIModel.PostItem>
}