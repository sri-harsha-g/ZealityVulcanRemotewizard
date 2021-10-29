package com.famas.frontendtask.feature_requests.presentation.screen_request_status

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.famas.frontendtask.core.ui.theme.SpaceLarge
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSemiLarge
import com.famas.frontendtask.core.ui.theme.SpaceSmall
import com.famas.frontendtask.feature_requests.presentation.components.EmployeeCard

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun PendingRequests() {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = SpaceSemiLarge)
    ) {
        items(10) {
            EmployeeCard(
                modifier = Modifier.padding(vertical = SpaceSmall),
                showButtons = true,
                showUserDetails = true
            )
        }
    }
}