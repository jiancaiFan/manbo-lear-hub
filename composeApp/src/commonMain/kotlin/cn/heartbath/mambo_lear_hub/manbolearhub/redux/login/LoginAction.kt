package cn.heartbath.mambo_lear_hub.manbolearhub.redux.login

sealed interface LoginAction {
    data class LoginSubmit(val username: String, val password: String) : LoginAction
    data object LoginLoading : LoginAction
    data class LoginSuccess(val raw: String) : LoginAction
    data class LoginError(val message: String) : LoginAction
    data object Logout : LoginAction
}