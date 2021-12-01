package com.famas.frontendtask.core.presentation.activity_main

data class LocationDialogState(
    val showLocationDialog: Boolean = false,
    val showOpenSettingsText: Boolean = true,
    val isPermissionsDeniedPermanently: Boolean = false
)

