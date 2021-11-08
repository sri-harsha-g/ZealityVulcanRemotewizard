package com.famas.frontendtask.feature_requests.presentation.screen_request_status

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.famas.frontendtask.core.presentation.components.EmphasisText
import com.famas.frontendtask.core.presentation.util.UiEvent
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSemiLarge
import com.famas.frontendtask.core.ui.theme.SpaceSmall
import com.famas.frontendtask.feature_requests.presentation.components.EmployeeCard
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun PendingRequests(
    viewModel: PendingRequestsViewModel = hiltViewModel()
) {
    val state = viewModel.pendingRequestsState.value

    LaunchedEffect(key1 = Unit, block = {
        viewModel.uiEventFlow.collectLatest {
            when (it) {
                is UiEvent.OnNavigate -> {

                }

                is UiEvent.ShowSnackBar -> {

                }
            }
        }
    })

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
                onAccept = {
                    viewModel.onEvent(PendingRequestsEvent.OnAccept)
                },
                onReject = {
                    viewModel.onEvent(PendingRequestsEvent.OnReject)
                }
            )
        }
    }


    if (state.reasonDialogState.showDialog) {
        Dialog(onDismissRequest = { }) {
            Card(modifier = Modifier.padding(SpaceMedium)) {
                Column(modifier = Modifier.padding(SpaceSemiLarge)) {
                    EmphasisText(
                        text = "Please specify the reason",
                        style = MaterialTheme.typography.subtitle1
                    )
                    Spacer(modifier = Modifier.height(SpaceSemiLarge))
                    OutlinedTextField(
                        value = state.reasonDialogState.reason ?: "",
                        onValueChange = { viewModel.onEvent(PendingRequestsEvent.OnReason(it)) },
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
                        TextButton(onClick = { viewModel.onEvent(PendingRequestsEvent.OnCancelDialog) }) {
                            Text(text = "Cancel")
                        }
                        Button(onClick = { viewModel.onEvent(PendingRequestsEvent.OnSubmitDialog) }) {
                            Text(text = "Submit")
                        }
                    }
                }
            }
        }
    }
}