package cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.home.HomeAction
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.home.HomeState
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.home.HomeStoreProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class HomeViewModel : ViewModel() {

    private val store = HomeStoreProvider.store

    private val _homeState = MutableStateFlow(store.state)
    val homeState: StateFlow<HomeState> = _homeState.asStateFlow()

    private val unsubscribe: () -> Unit = store.subscribe {
        _homeState.value = store.state
    }

    init {
        loadCategoryIfNeeded(store.state.uiModel.selectedCategory)
    }

    fun onCategorySelected(position: Int) {
        val state = store.state
        if (position !in state.uiModel.categories.indices) return
        if (position == state.uiModel.selectedCategory) return

        store.dispatch(HomeAction.SelectCategory(position))
        loadCategoryIfNeeded(position)
    }

    private fun loadCategoryIfNeeded(position: Int) {
        val state = store.state
        if (position !in state.uiModel.categories.indices) return
        if (state.uiModel.categoryDataMap.containsKey(position)) return
        if (state.loadingCategories.contains(position)) return

        viewModelScope.launch {
            store.dispatch(HomeAction.LoadCategoryStarted(position))
            try {
                val data = fetchCategoryData(position)
                store.dispatch(HomeAction.LoadCategorySucceeded(position, data))
            } catch (e: Exception) {
                store.dispatch(
                    HomeAction.LoadCategoryFailed(
                        position = position,
                        message = e.message ?: "加载失败"
                    )
                )
            }
        }
    }

    private suspend fun fetchCategoryData(position: Int): List<String> {
        delay(500.milliseconds)
        return listOf(
            "分类 position=$position 的数据 1",
            "分类 position=$position 的数据 2",
            "分类 position=$position 的数据 3"
        )
    }

    override fun onCleared() {
        unsubscribe()
        super.onCleared()
    }
}