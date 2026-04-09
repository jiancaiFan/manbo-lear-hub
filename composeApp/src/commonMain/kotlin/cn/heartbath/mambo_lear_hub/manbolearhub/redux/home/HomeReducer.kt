package cn.heartbath.mambo_lear_hub.manbolearhub.redux.home

val homeReducer: (HomeState, Any) -> HomeState = { state, action ->
    when (action) {
        is HomeAction.HomeCategoriesLoading -> {
            state.copy(
                isLoading = true,
                isSuccess = false,
                isError = false,
                errorMessage = null
            )
        }

        is HomeAction.HomeCategoriesLoad -> {
            state.copy(
                uiModel = state.uiModel.copy(
                    categories = action.categories,
                    selectedCategory = 0
                ),
                isLoading = false,
                isSuccess = true,
                isError = false,
                errorMessage = null
            )
        }

        is HomeAction.HomeCategoriesError -> {
            state.copy(
                isLoading = false,
                isSuccess = false,
                isError = true,
                errorMessage = action.message
            )
        }

        is HomeAction.HomeCategorySelect -> {
            state.copy(
                uiModel = state.uiModel.copy(selectedCategory = action.position),
                isError = false,
                errorMessage = null
            )
        }

        is HomeAction.HomeCategoryLoading -> {
            state.copy(isLoading = true, isSuccess = false, isError = false, errorMessage = null)
        }

        is HomeAction.HomeCategoryLoad -> {
            state.copy(
                uiModel = state.uiModel.copy(
                    categoryDataMap = state.uiModel.categoryDataMap + (action.position to action.data)
                ),
                isLoading = false,
                isSuccess = true,
                isError = false,
                errorMessage = null
            )
        }

        is HomeAction.HomeCategoryError -> {
            state.copy(isLoading = false, isSuccess = false, isError = true, errorMessage = action.message)
        }

        else -> state
    }
}