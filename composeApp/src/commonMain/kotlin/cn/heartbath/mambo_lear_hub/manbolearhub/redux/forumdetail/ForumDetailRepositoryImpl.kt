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

            val base = baseUrl.trimEnd('/')

            return when {
                href.startsWith("http://") || href.startsWith("https://") -> href
                href.startsWith("//") -> "https:$href"
                href.startsWith("/") -> base + href
                href.startsWith("./") -> base + "/" + href.removePrefix("./")
                href.startsWith("../") -> base + "/" + href.removePrefix("../")
                else -> base + "/" + href
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

        val forumName = doc.selectFirst(".forumdisplay-top h2")
            ?.ownText()
            ?.trim()
            ?.takeIf { it.isNotBlank() }
            ?: doc.selectFirst(".header h2")
                ?.text()
                ?.trim()
                ?.takeIf { it.isNotBlank() }
            ?: doc.selectFirst("title")
                ?.text()
                .orEmpty()
                .substringBefore(" - ")
                .trim()

        val forumIconUrl = run {
            val raw = sequenceOf(
                doc.selectFirst(".forumdisplay-top h2 img")?.attr("src"),
                doc.selectFirst(".forumdisplay-top img")?.attr("src"),
                doc.selectFirst("meta[property=og:image]")?.attr("content"),
                doc.selectFirst("link[rel=image_src]")?.attr("href")
            ).firstOrNull { !it.isNullOrBlank() }

            val parsed = toAbsUrl(raw)

            val isInvalidPlaceholder = parsed.isBlank() ||
                    parsed.contains("noavatar", ignoreCase = true) ||
                    (parsed.endsWith(".svg", ignoreCase = true) && parsed.contains("avatar", ignoreCase = true))

            if (isInvalidPlaceholder) {
                toAbsUrl("static/image/common/forum_default.png")
            } else {
                parsed
            }
        }.ifBlank { null }

        val forumId = run {
            val fidHref = doc.selectFirst("#dhnav_li a[href*=\"fid=\"]")?.attr("href").orEmpty()
            Regex("""[?&]fid=(\d+)""").find(fidHref)?.groupValues?.getOrNull(1)
                ?: run {
                    val favHref = doc.selectFirst("#a_favorite[href*=\"id=\"]")?.attr("href").orEmpty()
                    Regex("""[?&]id=(\d+)""").find(favHref)?.groupValues?.getOrNull(1)
                }
        }

        val statText = doc.selectFirst(".forumdisplay-top p")?.text().orEmpty()
        val todayPostCount = parseStat(statText, "今日:")
        val totalThreadCount = parseStat(statText, "主题:")
        val forumRank = parseStat(statText, "排名:")

        val tabAnchors = doc.select("#dhnav_li li a").ifEmpty {
            doc.select(".dhnav_box a[href*=\"forumdisplay\"]")
        }

        val tabList = tabAnchors.mapNotNull { a ->
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
        }.distinctBy { it.linkUrl }

        return ForumDetailUiModel.ForumHeader(
            forumId = forumId,
            forumName = forumName,
            forumIconUrl = forumIconUrl,
            todayPostCount = todayPostCount,
            totalThreadCount = totalThreadCount,
            forumRank = forumRank,
            tabList = tabList
        )
    }

    override suspend fun fetchForumThreads(path: String): List<ForumDetailUiModel.ForumThreadItem> {
        val html = networkClient.get(path)
        val doc = Ksoup.parse(html.replace("< img", "<img"), baseUrl)

        fun toAbsUrl(raw: String?): String {
            val href = raw.orEmpty().trim()
            if (href.isBlank()) return ""

            val base = baseUrl.trimEnd('/')

            return when {
                href.startsWith("http://") || href.startsWith("https://") -> href
                href.startsWith("//") -> "https:$href"
                href.startsWith("/") -> base + href
                href.startsWith("./") -> base + "/" + href.removePrefix("./")
                href.startsWith("../") -> base + "/" + href.removePrefix("../")
                else -> base + "/" + href
            }
        }

        fun parseInt(text: String?): Int? =
            text?.trim()?.let { Regex("""\d+""").find(it)?.value?.toIntOrNull() }

        return doc.select(".threadlist_box .threadlist ul > li.list").mapNotNull { li ->
            val threadUrl = toAbsUrl(li.selectFirst("a[href*=\"mod=viewthread\"]")?.attr("href"))
            if (threadUrl.isBlank()) return@mapNotNull null

            val title = li.selectFirst(".threadlist_tit em")?.text()?.trim().orEmpty()
            if (title.isBlank()) return@mapNotNull null

            val tid = Regex("""[?&]tid=(\d+)""").find(threadUrl)?.groupValues?.getOrNull(1)
            val summary = li.selectFirst(".threadlist_mes")?.text()?.trim()?.takeIf { it.isNotBlank() }

            val authorA = li.selectFirst(".threadlist_top .muser h3 a")
            val authorName = authorA?.text()?.trim()?.takeIf { it.isNotBlank() }
            val authorUrl = toAbsUrl(authorA?.attr("href")).ifBlank { null }

            val avatarRaw = li.selectFirst(".threadlist_top a.mimg img")?.attr("data-src")
                ?: li.selectFirst(".threadlist_top a.mimg img")?.attr("src")
            val authorAvatarUrl = toAbsUrl(avatarRaw).ifBlank { null }

            val publishTimeText = li.selectFirst(".threadlist_top .mtime")?.text()?.trim()
                ?.takeIf { it.isNotBlank() }

            val imageUrls = li.select(".threadlist_imgs1 img, .threadlist_imgs img")
                .mapNotNull {
                    val raw = it.attr("data-src").ifBlank { it.attr("src") }
                    toAbsUrl(raw).ifBlank { null }
                }

            val stats = li.select(".threadlist_foot li")
            val viewCount = parseInt(stats.getOrNull(0)?.text())
            val replyCount = parseInt(stats.getOrNull(1)?.text())

            ForumDetailUiModel.ForumThreadItem(
                threadId = tid,
                threadUrl = threadUrl,
                title = title,
                summary = summary,
                authorName = authorName,
                authorUrl = authorUrl,
                authorAvatarUrl = authorAvatarUrl,
                publishTimeText = publishTimeText,
                imageUrls = imageUrls,
                viewCount = viewCount,
                replyCount = replyCount
            )
        }
    }

    override suspend fun favoriteForum(actionUrl: String) {
        val response = networkClient.get(actionUrl)

        val success = response.contains("收藏成功")
                || response.contains("do_success")
                || response.contains("favorite")
                || response.contains("操作成功")

        if (!success) {
            throw IllegalStateException("收藏请求可能失败，请检查登录态、formhash 或接口返回")
        }
    }
}