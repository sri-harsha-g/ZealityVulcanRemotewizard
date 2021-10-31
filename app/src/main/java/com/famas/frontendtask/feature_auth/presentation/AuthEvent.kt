package com.famas.frontendtask.feature_auth.presentation

sealed class AuthEvent {
    data class OnChangeLoginState(val loginState: LoginState): AuthEvent()
    object OnLoginClick: AuthEvent()
}
