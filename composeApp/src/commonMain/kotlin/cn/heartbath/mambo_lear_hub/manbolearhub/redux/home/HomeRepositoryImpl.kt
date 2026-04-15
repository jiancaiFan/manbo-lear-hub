package cn.heartbath.mambo_lear_hub.manbolearhub.redux.home

import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel.CategoryItem
import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel.PostItem
import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel.ForumCategory
import cn.heartbath.mambo_lear_hub.manbolearhub.network.NetworkClient
import cn.heartbath.mambo_lear_hub.manbolearhub.repository.home.HomeRepository
import com.fleeksoft.ksoup.Ksoup

class HomeRepositoryImpl(
    private val networkClient: NetworkClient,
    private val baseUrl: String
) : HomeRepository {

    private fun normalizedBaseUrl(): String = baseUrl.trim().trimEnd('/') + "/"

    private fun toAbsUrl(url: String?): String {
        if (url.isNullOrBlank()) return ""

        val raw = url.trim().replace("&amp;", "&")
        val base = normalizedBaseUrl()

        return when {
            raw.startsWith("http://") || raw.startsWith("https://") -> raw
            raw.startsWith("//") -> "http:$raw"
            raw.startsWith("/") -> base.trimEnd('/') + raw
            raw.startsWith("./") -> base + raw.removePrefix("./")
            else -> base + raw
        }
    }

    override suspend fun fetchCategories(): List<CategoryItem> {
        val html = networkClient.get("forum.php?mod=guide&view=newthread&mobile=2")
        val doc = Ksoup.parse(html)

        val excludedNames = setOf("回复", "抢沙发")

        return doc.select(".dhnv a.flex")
            .map {
                CategoryItem(
                    name = it.text().trim(),
                    url = it.attr("href").trim()
                )
            }
            .filter { it.name !in excludedNames }
    }

    override suspend fun fetchCategoryDetail(path: String): List<PostItem> {
        val html = networkClient.get(path)
        val doc = Ksoup.parse(html)

        return doc.select(".threadlist > ul > li.list").map { item ->
            val title = item.select(".threadlist_tit em").text().trim()
            val summary = item.select(".threadlist_mes").text().trim()
            val username = item.select(".muser h3 a").text().trim()
            val postTime = item.select(".mtime").text().trim()
            val forumName = item.select(".threadlist_foot a").text().trim().removePrefix("#")
            val readCount = item.select(".threadlist_foot li:eq(1)").text().trim().toIntOrNull()
            val replyCount = item.select(".threadlist_foot li:eq(2)").text().trim().toIntOrNull()

            val imageList = item
                .select(".threadlist_imgs1 img, .threadlist_imgs img")
                .mapNotNull { img ->
                    val raw = img.attr("src").ifBlank { img.attr("data-src") }.trim()
                    toAbsUrl(raw).ifBlank { null }
                }
                .distinct()

            val detailUrl = item.select("a[href*=viewthread]").attr("href").trim()

            val avatarRaw = item.select(".mimg img").attr("data-src").ifBlank {
                item.select(".mimg img").attr("src")
            }.trim()
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

    override suspend fun fetchForumList(path: String): List<ForumCategory> {
        val html = networkClient.get(path)
        val doc = Ksoup.parse(html)

        return doc.select("div.bm.bmw.cl").mapNotNull { catEl ->

            val categoryName = catEl.select(".bm_h h2 a").text().trim()

            val forums = catEl.select(".fl_tb tr").mapNotNull { tr ->
                val a = tr.select("td h2 a").firstOrNull() ?: return@mapNotNull null
                val name = a.text().trim()
                val url = a.attr("href").trim()
                val fid = Regex("fid=(\\d+)").find(url)?.groupValues?.get(1) ?: return@mapNotNull null

                val icon = toAbsUrl(tr.select("td.fl_icn img").attr("src").trim()).ifBlank { null }
                val desc = tr.select("td p.xg2").text().trim().ifBlank { null }

                val countParts = tr.select(".fl_i").text()
                    .split("/")
                    .map { it.trim().toIntOrNull() ?: 0 }
                val threadCount = countParts.getOrElse(0) { 0 }
                val postCount = countParts.getOrElse(1) { 0 }

                val lastTime = tr.select(".fl_by cite span").text().trim().ifBlank { null }
                val lastAuthor = tr.select(".fl_by cite a").text().trim().ifBlank { null }

                ForumCategory.ForumItem(
                    forumId = fid,
                    forumName = name,
                    iconUrl = icon,
                    forumDescription = desc,
                    forumUrl = url,
                    threadCount = threadCount,
                    postCount = postCount,
                    lastPostTime = lastTime,
                    lastPostAuthor = lastAuthor
                )
            }

            if (forums.isEmpty()) null else ForumCategory(categoryName, forums)
        }
    }
}