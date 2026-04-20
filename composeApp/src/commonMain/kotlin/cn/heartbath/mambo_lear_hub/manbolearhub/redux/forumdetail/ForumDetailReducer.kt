package cn.heartbath.mambo_lear_hub.manbolearhub.redux.forumdetail

import cn.heartbath.mambo_lear_hub.manbolearhub.model.forumdetail.ForumDetailUiModel

val forumDetailReducer: (ForumDetailState, Any) -> ForumDetailState = { state, action ->
    when (action) {
        is ForumDetailAction.ForumDetailLoading -> {
            state.copy(
                isForumLoading = true,
                isThreadsLoading = false,
                isSuccess = false,
                isError = false,
                errorMessage = null
            )
        }

        is ForumDetailAction.ForumDetailLoad -> {
            state.copy(
                forumDetailUIModel = state.forumDetailUIModel.copy(
                    header = action.header,
                    selectedTabIndex = 0
                ),
                isForumLoading = false,
                isThreadsLoading = false,
                isSuccess = true,
                isError = false,
                errorMessage = null
            )
        }

        is ForumDetailAction.ForumDetailError -> {
            state.copy(
                isForumLoading = false,
                isThreadsLoading = false,
                isSuccess = false,
                isError = true,
                errorMessage = action.message
            )
        }

        is ForumDetailAction.ForumTabSelect -> {
            val cached = state.forumDetailUIModel.threadDataMap[action.position]
            state.copy(
                forumDetailUIModel = state.forumDetailUIModel.copy(
                    selectedTabIndex = action.position,
                    threadList = cached ?: emptyList()
                ),
                isError = false,
                errorMessage = null
            )
        }

        is ForumDetailAction.ForumDetailReset -> {
            state.copy(
                forumDetailUIModel = ForumDetailUiModel.Empty,
                isForumLoading = false,
                isThreadsLoading = false,
                isSuccess = false,
                isError = false,
                errorMessage = null
            )
        }

        is ForumDetailAction.ForumThreadsLoading -> {
            state.copy(
                isThreadsLoading = true,
                isSuccess = false,
                isError = false,
                errorMessage = null
            )
        }

        is ForumDetailAction.ForumThreadsLoad -> {
            state.copy(
                forumDetailUIModel = state.forumDetailUIModel.copy(
                    threadList = action.list,
                    threadDataMap = state.forumDetailUIModel.threadDataMap
                        .toMutableMap()
                        .apply { this[action.position] = action.list }
                ),
                isForumLoading = false,
                isThreadsLoading = false,
                isSuccess = true,
                isError = false,
                errorMessage = null
            )
        }

        is ForumDetailAction.ForumThreadsError -> {
            state.copy(
                isThreadsLoading = false,
                isSuccess = false,
                isError = true,
                errorMessage = action.message
            )
        }

        else -> state
    }
}