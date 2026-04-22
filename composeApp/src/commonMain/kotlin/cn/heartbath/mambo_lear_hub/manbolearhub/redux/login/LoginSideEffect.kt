package cn.heartbath.mambo_lear_hub.manbolearhub.redux.login

import cn.heartbath.mambo_lear_hub.manbolearhub.repository.auth.AuthRepository
import cn.heartbath.mambo_lear_hub.manbolearhub.repository.login.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.reduxkotlin.Middleware
import org.reduxkotlin.Store

object LoginSideEffect {

    fun createLoginMiddleware(
        loginRepository: LoginRepository,
        authRepository: AuthRepository,
        scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    ): Middleware<LoginState> = { store: Store<LoginState> ->
        { next ->
            { action ->
                next(action)

                when (action) {
                    is LoginAction.LoginSubmit -> {
                        store.dispatch(LoginAction.LoginLoading)
                        scope.launch {
                            runCatching { loginRepository.login(action.username, action.password) }
                                .onSuccess { raw ->
                                    store.dispatch(LoginAction.LoginSuccess(raw))
                                }
                                .onFailure { e ->
                                    store.dispatch(LoginAction.LoginError(e.message ?: "登录失败"))
                                }
                        }
                    }

                    is LoginAction.Logout -> {
                        authRepository.clearToken()
                    }

                    else -> Unit
                }
            }
        }
    }
}