package com.famas.frontendtask.core.presentation.activity_main

sealed class MainActivityUiEventFlow {
    data class Navigate(val route: String) : MainActivityUiEventFlow()
    data class OpenBottomBar(val route: String, val fromRoute: String? = null) : MainActivityUiEventFlow()
    data class ShowSnackBar(val message: String) : MainActivityUiEventFlow()
}