package com.famas.frontendtask.feature_auth.presentation

data class LoginState(
    val loading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false
)