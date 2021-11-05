package com.famas.frontendtask.core.presentation.util

sealed class UiEvent {
    data class OnNavigate(val route: String): UiEvent()
    data class ShowSnackBar(val message: String, val actionLabel: String? = null): UiEvent()
}
