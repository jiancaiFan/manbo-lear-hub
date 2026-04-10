package cn.heartbath.mambo_lear_hub.manbolearhub.redux.home

import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel.CategoryItem
import cn.heartbath.mambo_lear_hub.manbolearhub.network.NetworkClient
import cn.heartbath.mambo_lear_hub.manbolearhub.repository.home.HomeRepository
import com.fleeksoft.ksoup.Ksoup

class HomeRepositoryImpl(
    private val networkClient: NetworkClient
) : HomeRepository {

    override suspend fun fetchCategories(): List<CategoryItem> {
        val html = networkClient.get("forum.php?mod=guide&view=newthread&mobile=2")
        val doc = Ksoup.parse(html)

        val elements = doc.select(".dhnv a.flex")
        return elements.map {
            CategoryItem(
                name = it.text().trim(),
                url = it.attr("href"),
            )
        }
    }

    override suspend fun fetchCategoryData(position: Int): List<String> {
        val html = networkClient.get("/api/category/content?position=$position") // 实际改成你的页面路径
        val doc = Ksoup.parse(html)

        // 示例：<li class="content-item">...</li>
        return doc.select(".content-item")
            .map { it.text().trim() }
            .filter { it.isNotBlank() }
    }
}