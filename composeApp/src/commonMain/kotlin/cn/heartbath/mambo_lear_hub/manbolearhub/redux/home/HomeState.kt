package cn.heartbath.mambo_lear_hub.manbolearhub.redux.home

import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel

data class HomeState(
    val uiModel: HomeUIModel = HomeUIModel.Empty,
    val loadingCategories: Set<Int> = emptySet(),
    val errorMessage: String? = null
)