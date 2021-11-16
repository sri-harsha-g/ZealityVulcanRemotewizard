package com.famas.frontendtask.feature_auth.presentation.screen_splash

data class SplashState(
    val loading: Boolean = false,
    val isUpdateAvailable: Boolean = false,
    val userId: String? = "",
    val isInDarkTheme: Boolean? = false
)