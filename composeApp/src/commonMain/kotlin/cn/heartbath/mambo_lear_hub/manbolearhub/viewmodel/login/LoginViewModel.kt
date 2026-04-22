package cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.login

import androidx.lifecycle.ViewModel
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.login.LoginAction
import cn.heartbath.mambo_lear_hub.manbolearhub.redux.login.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.reduxkotlin.Store

class LoginViewModel(
    private val store: Store<LoginState>
) : ViewModel() {

    private val _loginState = MutableStateFlow(store.state)
    val loginState = _loginState.asStateFlow()
    private val unsubscribe = store.subscribe { _loginState.value = store.state }

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            store.dispatch(LoginAction.LoginError("用户名或密码不能为空"))
            return
        }
        store.dispatch(LoginAction.LoginSubmit(username, password))
    }

    fun logout() {
        store.dispatch(LoginAction.Logout)
    }

    override fun onCleared() {
        unsubscribe()
        super.onCleared()
    }
}