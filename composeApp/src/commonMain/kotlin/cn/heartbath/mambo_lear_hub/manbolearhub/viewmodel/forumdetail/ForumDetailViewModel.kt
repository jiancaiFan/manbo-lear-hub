package cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.forumdetail

import androidx.lifecycle.ViewModel
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.forumdetail.ForumDetailState
import org.reduxkotlin.Store

class ForumDetailViewModel(
    private val store: Store<ForumDetailState>
): ViewModel() {
}