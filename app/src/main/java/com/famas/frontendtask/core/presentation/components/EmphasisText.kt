package com.famas.frontendtask.core.presentation.components

import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle


@Composable
fun EmphasisText(
    text: String,
    modifier: Modifier = Modifier,
    contentAlpha: Float = ContentAlpha.medium,
    style: TextStyle = MaterialTheme.typography.body1
) {
    CompositionLocalProvider(LocalContentAlpha provides contentAlpha) {
        Text(
            text,
            style = style,
            modifier = modifier
        )
    }
}