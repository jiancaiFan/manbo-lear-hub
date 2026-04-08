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
                ), selectedCategoryId = "1"
            )
        )
    )
    val homeState: StateFlow<HomeState> = _homeState.asStateFlow()

    init {
        loadCategoryIfNeeded("1")
    }

    fun onCategorySelected(categoryId: String) {
        if (_homeState.value.uiModel.selectedCategoryId == categoryId) return

        _homeState.update { it.copy(uiModel = it.uiModel.copy(selectedCategoryId = categoryId)) }
        loadCategoryIfNeeded(categoryId)
    }

    private fun loadCategoryIfNeeded(categoryId: String) {
        val state = _homeState.value
        if (state.uiModel.categoryDataMap.containsKey(categoryId)) return
        if (state.loadingCategoryIds.contains(categoryId)) return

        viewModelScope.launch {
            _homeState.update {
                it.copy(
                    loadingCategoryIds = it.loadingCategoryIds + categoryId,
                    errorMessage = null
                )
            }

            try {
                // TODO 替换成 repository 请求
                val data = fetchCategoryData(categoryId)

                _homeState.update {
                    it.copy(
                        uiModel = it.uiModel.copy(
                            categoryDataMap = it.uiModel.categoryDataMap + (categoryId to data),
                        ),
                        loadingCategoryIds = it.loadingCategoryIds - categoryId
                    )
                }
            } catch (e: Exception) {
                _homeState.update {
                    it.copy(
                        loadingCategoryIds = it.loadingCategoryIds - categoryId,
                        errorMessage = e.message ?: "加载失败"
                    )
                }
            }
        }
    }

    private suspend fun fetchCategoryData(categoryId: String): List<String> {
        delay(500.milliseconds)
        return listOf(
            "分类 $categoryId 的数据 1",
            "分类 $categoryId 的数据 2",
            "分类 $categoryId 的数据 3"
        )
    }
}