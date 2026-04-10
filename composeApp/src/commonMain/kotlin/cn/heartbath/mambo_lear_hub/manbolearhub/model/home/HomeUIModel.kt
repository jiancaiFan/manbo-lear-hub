package cn.heartbath.mambo_lear_hub.manbolearhub.model.home

data class HomeUIModel(
    val categories: List<CategoryItem>,
    val selectedCategory: Int,
    val categoryDataMap: Map<Int, List<String>>
) {
    companion object {
        val Empty = HomeUIModel(
            categories = emptyList(),
            selectedCategory = 0,
            categoryDataMap = emptyMap()
        )
    }

    data class CategoryItem(
        val id: String,
        val title: String
    )
}