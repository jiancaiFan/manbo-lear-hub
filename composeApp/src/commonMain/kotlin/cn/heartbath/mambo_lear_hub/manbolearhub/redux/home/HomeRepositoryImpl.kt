package cn.heartbath.mambo_lear_hub.manbolearhub.redux.home

import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel.CategoryItem
import cn.heartbath.mambo_lear_hub.manbolearhub.model.response.CategoryResponse
import cn.heartbath.mambo_lear_hub.manbolearhub.network.NetworkClient
import cn.heartbath.mambo_lear_hub.manbolearhub.repository.home.HomeRepository
import kotlinx.serialization.json.Json

class HomeRepositoryImpl(
    private val networkClient: NetworkClient
) : HomeRepository {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun fetchCategories(): List<CategoryItem> {
        val raw = networkClient.get("/api/categories")
        val list = json.decodeFromString<List<CategoryResponse>>(raw)
        return list.map { CategoryItem(id = it.id, title = it.title) }
    }

    override suspend fun fetchCategoryData(position: Int): List<String> {
        val raw = networkClient.get("/api/category/content?position=$position")
        return json.decodeFromString<List<String>>(raw)
    }
}