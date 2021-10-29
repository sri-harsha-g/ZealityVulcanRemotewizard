package com.famas.frontendtask.core.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: (Boolean) -> ImageVector, val label: String) {
    object Search: BottomNavItem(route = "Search", icon = { if (it) Icons.Filled.Search else Icons.Outlined.Search}, label = "Search")
    object Notifications: BottomNavItem(route = "Notifications", icon = { if (it) Icons.Filled.Notifications else Icons.Outlined.Notifications}, label = "Notifications")
    object Logout: BottomNavItem(route = "Logout", icon = { if (it) Icons.Filled.Logout else Icons.Outlined.Logout}, label = "Logout")
}
