package cn.heartbath.mambo_lear_hub.manbolearhub.redux.home

sealed interface HomeAction {
    data class SelectCategory(val position: Int) : HomeAction
    data class LoadCategoryStarted(val position: Int) : HomeAction
    data class LoadCategorySucceeded(val position: Int, val data: List<String>) : HomeAction
    data class LoadCategoryFailed(val position: Int, val message: String) : HomeAction
}