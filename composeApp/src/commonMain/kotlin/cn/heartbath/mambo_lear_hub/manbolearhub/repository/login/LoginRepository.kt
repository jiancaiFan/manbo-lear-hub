package cn.heartbath.mambo_lear_hub.manbolearhub.repository.login

interface LoginRepository {
    suspend fun login(username: String, password: String): String
}