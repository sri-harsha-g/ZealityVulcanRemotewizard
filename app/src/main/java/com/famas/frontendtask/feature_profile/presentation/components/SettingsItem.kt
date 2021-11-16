package com.famas.frontendtask.feature_profile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ModeNight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.famas.frontendtask.core.ui.theme.SpaceSemiSmall

@Composable
fun DarkMode(
    checked: Boolean,
    onCheck: (Boolean) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary.copy(0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ModeNight,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
        }

        Text(text = "Dark mode", style = MaterialTheme.typography.subtitle1, color = MaterialTheme.colors.onSurface)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = if (checked) "On" else "Off", color = MaterialTheme.colors.onSurface)
            Switch(
                checked = checked,
                onCheckedChange = onCheck,
                modifier = Modifier.padding(horizontal = SpaceSemiSmall),
                colors = SwitchDefaults.colors(
                    checkedTrackColor = MaterialTheme.colors.primary,
                    checkedThumbColor = MaterialTheme.colors.primary
                )
            )
        }
    }
}