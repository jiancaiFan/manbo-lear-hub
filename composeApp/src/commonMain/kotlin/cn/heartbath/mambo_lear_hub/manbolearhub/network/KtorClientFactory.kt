package cn.heartbath.mambo_lear_hub.manbolearhub.network

import cn.heartbath.mambo_lear_hub.manbolearhub.utils.auth.ApiAuth
import cn.heartbath.mambo_lear_hub.manbolearhub.utils.auth.TokenProvider
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createKtorRawClient(tokenProvider: TokenProvider): HttpClient {
    return HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    encodeDefaults = true
                }
            )
        }

        install(HttpCookies) {
            storage = AcceptAllCookiesStorage()
        }

        install(DefaultRequest) {
            contentType(ContentType.Application.Json)

            ApiAuth.buildSignHeaders().forEach { (k, v) ->
                headers.append(k, v)
            }

            tokenProvider.getToken()
                ?.takeIf { it.isNotBlank() }
                ?.let { headers.append("token", it) }
        }

        expectSuccess = false
    }
}