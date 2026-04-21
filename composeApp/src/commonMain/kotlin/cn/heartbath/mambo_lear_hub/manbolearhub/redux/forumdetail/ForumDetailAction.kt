package cn.heartbath.mambo_lear_hub.manbolearhub.redux.forumdetail

import cn.heartbath.mambo_lear_hub.manbolearhub.model.forumdetail.ForumDetailUiModel

sealed class ForumDetailAction {
    data class ForumDetailFetch(val path: String) : ForumDetailAction()
    data object ForumDetailLoading : ForumDetailAction()
    data class ForumDetailLoad(val header: ForumDetailUiModel.ForumHeader) : ForumDetailAction()
    data class ForumDetailError(val message: String) : ForumDetailAction()

    data class ForumTabSelect(val position: Int) : ForumDetailAction()
    data object ForumDetailReset : ForumDetailAction()
    data class ForumThreadsFetch(val position: Int) : ForumDetailAction()
    data class ForumThreadsLoading(val position: Int) : ForumDetailAction()

    data class ForumThreadsLoad(
        val position: Int,
        val list: List<ForumDetailUiModel.ForumThreadItem>
    ) : ForumDetailAction()
    data class ForumThreadsError(val position: Int, val message: String) : ForumDetailAction()
}