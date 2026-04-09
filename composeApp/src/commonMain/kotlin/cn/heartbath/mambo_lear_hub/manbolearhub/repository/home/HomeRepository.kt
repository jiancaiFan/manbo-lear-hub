package cn.heartbath.mambo_lear_hub.manbolearhub.repository.home

interface HomeRepository {
    suspend fun fetchCategoryData(position: Int): List<String>
}