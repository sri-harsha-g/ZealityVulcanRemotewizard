package com.famas.frontendtask.core.presentation.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import com.famas.frontendtask.core.presentation.util.BottomNavItem
import com.famas.frontendtask.core.presentation.util.BottomNavItem.*
import com.famas.frontendtask.core.ui.theme.SpaceMedium

@Composable
fun CustomBottomBar(currentScreen: String, onItemClick: (BottomNavItem) -> Unit) {

    val bottomNavItems = remember { listOf(Search, Notifications, Logout) }
    var selectedItem by remember { mutableStateOf<BottomNavItem>(Logout) }

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface,
        elevation = SpaceMedium
    ) {
        bottomNavItems.forEach {
            StandardBottomNavItem(
                icon = it.icon,
                contentDescription = it.label,
                selected = selectedItem == it && currentScreen == it.route
            ) {
                selectedItem = it
                onItemClick(it)
            }
        }
    }
}