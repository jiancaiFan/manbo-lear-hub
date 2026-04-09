package cn.heartbath.mambo_lear_hub.manbolearhub.redux.home

val homeReducer: (HomeState, Any) -> HomeState = { state, action ->
    when (action) {
        is HomeAction.SelectCategory -> {
            state.copy(
                uiModel = state.uiModel.copy(selectedCategory = action.position)
            )
        }

        is HomeAction.LoadCategoryStarted -> {
            val newLoading = state.loadingCategories + action.position
            state.copy(
                isLoading = newLoading.isNotEmpty(),
                isSuccess = false,
                isError = false,
                errorMessage = null,
                loadingCategories = newLoading
            )
        }

        is HomeAction.LoadCategorySucceeded -> {
            val newLoading = state.loadingCategories - action.position
            state.copy(
                uiModel = state.uiModel.copy(
                    categoryDataMap = state.uiModel.categoryDataMap + (action.position to action.data)
                ),
                isLoading = newLoading.isNotEmpty(),
                isSuccess = true,
                isError = false,
                errorMessage = null,
                loadingCategories = newLoading
            )
        }

        is HomeAction.LoadCategoryFailed -> {
            val newLoading = state.loadingCategories - action.position
            state.copy(
                isLoading = newLoading.isNotEmpty(),
                isSuccess = false,
                isError = true,
                errorMessage = action.message,
                loadingCategories = newLoading
            )
        }

        else -> state
    }
}