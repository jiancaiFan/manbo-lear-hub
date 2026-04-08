package cn.heartbath.mambo_lear_hub.manbolearhub.redux.home

import cn.heartbath.mambo_lear_hub.manbolearhub.ui.home.CategoryItem

data class HomeState(
    val categories: List<CategoryItem> = emptyList(),
    val selectedCategoryId: String = "",
    val categoryDataMap: Map<String, List<String>> = emptyMap(),
    val loadingCategoryIds: Set<String> = emptySet(),
    val errorMessage: String? = null
)