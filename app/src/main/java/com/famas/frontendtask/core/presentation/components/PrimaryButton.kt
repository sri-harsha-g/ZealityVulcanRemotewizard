package com.famas.frontendtask.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSemiSmall

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector? = null,
    color: Color = MaterialTheme.colors.primary,
    textColor: Color = MaterialTheme.colors.onPrimary,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceAround,
    shape: Shape = CircleShape,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .defaultMinSize(minWidth = 150.dp, minHeight = 45.dp)
            .clip(shape = shape)
            .background(color)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.button,
            modifier = Modifier.padding(horizontal = SpaceMedium)
        )

        icon?.let {
            Icon(imageVector = it, contentDescription = null, modifier = Modifier.padding(SpaceMedium))
        }
    }
}