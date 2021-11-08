package com.famas.frontendtask.feature_auth.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.famas.frontendtask.core.presentation.components.PrimaryButton
import com.famas.frontendtask.core.ui.theme.SpaceLarge
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.feature_auth.presentation.util.EnterAnimationInterval
import com.famas.frontendtask.feature_auth.presentation.util.LoginState

@ExperimentalAnimationApi
@Composable
fun AnimLoginLt(
    visible: Boolean,
    loginState: LoginState,
    setLoginState: (LoginState) -> Unit,
    onClickLogin: () -> Unit
) {
    val loginLtTransition = fadeIn(
        animationSpec = tween(1000, EnterAnimationInterval.THIRD.time)
    ) + slideInVertically(
        initialOffsetY = { 100 },
        animationSpec = tween(1000, EnterAnimationInterval.THIRD.time)
    )

    AnimatedVisibility(
        visible = visible,
        enter = loginLtTransition,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = loginState.email,
                onValueChange = { setLoginState(loginState.copy(email = it)) },
                label = {
                    Text(text = "Email")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = SpaceMedium)
            )

            OutlinedTextField(
                value = loginState.password,
                onValueChange = { setLoginState(loginState.copy(password = it)) },
                label = {
                    Text(text = "Password")
                },
                trailingIcon = {
                    IconButton(onClick = { setLoginState(loginState.copy(isPasswordVisible = !loginState.isPasswordVisible)) }) {
                        Icon(
                            imageVector = if (loginState.isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (!loginState.isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = SpaceMedium)
            )

            Spacer(modifier = Modifier.height(SpaceLarge))
            PrimaryButton(text = "Login") {
                onClickLogin()
            }
        }
    }
}