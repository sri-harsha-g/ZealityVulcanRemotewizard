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
fun Int.Height() = Spacer(modifier = Modifier.height(this.dp))


// Weight Extension
@Composable
fun Int.Width() = Spacer(modifier = Modifier.width(this.dp))

// Rounded corner All Extension
@Composable
fun Int.radius() = RoundedCornerShape(this.dp)

// Integer null check extensions
fun Int?.validate(value: Int = 0): Int {
    return this ?: value
}
