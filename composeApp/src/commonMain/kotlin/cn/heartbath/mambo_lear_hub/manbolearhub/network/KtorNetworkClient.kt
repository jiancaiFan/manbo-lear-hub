package cn.heartbath.mambo_lear_hub.manbolearhub.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorNetworkClient(
    private val baseUrl: String,
    private val client: HttpClient
) : NetworkClient {

    override suspend fun get(path: String, headers: Map<String, String>): String {
        return client.get(buildUrl(path)) {
            headers.forEach { (k, v) -> header(k, v) }
        }.body()
    }

    override suspend fun post(path: String, jsonBody: String, headers: Map<String, String>): String {
        return client.post(buildUrl(path)) {
            headers.forEach { (k, v) -> header(k, v) }
            contentType(ContentType.Application.Json)
            setBody(jsonBody)
        }.body()
    }

    private fun buildUrl(path: String): String {
        return if (path.startsWith("http")) path
        else "${baseUrl.trimEnd('/')}/${path.trimStart('/')}"
    }
}