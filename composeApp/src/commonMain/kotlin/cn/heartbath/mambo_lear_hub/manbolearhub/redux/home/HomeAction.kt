package cn.heartbath.mambo_lear_hub.manbolearhub.redux.home

import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel

sealed interface HomeAction {
    data object HomeCategoriesFetch : HomeAction
    data object HomeCategoriesLoading : HomeAction
    data class HomeCategoriesLoad(val categories: List<HomeUIModel.CategoryItem>) : HomeAction
    data class HomeCategoriesError(val message: String) : HomeAction
    data class HomeCategorySelect(val position: Int) : HomeAction
    data class HomeCategoryFetch(val position: Int) : HomeAction
    data class HomeCategoryLoading(val position: Int) : HomeAction
    data class HomeCategoryLoad(val position: Int, val data: List<String>) : HomeAction
    data class HomeCategoryError(val position: Int, val message: String) : HomeAction
}