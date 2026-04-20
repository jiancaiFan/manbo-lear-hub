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
        val currentState = store.state
        val tabList = currentState.forumDetailUIModel.header.tabList
        if (position !in tabList.indices) return

        val selectedTabIndex = currentState.forumDetailUIModel.selectedTabIndex
        val hasCache = currentState.forumDetailUIModel.threadDataMap.containsKey(position)

        if (position == selectedTabIndex && hasCache) return

        store.dispatch(ForumDetailAction.ForumTabSelect(position))
    }

    fun onFavoriteClick() {

    }

    override fun onCleared() {
        unsubscribe()
        super.onCleared()
    }
}