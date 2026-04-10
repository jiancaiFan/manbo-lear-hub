package cn.heartbath.mambo_lear_hub.manbolearhub.redux.home

import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel
import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel.PostItem

sealed interface HomeAction {
    data object HomeCategoriesFetch : HomeAction
    data object HomeCategoriesLoading : HomeAction
    data class HomeCategoriesLoad(val categories: List<HomeUIModel.CategoryItem>) : HomeAction
    data class HomeCategoriesError(val message: String) : HomeAction
    data class HomeCategorySelect(val position: Int, val url: String = "forum.php?mod=guide&view=newthread&mobile=2") : HomeAction
    data class HomeCategoryFetch(val position: Int, val path: String) : HomeAction
    data class HomeCategoryLoading(val position: Int) : HomeAction
    data class HomeCategoryLoad(val position: Int, val data: List<PostItem>) : HomeAction
    data class HomeCategoryError(val position: Int, val message: String) : HomeAction
}