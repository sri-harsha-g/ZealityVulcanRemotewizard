package com.famas.frontendtask.feature_auth.presentation.util

sealed class AuthEvent {
    data class OnEmail(val email: String) : AuthEvent()
    data class OnPassword(val password: String) : AuthEvent()
    object TogglePassword : AuthEvent()
    object OnLoginClick : AuthEvent()
}
