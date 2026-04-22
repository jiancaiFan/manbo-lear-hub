package cn.heartbath.mambo_lear_hub.manbolearhub.redux.login

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val loginRaw: String? = null,
    val isLoggedIn: Boolean = false
)