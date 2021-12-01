package com.famas.frontendtask.core.presentation.activity_main

import com.famas.frontendtask.core.presentation.util.BottomNavItem

sealed class MainActivityEvent {
    data class Navigate(val route: String) : MainActivityEvent()
    data class OpenBottomBar(val screen: BottomNavItem, val fromRoute: String?) : MainActivityEvent()
    data class ShowLocPermissionsDialog(val shouldShowRationale: Boolean = false) : MainActivityEvent()
    object CloseLocPermissionsDialog : MainActivityEvent()
    object ShowOpenSettingsText: MainActivityEvent()
    object HideOpenSettingsText: MainActivityEvent()
}