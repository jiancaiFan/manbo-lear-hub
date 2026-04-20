package cn.heartbath.mambo_lear_hub.manbolearhub.redux.forumdetail

import cn.heartbath.mambo_lear_hub.manbolearhub.model.forumdetail.ForumDetailUiModel

data class ForumDetailState(
    val forumDetailUIModel: ForumDetailUiModel = ForumDetailUiModel.Empty,
    val isForumLoading: Boolean = false,
    val isThreadsLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)