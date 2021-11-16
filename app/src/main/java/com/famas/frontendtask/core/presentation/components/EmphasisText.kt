package com.famas.frontendtask.core.presentation.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle


@Composable
fun EmphasisText(
    text: String,
    modifier: Modifier = Modifier,
    contentAlpha: Float = ContentAlpha.medium,
    style: TextStyle = MaterialTheme.typography.body1,
    containerColor: Color = MaterialTheme.colors.surface
) {
    CompositionLocalProvider(LocalContentAlpha provides contentAlpha) {
        Text(
            text,
            style = style,
            modifier = modifier,
            color = contentColorFor(backgroundColor = containerColor)
        )
    }
}