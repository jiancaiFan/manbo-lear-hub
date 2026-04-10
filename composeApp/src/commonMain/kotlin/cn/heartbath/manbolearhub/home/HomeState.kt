package cn.heartbath.manbolearhub.home

import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel

data class HomeState(
    val uiModel: HomeUIModel = HomeUIModel.Empty,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)