package com.famas.frontendtask.feature_face_auth.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Replay
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CaptureButton(modifier: Modifier = Modifier, retakePhoto: Boolean, enabled: Boolean, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .size(70.dp)
            .background(if (enabled) Color.Red else Color.Gray, shape = CircleShape)
            .clip(CircleShape)
            .border(BorderStroke(4.dp, color = Color.White), CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (retakePhoto) {
            Icon(imageVector = Icons.Default.Replay, contentDescription = null, tint = Color.White, modifier = Modifier.scale(1.2f))
        }
    }
}