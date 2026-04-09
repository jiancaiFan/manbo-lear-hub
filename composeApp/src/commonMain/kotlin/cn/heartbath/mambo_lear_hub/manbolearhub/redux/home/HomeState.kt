package cn.heartbath.mambo_lear_hub.manbolearhub.redux.home

import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel

data class HomeState(
    val uiModel: HomeUIModel = HomeUIModel.Empty,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    // 若你还需要“按分类粒度”加载控制，就保留它
    val loadingCategories: Set<Int> = emptySet()
)