package cn.heartbath.mambo_lear_hub.manbolearhub.utils.auth

import korlibs.crypto.SHA256
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.random.Random
import kotlin.time.Clock

object ApiAuth {
    private const val APP_ID = "16396561"
    private const val SECRET = "KU1NGT1BRT7SZGC7"

    fun buildSignHeaders(): Map<String, String> {
        val nonce = random16()
        val t = Clock.System.now().epochSeconds.toString()
        val sign = sign(nonce, t)

        return mapOf(
            "appid" to APP_ID,
            "nonce" to nonce,
            "t" to t,
            "sign" to sign
        )
    }

    private fun random16(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..16).map { chars[Random.nextInt(chars.length)] }.joinToString("")
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun sign(nonce: String, t: String): String {
        val input = nonce + t + SECRET
        val sha256Hex = SHA256.digest(input.encodeToByteArray()).hexLower
        return Base64.encode(sha256Hex.encodeToByteArray())
    }
}