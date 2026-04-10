package cn.heartbath.manbolearhub.viewmodel.home

import androidx.lifecycle.ViewModel
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.home.HomeAction
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.home.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.reduxkotlin.Store

class HomeViewModel(
    private val store: Store<cn.heartbath.manbolearhub.home.HomeState>
) : ViewModel() {

    private val _homeState = MutableStateFlow(store.state)
    val homeState = _homeState.asStateFlow()

    private val unsubscribe = store.subscribe { _homeState.value = store.state }

    init {
        store.dispatch(_root_ide_package_.cn.heartbath.manbolearhub.home.HomeAction.HomeCategoriesFetch)
    }

    fun onCategorySelected(position: Int, url: String) {
        val categories = store.state.uiModel.categories
        if (position !in categories.indices) return
        if (position == store.state.uiModel.selectedCategory) return
        store.dispatch(_root_ide_package_.cn.heartbath.manbolearhub.home.HomeAction.HomeCategorySelect(position, url))
    }

    override fun onCleared() {
        unsubscribe()
        super.onCleared()
    }
}