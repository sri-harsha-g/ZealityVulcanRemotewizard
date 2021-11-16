package com.famas.frontendtask.core.util.extensions

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Height Extension
@Composable
fun Int.height() = Spacer(modifier = Modifier.height(this.dp))


// Weight Extension
@Composable
fun Int.width() = Spacer(modifier = Modifier.width(this.dp))

// Rounded corner All Extension
@Composable
fun Int.radius() = RoundedCornerShape(this.dp)

// Rounded corner Only Extension
@Composable
fun Int.radiusOnly(
    topStart: Int,
    topEnd: Int,
    bottomEnd: Int,
    bottomStart: Int
) = RoundedCornerShape(
    topStart = topStart.dp,
    topEnd = topEnd.dp,
    bottomEnd = bottomEnd.dp,
    bottomStart = bottomStart.dp
)

// Integer null check extensions
fun Int?.validate(value: Int = 0): Int {
    return this ?: value
}
