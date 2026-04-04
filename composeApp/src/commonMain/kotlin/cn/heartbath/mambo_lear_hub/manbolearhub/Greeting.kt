package cn.heartbath.mambo_lear_hub.manbolearhub

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}