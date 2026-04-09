package cn.heartbath.mambo_lear_hub.manbolearhub.redux.home

import cn.heartbath.mambo_lear_hub.manbolearhub.repository.home.HomeRepository
import cn.heartbath.mambo_lear_hub.manbolearhub.ui.home.CategoryItem

class HomeRepositoryImpl : HomeRepository {

    override suspend fun fetchCategories(): List<CategoryItem> {
        // TODO 替换真实接口
        return listOf(
            CategoryItem("1", "推荐"),
            CategoryItem("2", "安卓"),
            CategoryItem("3", "苹果"),
            CategoryItem("4", "数码")
        )
    }

    override suspend fun fetchCategoryData(position: Int): List<String> {
        // TODO 替换真实接口
        return listOf("分类$position-1", "分类$position-2")
    }
}