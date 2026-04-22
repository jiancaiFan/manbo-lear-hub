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

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()
    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()
    private val unsubscribe = store.subscribe { _loginState.value = store.state }

    fun onUsernameChanged(newValue: String) {
        _username.value = newValue
    }

    fun onPasswordChanged(newValue: String) {
        _password.value = newValue
    }

    fun onLogin() {
        if (username.value.isBlank() || password.value.isBlank()) {
            store.dispatch(LoginAction.LoginError("用户名或密码不能为空"))
            return
        }
        store.dispatch(LoginAction.LoginSubmit(username.value, password.value))
    }

    fun logout() {
        store.dispatch(LoginAction.Logout)
    }

    override fun onCleared() {
        unsubscribe()
        super.onCleared()
    }
}