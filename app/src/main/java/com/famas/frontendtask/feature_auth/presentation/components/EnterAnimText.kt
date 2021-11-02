package com.famas.frontendtask.feature_auth.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.famas.frontendtask.feature_auth.presentation.util.EnterAnimationInterval

@ExperimentalAnimationApi
@Composable
fun AnimEnterText(visible: Boolean) {

    val titleEnterTransition = fadeIn(
        animationSpec = tween(1000, EnterAnimationInterval.FIRST.time)
    ) + slideInVertically(
        initialOffsetY = { -100 },
        animationSpec = tween(1000, EnterAnimationInterval.FIRST.time)
    )

    AnimatedVisibility(
        visible = visible,
        enter = titleEnterTransition,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Hello, there!",
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}