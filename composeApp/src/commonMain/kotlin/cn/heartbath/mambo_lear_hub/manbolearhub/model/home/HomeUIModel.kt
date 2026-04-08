package cn.heartbath.mambo_lear_hub.manbolearhub.model.home

import cn.heartbath.mambo_lear_hub.manbolearhub.ui.home.CategoryItem

data class HomeUIModel(
    val categories: List<CategoryItem>,
    val selectedCategoryId: String,
    val categoryDataMap: Map<String, List<String>>,
) {

    companion object {
        val Empty = HomeUIModel(
            categories = emptyList(),
            selectedCategoryId = "",
            categoryDataMap = emptyMap()
        )
    }
}
