package cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.forumdetail

import androidx.lifecycle.ViewModel
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.forumdetail.ForumDetailAction
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.forumdetail.ForumDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.reduxkotlin.Store

class ForumDetailViewModel(
    private val store: Store<ForumDetailState>
) : ViewModel() {

    private val _forumDetailState = MutableStateFlow(store.state)
    val forumDetailState = _forumDetailState.asStateFlow()

    private val unsubscribe = store.subscribe { _forumDetailState.value = store.state }

    fun fetchForumDetail(forumId: Int?) {
        if (forumId == null) return
        store.dispatch(ForumDetailAction.ForumDetailReset)
        store.dispatch(
            ForumDetailAction.ForumDetailFetch("forum.php?mod=forumdisplay&fid=$forumId&mobile=2")
        )
    }

    fun onTabSelected(position: Int) {
        val state = store.state
        val tabs = state.forumDetailUIModel.header.tabList
        if (position !in tabs.indices) return

        val selected = state.forumDetailUIModel.selectedTabIndex
        val hasCache = state.forumDetailUIModel.threadDataMap.containsKey(position)

        if (position == selected && hasCache) return

        store.dispatch(ForumDetailAction.ForumTabSelect(position))
    }

    fun onFavoriteClick() {
        val action = store.state.forumDetailUIModel.header.favoriteAction ?: return
        if (action.actionUrl.isBlank()) return
        store.dispatch(ForumDetailAction.ForumFavoriteToggle(action))
    }

    override fun onCleared() {
        unsubscribe()
        super.onCleared()
    }
}