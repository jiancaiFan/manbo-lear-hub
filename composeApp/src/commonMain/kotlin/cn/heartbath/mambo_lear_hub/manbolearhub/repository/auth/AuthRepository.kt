package cn.heartbath.mambo_lear_hub.manbolearhub.repository.auth

interface AuthRepository {
    suspend fun refreshToken(): String
    fun currentToken(): String?
    fun clearToken()
}