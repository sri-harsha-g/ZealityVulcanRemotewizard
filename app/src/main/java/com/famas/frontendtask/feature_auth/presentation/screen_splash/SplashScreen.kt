package com.famas.frontendtask.feature_auth.presentation.screen_splash

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.famas.frontendtask.core.navigation.Screen
import com.famas.frontendtask.core.presentation.util.UiEvent
import com.famas.frontendtask.core.util.Constants
import com.famas.frontendtask.feature_auth.presentation.screen_auth.LoginViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    loginViewModel: SplashViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit,
) {
    LaunchedEffect(key1 = loginViewModel.userId.value, block = {
        loginViewModel.userId.value?.let {
            Log.d("myTag", "user id in splash screen: $it")
            if (it.isNotBlank()) onNavigate(Screen.DashBoard.getRoute(it))
        } ?: onNavigate(Constants.AUTH_SCREEN)
    })

    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Splash screen", modifier = Modifier.align(Alignment.Center))
    }
}