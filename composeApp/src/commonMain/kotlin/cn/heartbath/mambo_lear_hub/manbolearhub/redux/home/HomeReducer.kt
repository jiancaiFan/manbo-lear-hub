package cn.heartbath.mambo_lear_hub.manbolearhub.redux.home

val homeReducer: (HomeState, Any) -> HomeState = { state, action ->
    when (action) {
        is HomeAction.SelectCategory -> {
            state.copy(
                uiModel = state.uiModel.copy(selectedCategory = action.position)
            )
        }

        is HomeAction.LoadCategoryStarted -> {
            state.copy(
                loadingCategories = state.loadingCategories + action.position,
                errorMessage = null
            )
        }

        is HomeAction.LoadCategorySucceeded -> {
            state.copy(
                uiModel = state.uiModel.copy(
                    categoryDataMap = state.uiModel.categoryDataMap + (action.position to action.data)
                ),
                loadingCategories = state.loadingCategories - action.position
            )
        }

        is HomeAction.LoadCategoryFailed -> {
            state.copy(
                loadingCategories = state.loadingCategories - action.position,
                errorMessage = action.message
            )
        }

        else -> state
    }
}