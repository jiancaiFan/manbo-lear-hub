package cn.heartbath.mambo_lear_hub.manbolearhub.network

interface NetworkClient {
    suspend fun get(path: String, headers: Map<String, String> = emptyMap()): String
    suspend fun post(path: String, jsonBody: String, headers: Map<String, String> = emptyMap()): String
}