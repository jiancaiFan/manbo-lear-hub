package cn.heartbath.mambo_lear_hub.manbolearhub.redux.login

val loginReducer: (LoginState, Any) -> LoginState = { state, action ->
    when (action) {
        is LoginAction.LoginLoading -> state.copy(
            isLoading = true,
            isSuccess = false,
            isError = false,
            errorMessage = null
        )

        is LoginAction.LoginSuccess -> state.copy(
            isLoading = false,
            isSuccess = true,
            isError = false,
            errorMessage = null,
            loginRaw = action.raw,
            isLoggedIn = true
        )

        is LoginAction.LoginError -> state.copy(
            isLoading = false,
            isSuccess = false,
            isError = true,
            errorMessage = action.message,
            isLoggedIn = false
        )

        is LoginAction.Logout -> state.copy(
            isLoading = false,
            isSuccess = false,
            isError = false,
            errorMessage = null,
            loginRaw = null,
            isLoggedIn = false
        )

        else -> state
    }
}