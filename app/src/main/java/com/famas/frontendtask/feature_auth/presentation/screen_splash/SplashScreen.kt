package com.famas.frontendtask.feature_auth.presentation.screen_splash

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.famas.frontendtask.core.illustrations.UpdateAnimation
import com.famas.frontendtask.core.navigation.Screen
import com.famas.frontendtask.core.ui.theme.SpaceLarge
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.util.Constants
import com.famas.frontendtask.core.util.Constants.IS_IN_DARK
import com.famas.frontendtask.core.util.extensions.primaryTextStyle

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit,
) {

    val state = viewModel.splashState.value

    LaunchedEffect(key1 = state, block = {
        if (state.loading) return@LaunchedEffect

        if (!state.isUpdateAvailable) {
            state.userId?.let { id ->
                Log.d("myTag", "user id in splash screen: $id")
                state.isInDarkTheme?.let {
                    Log.d("myTag", "is dark: $it")
                    onNavigate(Screen.DashBoard.getRoute("$id?$IS_IN_DARK=$it"))
                }
            } ?: onNavigate(Constants.AUTH_SCREEN)
        }
    })

    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Splash screen", modifier = Modifier.align(Alignment.Center), style = primaryTextStyle())
        if (state.loading) {
            CircularProgressIndicator(modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = SpaceLarge))
        }
    }

    if (state.isUpdateAvailable) {
        Dialog(onDismissRequest = {  }, properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)) {
            Card(modifier = Modifier.fillMaxWidth(0.99f)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(SpaceMedium)) {
                    Text(text = "Please update the app to continue", style = primaryTextStyle())
                    UpdateAnimation(modifier = Modifier.height(100.dp))
                    Spacer(modifier = Modifier.height(SpaceMedium))
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "UPDATE NOW")
                    }
                }
            }
        }
    }
}