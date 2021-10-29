package com.famas.frontendtask.core.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.famas.frontendtask.R
import com.famas.frontendtask.core.ui.theme.SpaceSemiSmall

@Composable
fun StandardToolbar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    showBackArrow: Boolean = false,
    navActions: @Composable RowScope.() -> Unit = {},
    onNavigateUp: () -> Unit = {},
    onNavIconClick: () -> Unit
) {
    TopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = {
            if (showBackArrow) {
                IconButton(onClick = {
                    onNavigateUp()
                }, modifier = Modifier.padding(SpaceSemiSmall)) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.back),
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            } else {
                IconButton(
                    onClick = { onNavIconClick() },
                    modifier = Modifier.padding(SpaceSemiSmall)
                ) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "menu")
                }
            }
        },
        actions = navActions,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp,
    )
}