package cn.heartbath.mambo_lear_hub.manbolearhub.redux.home

import cn.heartbath.mambo_lear_hub.manbolearhub.repository.home.HomeRepository

class HomeRepositoryImpl : HomeRepository {
    override suspend fun fetchCategoryData(position: Int): List<String> {
        // TODO 这里接你真实接口
        return listOf("分类$position-1", "分类$position-2")
    }
}