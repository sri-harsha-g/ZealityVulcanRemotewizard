package com.famas.frontendtask.feature_auth.presentation.screen_auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.famas.frontendtask.core.illustrations.UserAuthAnimation
import com.famas.frontendtask.core.presentation.util.UiEvent
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSemiLarge
import com.famas.frontendtask.feature_auth.presentation.util.AuthEvent.*
import com.famas.frontendtask.feature_auth.presentation.components.AnimEnterText
import com.famas.frontendtask.feature_auth.presentation.components.AnimLoginLt
import com.famas.frontendtask.feature_auth.presentation.util.EnterAnimationInterval
import com.famas.frontendtask.feature_auth.presentation.screen_auth.LoginViewModel
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(SpaceSemiLarge)
    ) {
        item {
            AnimEnterText(visible)
            Spacer(modifier = Modifier.height(SpaceMedium))
            AnimatedVisibility(visible = visible, enter = lottieEnterTransition) {
                UserAuthAnimation(modifier = Modifier.fillParentMaxHeight(0.4f))
            }
            Spacer(modifier = Modifier.height(SpaceMedium))
            AnimLoginLt(
                visible,
                loginState = state,
                setLoginState = { viewModel.onEvent(OnChangeLoginState(it)) }
            ) {
                viewModel.onEvent(OnLoginClick)
            }
        }
    }

    if (state.loading) {
        Dialog(onDismissRequest = { /*TODO*/ }) {
            CircularProgressIndicator()
        }
    }
}