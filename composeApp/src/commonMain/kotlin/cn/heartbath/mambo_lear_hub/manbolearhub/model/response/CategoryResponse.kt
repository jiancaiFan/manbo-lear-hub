package cn.heartbath.mambo_lear_hub.manbolearhub.model.response

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val name: String,
    val url: String
)