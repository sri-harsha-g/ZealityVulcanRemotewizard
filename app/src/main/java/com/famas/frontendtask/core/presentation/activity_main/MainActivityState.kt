package com.famas.frontendtask.core.presentation.activity_main

data class MainActivityState(
    val isInDarkTheme: Boolean = false,
    val openedBottomBarFromRoute: String? = null,
    val locationDialogState: LocationDialogState = LocationDialogState()
)