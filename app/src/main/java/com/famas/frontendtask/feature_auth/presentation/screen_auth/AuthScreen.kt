package com.famas.frontendtask.feature_auth.presentation.screen_auth

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.famas.frontendtask.core.presentation.util.UiEvent
import com.famas.frontendtask.feature_auth.presentation.components.SignInScreen
import com.famas.frontendtask.feature_auth.presentation.util.AuthEvent
import com.famas.frontendtask.feature_auth.presentation.util.EnterAnimationInterval
import kotlinx.coroutines.flow.collectLatest

@ExperimentalAnimationApi
@Composable
fun AuthScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigate: (String) -> Unit,
    showSnackBar: (String) -> Unit
) {

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true, block = {
        visible = true
        viewModel.uiEventFlow.collectLatest {
            when (it) {
                is UiEvent.OnNavigate -> {
                    navigate(it.route)
                }

                is UiEvent.ShowSnackBar -> {
                    showSnackBar(it.message)
                }
            }
        }
    })

    val state = viewModel.loginState.value
    val lottieEnterTransition = fadeIn(
        animationSpec = tween(1500, EnterAnimationInterval.SECOND.time)
    )

    SignInScreen(
        email = state.email,
        password = state.password,
        isPasswordVisible = state.isPasswordVisible,
        onEmail = { viewModel.onEvent(AuthEvent.OnEmail(it)) },
        onPassword = { viewModel.onEvent(AuthEvent.OnPassword(it)) },
        togglePassVisibility = { viewModel.onEvent(AuthEvent.TogglePassword) },
        onLoginClick = { viewModel.onEvent(AuthEvent.OnLoginClick) }
    )

    if (state.loading) {
        Dialog(onDismissRequest = { /*TODO*/ }) {
            CircularProgressIndicator()
        }
    }
}