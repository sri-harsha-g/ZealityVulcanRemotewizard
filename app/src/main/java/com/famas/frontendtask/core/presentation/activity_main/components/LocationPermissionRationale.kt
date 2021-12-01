package com.famas.frontendtask.core.presentation.activity_main.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSmall
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LocationPermissionsRationale(
    shouldShowRationale: Boolean,
    showOpenSettingsText: Boolean,
    setShowOpenSettings: (Boolean) -> Unit,
    onClickContinue: () -> Unit,
    openSettings: () -> Unit,
    onClickOk: () -> Unit
) {
    val coroutine = rememberCoroutineScope()
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(0.99f)
        ) {
            Column(
                modifier = Modifier
                    .padding(SpaceMedium)
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                if (!shouldShowRationale) {
                    Text(
                        text = "This application can't work without location permissions.",
                    )
                } else {
                    Text(text = "You denied location permanently. Please accept it from settings then click continue")
                }
                Text(text = "Please accept the location permissions")
                Spacer(modifier = Modifier.height(SpaceSmall))
                if (shouldShowRationale) {
                    TextButton(
                        onClick = {
                            if (showOpenSettingsText) {
                                openSettings()
                                coroutine.launch {
                                    delay(2000)
                                    setShowOpenSettings(false)
                                }
                            } else {
                                onClickContinue()
                                setShowOpenSettings(true)
                            }
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(text = if (showOpenSettingsText) "Open Settings" else "Continue")
                    }
                } else {
                    TextButton(
                        onClick = {
                            onClickOk()
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Ok")
                    }
                }
            }
        }
    }
}