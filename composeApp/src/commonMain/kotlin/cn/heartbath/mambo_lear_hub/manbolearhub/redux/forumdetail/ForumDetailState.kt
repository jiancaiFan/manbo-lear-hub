package cn.heartbath.mambo_lear_hub.manbolearhub.redux.forumdetail

import cn.heartbath.mambo_lear_hub.manbolearhub.model.forumdetail.ForumDetailUiModel

data class ForumDetailState(
    val forumDetailUIModel: ForumDetailUiModel = ForumDetailUiModel.Empty,
    val isForumLoading: Boolean = false,
    val loadingTabs: Set<Int> = emptySet(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
) {
    val isThreadsLoading: Boolean
        get() = loadingTabs.isNotEmpty()
}