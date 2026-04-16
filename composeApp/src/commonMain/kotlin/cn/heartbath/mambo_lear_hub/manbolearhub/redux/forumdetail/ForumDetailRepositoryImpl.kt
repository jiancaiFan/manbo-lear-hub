package cn.heartbath.mambo_lear_hub.manbolearhub.redux.forumdetail

import cn.heartbath.mambo_lear_hub.manbolearhub.model.forumdetail.ForumDetailUiModel
import cn.heartbath.mambo_lear_hub.manbolearhub.network.NetworkClient
import cn.heartbath.mambo_lear_hub.manbolearhub.repository.forumdetail.ForumDetailRepository
import com.fleeksoft.ksoup.Ksoup

class ForumDetailRepositoryImpl(
    private val networkClient: NetworkClient,
    private val baseUrl: String
) : ForumDetailRepository {

    override suspend fun fetchForumDetail(path: String): ForumDetailUiModel.ForumHeader {
        val html = networkClient.get(path)
        val normalizedHtml = html.replace("< img", "<img")
        val doc = Ksoup.parse(normalizedHtml, baseUrl)

        fun toAbsUrl(raw: String?): String {
            val href = raw.orEmpty().trim()
            if (href.isBlank()) return ""
            return when {
                href.startsWith("http://") || href.startsWith("https://") -> href
                href.startsWith("//") -> "https:$href"
                href.startsWith("/") -> baseUrl.trimEnd('/') + href
                else -> baseUrl.trimEnd('/') + "/" + href
            }
        }

        fun String.queryParam(name: String): String? {
            val query = substringAfter('?', "")
            if (query.isBlank()) return null
            return query.split("&")
                .asSequence()
                .mapNotNull {
                    val i = it.indexOf('=')
                    if (i <= 0) null else it.substring(0, i) to it.substring(i + 1)
                }
                .firstOrNull { it.first == name }
                ?.second
                ?.takeIf { it.isNotBlank() }
        }

        fun parseStat(statText: String, key: String): Int? {
            val idx = statText.indexOf(key)
            if (idx < 0) return null
            return Regex("""\d+""")
                .find(statText.substring(idx + key.length))
                ?.value
                ?.toIntOrNull()
        }

        // forumName
        val forumName = doc.selectFirst(".forumdisplay-top h2")
            ?.ownText()
            ?.trim()
            ?.takeIf { it.isNotBlank() }
            ?: doc.selectFirst("title")
                ?.text()
                .orEmpty()
                .substringBefore(" - ")
                .trim()

        // forumIcon
        val forumIconUrl = toAbsUrl(
            doc.selectFirst(".forumdisplay-top h2 img")?.attr("src")
        ).ifBlank { null }

        // forumId: 优先 fid，其次收藏链接 id
        val forumId = run {
            val fidHref = doc.selectFirst("#dhnav_li a[href*=\"fid=\"]")?.attr("href").orEmpty()
            Regex("""[?&]fid=(\d+)""").find(fidHref)?.groupValues?.getOrNull(1)
                ?: run {
                    val favHref = doc.selectFirst("#a_favorite[href*=\"id=\"]")?.attr("href").orEmpty()
                    Regex("""[?&]id=(\d+)""").find(favHref)?.groupValues?.getOrNull(1)
                }
        }

        // stats
        val statText = doc.selectFirst(".forumdisplay-top p")?.text().orEmpty()
        val todayPostCount = parseStat(statText, "今日:")
        val totalThreadCount = parseStat(statText, "主题:")
        val forumRank = parseStat(statText, "排名:")

        // favorite
        val favoriteAction = doc.selectFirst("#a_favorite")?.let { a ->
            val actionUrl = toAbsUrl(a.attr("href"))
            if (actionUrl.isBlank()) {
                null
            } else {
                ForumDetailUiModel.FavoriteAction(
                    actionUrl = actionUrl,
                    formHash = actionUrl.queryParam("formhash"),
                    handleKey = actionUrl.queryParam("handlekey"),
                    displayText = a.ownText().trim().ifBlank { "收藏" },
                    countText = a.selectFirst("#number_favorite_num")
                        ?.text()
                        ?.trim()
                        ?.takeIf { it.isNotBlank() }
                )
            }
        }

        // tabs
        val tabList = doc.select("#dhnav_li li a").mapNotNull { a ->
            val title = a.text().trim()
            val linkUrl = toAbsUrl(a.attr("href"))
            if (title.isBlank() || linkUrl.isBlank()) {
                null
            } else {
                ForumDetailUiModel.ForumTabUiModel(
                    title = title,
                    linkUrl = linkUrl
                )
            }
        }

        return ForumDetailUiModel.ForumHeader(
            forumId = forumId,
            forumName = forumName,
            forumIconUrl = forumIconUrl,
            todayPostCount = todayPostCount,
            totalThreadCount = totalThreadCount,
            forumRank = forumRank,
            favoriteAction = favoriteAction,
            tabList = tabList
        )
    }
}