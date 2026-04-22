package cn.heartbath.mambo_lear_hub.manbolearhub.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    @SerialName("ret") val ret: Int,
    @SerialName("token") val token: String,
    @SerialName("expires_in") val expiresIn: Long
)