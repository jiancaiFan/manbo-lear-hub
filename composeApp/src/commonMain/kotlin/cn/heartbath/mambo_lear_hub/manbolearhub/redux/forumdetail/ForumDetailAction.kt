package cn.heartbath.mambo_lear_hub.manbolearhub.redux.forumdetail

import cn.heartbath.mambo_lear_hub.manbolearhub.model.forumdetail.ForumDetailUiModel

sealed interface ForumDetailAction {

    data class ForumDetailFetch(val path: String) : ForumDetailAction

    data object ForumDetailLoading : ForumDetailAction

    data class ForumDetailLoad(val header: ForumDetailUiModel.ForumHeader) : ForumDetailAction

    data class ForumDetailError(val message: String) : ForumDetailAction

    data class ForumTabSelect(val position: Int, val tab: ForumDetailUiModel.ForumTabUiModel) : ForumDetailAction

    data class ForumFavoriteToggle(val action: ForumDetailUiModel.FavoriteAction) : ForumDetailAction

    data object ForumFavoriteLoading : ForumDetailAction

    data class ForumFavoriteResult(val success: Boolean, val message: String? = null) : ForumDetailAction
}