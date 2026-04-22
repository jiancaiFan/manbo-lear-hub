package cn.heartbath.mambo_lear_hub.manbolearhub.model.request.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)