package cn.heartbath.mambo_lear_hub.manbolearhub

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform