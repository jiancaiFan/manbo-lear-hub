package cn.heartbath.mambo_lear_hub.manbolearhub.utils.auth

import kotlin.concurrent.Volatile

interface TokenProvider {
    fun getToken(): String?
    fun setToken(token: String?)
}

class InMemoryTokenProvider : TokenProvider {
    @Volatile
    private var token: String? = null

    override fun getToken(): String? = token
    override fun setToken(token: String?) {
        this.token = token
    }
}