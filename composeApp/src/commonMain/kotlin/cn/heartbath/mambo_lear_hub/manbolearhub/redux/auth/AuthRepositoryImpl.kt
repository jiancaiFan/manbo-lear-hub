package cn.heartbath.mambo_lear_hub.manbolearhub.redux.auth

import cn.heartbath.mambo_lear_hub.manbolearhub.model.response.TokenResponse
import cn.heartbath.mambo_lear_hub.manbolearhub.network.NetworkClient
import cn.heartbath.mambo_lear_hub.manbolearhub.repository.auth.AuthRepository
import cn.heartbath.mambo_lear_hub.manbolearhub.utils.auth.TokenProvider
import kotlinx.serialization.json.Json

class AuthRepositoryImpl(
    private val networkClient: NetworkClient,
    private val tokenProvider: TokenProvider,
    private val json: Json = Json { ignoreUnknownKeys = true }
) : AuthRepository {

    override suspend fun refreshToken(): String {
        val raw = networkClient.post("/api/restful/?/token", "{}")
        val resp = json.decodeFromString(TokenResponse.serializer(), raw)
        require(resp.ret == 0) { "获取 token 失败: $raw" }
        tokenProvider.setToken(resp.token)
        return resp.token
    }

    override fun currentToken(): String? = tokenProvider.getToken()

    override fun clearToken() {
        tokenProvider.setToken(null)
    }
}