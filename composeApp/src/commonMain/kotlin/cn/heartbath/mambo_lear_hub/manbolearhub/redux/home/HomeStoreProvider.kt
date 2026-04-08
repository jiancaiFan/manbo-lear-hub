package cn.heartbath.mambo_lear_hub.manbolearhub.redux.home

import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel
import cn.heartbath.mambo_lear_hub.manbolearhub.ui.home.CategoryItem
import org.reduxkotlin.Store
import org.reduxkotlin.threadsafe.createThreadSafeStore

object HomeStoreProvider {

    private val initialState = HomeState(
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

    val store: Store<HomeState> = createThreadSafeStore(
        reducer = homeReducer,
        preloadedState = initialState
    )
}