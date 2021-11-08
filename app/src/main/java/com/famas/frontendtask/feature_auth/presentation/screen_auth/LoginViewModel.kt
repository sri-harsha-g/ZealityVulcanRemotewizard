package com.famas.frontendtask.feature_auth.presentation.screen_auth

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.famas.frontendtask.core.data.Response
import com.famas.frontendtask.core.data.ResponseStatus
import com.famas.frontendtask.core.navigation.Screen
import com.famas.frontendtask.core.presentation.util.UiEvent
import com.famas.frontendtask.feature_auth.domain.use_cases.LoginUseCase
import com.famas.frontendtask.feature_auth.domain.use_cases.StoreUserDetails
import com.famas.frontendtask.feature_auth.presentation.util.AuthEvent
import com.famas.frontendtask.feature_auth.presentation.util.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val storeUserDetails: StoreUserDetails
) : ViewModel() {

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    private val _uiEventFlow = MutableSharedFlow<UiEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    fun onEvent(event: AuthEvent) {
        viewModelScope.launch {
            when (event) {
                is AuthEvent.OnChangeLoginState -> {
                    _loginState.value = event.loginState
                }

                is AuthEvent.OnLoginClick -> {
                    _loginState.value = loginState.value.copy(loading = true)
                    viewModelScope.launch {
                        when (val result = loginUseCase(
                            emailOrUserName = loginState.value.email,
                            password = loginState.value.password
                        )) {

                            is Response.Success -> {
                                _loginState.value = loginState.value.copy(loading = false)
                                when (result.source?.status) {
                                    ResponseStatus.Success.id -> {
                                        _uiEventFlow.emit(UiEvent.ShowSnackBar("successfully logged in"))
                                        result.source.data.first().let {
                                            storeUserDetails(
                                                userId = it.userId,
                                                userName = it.userName,
                                                userType = it.userType
                                            )
                                        }
                                        _uiEventFlow.emit(
                                            UiEvent.OnNavigate(
                                                Screen.DashBoard.getRoute(
                                                    result.source.data.first().userId
                                                )
                                            )
                                        )
                                    }

                                    ResponseStatus.Failure.id -> {
                                        _uiEventFlow.emit(UiEvent.ShowSnackBar(result.source.msg))
                                    }

                                    else -> _uiEventFlow.emit(
                                        UiEvent.ShowSnackBar(
                                            result.source?.msg
                                                ?: "An unknown error occurred, please try again"
                                        )
                                    )
                                }
                            }

                            is Response.Error -> {
                                _loginState.value = loginState.value.copy(loading = false)
                                _uiEventFlow.emit(
                                    UiEvent.ShowSnackBar(
                                        result.message
                                            ?: "An unknown error occurred, please try again"
                                    )
                                )
                                Log.d("myTag", "${result.message}")
                            }
                        }
                    }
                }
            }
        }
    }
}