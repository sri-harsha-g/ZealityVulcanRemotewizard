package com.famas.frontendtask.core.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.famas.frontendtask.core.navigation.Screen
import com.famas.frontendtask.core.ui.theme.*

@Composable
fun NavigationDrawer(
    screens: List<Screen>,
    currentScreen: Screen,
    onItemClick: (Screen) -> Unit
) {
    LazyColumn {
        item {
            Spacer(modifier = Modifier.height(SpaceMedium))
            Text(
                text = "Venkatesh Paithireddy",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(start = SpaceSemiLarge)
            )
            Text(
                text = "1234567890",
                modifier = Modifier.padding(start = SpaceSemiLarge),
                style = MaterialTheme.typography.caption,
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            Divider(Modifier.padding(vertical = SpaceSemiSmall))
            Spacer(modifier = Modifier.height(SpaceMedium))
        }

        items(screens) {
            DrawerItem(screen = it, isSelected = it == currentScreen) {
                onItemClick(it)
            }
        }

        item {
            Spacer(modifier = Modifier.height(SpaceMedium))
        }
    }
}

@Composable
fun DrawerItem(screen: Screen, isSelected: Boolean, onItemClick: () -> Unit) {
    val animatedColor =
        animateColorAsState(targetValue = if (isSelected) MaterialTheme.colors.primary.copy(alpha = 0.2f) else MaterialTheme.colors.surface)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SpaceSemiLarge, vertical = SpaceSmall)
            .background(
                color = animatedColor.value.copy(alpha = 0.2f),
                shape = DefaultShape
            )
            .clip(DefaultShape)
            .clickable { onItemClick() }
            .padding(SpaceMedium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        screen.icon(isSelected)?.let {
            Icon(
                imageVector = it,
                contentDescription = screen.title,
                tint = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
            )
        }
        Text(
            text = screen.title,
            modifier = Modifier.padding(start = SpaceMedium),
            style = MaterialTheme.typography.subtitle1
        )
    }
}