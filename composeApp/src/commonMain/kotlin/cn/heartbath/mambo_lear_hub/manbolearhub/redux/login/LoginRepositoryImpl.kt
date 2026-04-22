package cn.heartbath.mambo_lear_hub.manbolearhub.redux.login

import cn.heartbath.mambo_lear_hub.manbolearhub.model.request.login.LoginRequest
import cn.heartbath.mambo_lear_hub.manbolearhub.network.NetworkClient
import cn.heartbath.mambo_lear_hub.manbolearhub.repository.auth.AuthRepository
import cn.heartbath.mambo_lear_hub.manbolearhub.repository.login.LoginRepository
import kotlinx.serialization.json.Json

class LoginRepositoryImpl(
    private val networkClient: NetworkClient,
    private val authRepository: AuthRepository,
    private val json: Json = Json { ignoreUnknownKeys = true }
) : LoginRepository {

    override suspend fun login(username: String, password: String): String {
        if (authRepository.currentToken().isNullOrBlank()) {
            authRepository.refreshToken()
        }

        val body = json.encodeToString(
            LoginRequest.serializer(),
            LoginRequest(username = username, password = password)
        )
        return networkClient.post("/api/restful/?/member/login", body)
    }
}