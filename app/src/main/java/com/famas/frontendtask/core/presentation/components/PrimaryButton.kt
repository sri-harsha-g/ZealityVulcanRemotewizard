package com.famas.frontendtask.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.famas.frontendtask.core.ui.theme.DefaultShape
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSmall
import com.famas.frontendtask.core.util.extensions.boldTextStyle

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector? = null,
    color: Color = MaterialTheme.colors.primary,
    textColor: Color = MaterialTheme.colors.onPrimary,
    textStyle: TextStyle = boldTextStyle(),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceAround,
    shape: Shape = DefaultShape,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .defaultMinSize(minWidth = 140.dp, minHeight = 45.dp)
            .clip(shape = shape)
            .background(color)
            .clickable(enabled = enabled, onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement
    ) {
        Text(
            text = text,
            color = textColor,
            style = textStyle,
            modifier = Modifier.padding(horizontal = SpaceSmall)
        )

        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                modifier = Modifier.padding(SpaceMedium),
                tint = contentColorFor(backgroundColor = MaterialTheme.colors.surface).copy(alpha = 0.8f)
            )
        }
    }
}