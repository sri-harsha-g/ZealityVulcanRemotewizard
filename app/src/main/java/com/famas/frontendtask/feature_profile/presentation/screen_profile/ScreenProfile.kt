package com.famas.frontendtask.feature_profile.presentation.screen_profile

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.famas.frontendtask.core.ui.theme.SpaceLarge
import com.famas.frontendtask.core.ui.theme.SpaceSemiLarge
import com.famas.frontendtask.feature_profile.presentation.components.DarkMode
import com.famas.frontendtask.feature_profile.presentation.components.ViewProfileLt


@Composable
fun ScreenProfile(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.profileState.value

    LazyColumn(modifier = Modifier.padding(horizontal = 36.dp)) {
        item {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(vertical = SpaceSemiLarge * 2),
                color = MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.SemiBold
            )
            ViewProfileLt()

            Text(
                text = "Settings",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(vertical = SpaceSemiLarge * 2),
                color = MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.SemiBold
            )
            DarkMode(
                checked = state.isInDarkMode,
                onCheck = { viewModel.onEvent(ProfileScreenEvent.OnCheckChange(it)) }
            )
            Spacer(modifier = Modifier.height(SpaceLarge * 2))
        }
    }
}
