package cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.home

import androidx.lifecycle.ViewModel
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.home.HomeAction
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.home.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.reduxkotlin.Store

class HomeViewModel(
    private val store: Store<HomeState>
) : ViewModel() {

    private val _homeState = MutableStateFlow(store.state)
    val homeState = _homeState.asStateFlow()

    private val unsubscribe = store.subscribe { _homeState.value = store.state }

    init {
        store.dispatch(HomeAction.HomeCategoriesFetch)
    }

    fun onCategorySelected(position: Int) {
        val categories = store.state.uiModel.categories
        if (position !in categories.indices) return
        if (position == store.state.uiModel.selectedCategory) return
        store.dispatch(HomeAction.HomeCategorySelect(position))
    }

    override fun onCleared() {
        unsubscribe()
        super.onCleared()
    }
}