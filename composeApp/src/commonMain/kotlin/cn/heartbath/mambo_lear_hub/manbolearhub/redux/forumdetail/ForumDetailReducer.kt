package cn.heartbath.mambo_lear_hub.manbolearhub.redux.forumdetail

val forumDetailReducer: (ForumDetailState, Any) -> ForumDetailState = { state, action ->
    when (action) {
        is ForumDetailAction.ForumDetailLoading -> {
            state.copy(
                isLoading = true,
                isSuccess = false,
                isError = false,
                errorMessage = null
            )
        }

        is ForumDetailAction.ForumDetailLoad -> {
            state.copy(
                forumDetailUIModel = state.forumDetailUIModel.copy(
                    header = action.header
                ),
                isLoading = false,
                isSuccess = true,
                isError = false,
                errorMessage = null
            )
        }

        is ForumDetailAction.ForumDetailError -> {
            state.copy(
                isLoading = false,
                isSuccess = false,
                isError = true,
                errorMessage = action.message
            )
        }

        is ForumDetailAction.ForumTabSelect -> {
            state.copy(
                forumDetailUIModel = state.forumDetailUIModel.copy(
                    selectedTabIndex = action.position
                ),
                isError = false,
                errorMessage = null
            )
        }

        is ForumDetailAction.ForumFavoriteLoading -> {
            state.copy(
                isLoading = true,
                isSuccess = false,
                isError = false,
                errorMessage = null
            )
        }

        is ForumDetailAction.ForumFavoriteResult -> {
            if (action.success) {
                state.copy(
                    isLoading = false,
                    isSuccess = true,
                    isError = false,
                    errorMessage = null
                )
            } else {
                state.copy(
                    isLoading = false,
                    isSuccess = false,
                    isError = true,
                    errorMessage = action.message ?: "收藏失败"
                )
            }
        }

        else -> state
    }
}