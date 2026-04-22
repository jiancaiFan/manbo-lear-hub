package cn.heartbath.mambo_lear_hub.manbolearhub.redux.login

import cn.heartbath.mambo_lear_hub.manbolearhub.repository.auth.AuthRepository
import cn.heartbath.mambo_lear_hub.manbolearhub.repository.login.LoginRepository
import org.reduxkotlin.Store
import org.reduxkotlin.applyMiddleware
import org.reduxkotlin.threadsafe.createThreadSafeStore

object LoginStoreProvider {
    fun create(
        loginRepository: LoginRepository,
        authRepository: AuthRepository
    ): Store<LoginState> {

        val middleware = LoginSideEffect.createLoginMiddleware(
            loginRepository = loginRepository,
            authRepository = authRepository
        )

        return createThreadSafeStore(
            reducer = loginReducer,
            preloadedState = LoginState(),
            enhancer = applyMiddleware(middleware)
        )
    }
}