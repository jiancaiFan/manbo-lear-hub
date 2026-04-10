package cn.heartbath.mambo_lear_hub.manbolearhub.redux.home

import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel.CategoryItem
import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel.PostItem
import cn.heartbath.mambo_lear_hub.manbolearhub.network.NetworkClient
import cn.heartbath.mambo_lear_hub.manbolearhub.repository.home.HomeRepository
import com.fleeksoft.ksoup.Ksoup

class HomeRepositoryImpl(
    private val networkClient: NetworkClient,
    private val baseUrl: String
) : HomeRepository {

    private fun toAbsUrl(url: String): String {
        val u = url.trim()
        if (u.startsWith("http://") || u.startsWith("https://")) return u
        return baseUrl + u.removePrefix("/").removePrefix("./")
    }

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

    override suspend fun fetchCategoryData(path: String): List<PostItem> {
        val html = networkClient.get(path)
        val doc = Ksoup.parse(html)

        return doc.select(".threadlist > ul > li.list").map { item ->
            // 标题
            val title = item.select(".threadlist_tit em").text().trim()

            // 简介
            val summary = item.select(".threadlist_mes").text().trim()

            // 用户名
            val username = item.select(".muser h3 a").text().trim()

            // 时间
            val postTime = item.select(".mtime").text().trim()

            // 版块
            val forumName = item.select(".threadlist_foot a").text().trim().removePrefix("#")

            // 阅读数
            val readCount = item.select(".threadlist_foot li:eq(1)").text().trim().toIntOrNull()

            // 回复数
            val replyCount = item.select(".threadlist_foot li:eq(2)").text().trim().toIntOrNull()

            // 图片列表（改：拼接绝对 URL）
            val imageList = item.select(".threadlist_imgs1 img")
                .map { toAbsUrl(it.attr("src")) }

            // 详情链接（不改）
            val detailUrl = item.select("a[href*=viewthread]").attr("href").trim()

            // 头像（改：优先 data-src，空则 src，并拼接绝对 URL）
            val avatarRaw = item.select(".mimg img").attr("data-src").ifBlank {
                item.select(".mimg img").attr("src")
            }
            val avatarUrl = toAbsUrl(avatarRaw)

            PostItem(
                title = title,
                summary = summary,
                username = username,
                postTime = postTime,
                forumName = forumName,
                readCount = readCount,
                replyCount = replyCount,
                imageList = imageList,
                detailUrl = detailUrl,
                avatarUrl = avatarUrl
            )
        }
    }
}