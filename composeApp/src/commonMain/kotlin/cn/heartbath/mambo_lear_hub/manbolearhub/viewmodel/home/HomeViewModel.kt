package cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.home.HomeState
import cn.heartbath.mambo_lear_hub.manbolearhub.ui.home.CategoryItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class HomeViewModel : ViewModel() {

    private val _homeState = MutableStateFlow(
        HomeState(
            uiModel = HomeUIModel.Empty.copy(
                categories = listOf(
                    CategoryItem("1", "推荐"),
                    CategoryItem("2", "安卓"),
                    CategoryItem("3", "苹果"),
                    CategoryItem("4", "数码"),
                    CategoryItem("5", "汽车"),
                    CategoryItem("6", "科技"),
                    CategoryItem("7", "游戏"),
                    CategoryItem("8", "AI"),
                ),
                selectedCategory = 0
            )
        )
    )
    val homeState: StateFlow<HomeState> = _homeState.asStateFlow()

    init {
        loadCategoryIfNeeded(0)
    }

    fun onCategorySelected(position: Int) {
        val categories = _homeState.value.uiModel.categories
        if (position !in categories.indices) return
        if (_homeState.value.uiModel.selectedCategory == position) return

        _homeState.update { state ->
            state.copy(uiModel = state.uiModel.copy(selectedCategory = position))
        }
        loadCategoryIfNeeded(position)
    }

    private fun loadCategoryIfNeeded(position: Int) {
        val state = _homeState.value
        if (position !in state.uiModel.categories.indices) return
        if (state.uiModel.categoryDataMap.containsKey(position)) return
        if (state.loadingCategories.contains(position)) return

        viewModelScope.launch {
            _homeState.update {
                it.copy(
                    loadingCategories = it.loadingCategories + position,
                    errorMessage = null
                )
            }

            try {
                val data = fetchCategoryData(position)
                _homeState.update {
                    it.copy(
                        uiModel = it.uiModel.copy(
                            categoryDataMap = it.uiModel.categoryDataMap + (position to data)
                        ),
                        loadingCategories = it.loadingCategories - position
                    )
                }
            } catch (e: Exception) {
                _homeState.update {
                    it.copy(
                        loadingCategories = it.loadingCategories - position,
                        errorMessage = e.message ?: "加载失败"
                    )
                }
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
}