package com.famas.frontendtask.feature_requests.presentation.screen_request_status

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.famas.frontendtask.core.presentation.components.EmphasisText
import com.famas.frontendtask.core.ui.theme.SpaceLarge
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSemiLarge
import com.famas.frontendtask.core.ui.theme.SpaceSmall
import com.famas.frontendtask.feature_requests.presentation.components.EmployeeCard

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun PendingRequests() {

    var showDialog by remember { mutableStateOf(false) }
    var reason by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = SpaceSemiLarge)
    ) {
        items(10) {
            EmployeeCard(
                modifier = Modifier.padding(vertical = SpaceSmall),
                showButtons = true,
                showUserDetails = true,
                onAccept = {},
                onReject = {
                    showDialog = true
                }
            )
        }
    }


    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Card {
                Column(modifier = Modifier.padding(SpaceSemiLarge)) {
                    EmphasisText(
                        text = "Please specify the reason",
                        style = MaterialTheme.typography.subtitle1
                    )
                    Spacer(modifier = Modifier.height(SpaceSemiLarge))
                    OutlinedTextField(
                        value = reason,
                        onValueChange = { reason = it },
                        maxLines = 5,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        label = { Text(text = "Reason") }
                    )
                    Spacer(modifier = Modifier.height(SpaceSemiLarge))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        TextButton(onClick = { showDialog = false }) {
                            Text(text = "Cancel")
                        }
                        Button(onClick = { showDialog = false }) {
                            Text(text = "Submit")
                        }
                    }
                }
            }
        }
    }
}