package com.famas.frontendtask.feature_auth.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.famas.frontendtask.core.navigation.Screen
import com.famas.frontendtask.core.presentation.util.UiEvent
import com.famas.frontendtask.core.util.Response
import com.famas.frontendtask.feature_auth.domain.use_cases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    val uiEventFlow = MutableSharedFlow<UiEvent>()

    fun onEvent(event: AuthEvent) {
        viewModelScope.launch {
            when(event) {
                is AuthEvent.OnChangeLoginState -> {
                    _loginState.value = event.loginState
                }

                is AuthEvent.OnLoginClick -> {
                    _loginState.value = loginState.value.copy(loading = true)
                    viewModelScope.launch {
                        when(val result = loginUseCase(emailOrUserName = loginState.value.email, password = loginState.value.password)) {
                            is Response.Success -> {
                                _loginState.value = loginState.value.copy(loading = false)
                                Log.d("myTag", "${result.data?.status}")
                            }

                            is Response.Error -> {
                                _loginState.value = loginState.value.copy(loading = false)
                                Log.d("myTag", "${result.message}", )
                            }
                        }
                    }
                    uiEventFlow.emit(UiEvent.OnNavigate(Screen.DashBoard.route))
                }
            }
        }
    }
}