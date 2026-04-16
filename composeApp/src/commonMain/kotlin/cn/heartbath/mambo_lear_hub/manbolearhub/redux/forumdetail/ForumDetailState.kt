package cn.heartbath.mambo_lear_hub.manbolearhub.redux.forumdetail

data class ForumDetailState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)