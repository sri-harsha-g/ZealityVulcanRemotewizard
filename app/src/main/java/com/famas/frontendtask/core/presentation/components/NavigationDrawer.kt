package com.famas.frontendtask.core.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
            Spacer(modifier = Modifier.height(150.dp))
            Text(
                text = "My Profile",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(start = SpaceSemiLarge)
            )
            EmphasisText(
                text = "Employee id",
                modifier = Modifier.padding(start = SpaceSemiLarge)
            )
            Divider(Modifier.padding(vertical = SpaceSemiSmall))
        }

        items(screens) {
            DrawerItem(screen = it, isSelected = it == currentScreen) {
                onItemClick(it)
            }
        }
    }
}

@Composable
fun DrawerItem(screen: Screen, isSelected: Boolean, onItemClick: () -> Unit) {
    val animatedColor =
        animateColorAsState(targetValue = if (isSelected) Orange700 else Color.White)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SpaceSemiLarge, vertical = SpaceSmall)
            .background(
                color = animatedColor.value.copy(alpha = 0.2f),
                shape = QuarterCornerShape
            )
            .clip(QuarterCornerShape)
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