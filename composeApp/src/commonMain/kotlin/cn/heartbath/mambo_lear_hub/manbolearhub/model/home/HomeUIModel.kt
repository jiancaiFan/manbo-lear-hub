package cn.heartbath.mambo_lear_hub.manbolearhub.model.home

import cn.heartbath.mambo_lear_hub.manbolearhub.ui.home.CategoryItem

data class HomeUIModel(
    val categories: List<CategoryItem>,
    val selectedCategory: Int,
    val categoryDataMap: Map<Int, List<String>>,
) {
    companion object {
        val Empty = HomeUIModel(
            categories = emptyList(),
            selectedCategory = 0,
            categoryDataMap = emptyMap()
        )
    }
}