package com.famas.frontendtask.feature_requests.presentation.screen_leave_vacation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.famas.frontendtask.core.presentation.components.DateTimePicker
import com.famas.frontendtask.core.presentation.components.DropDown
import com.famas.frontendtask.core.presentation.components.PrimaryButton
import com.famas.frontendtask.core.presentation.util.UiEvent
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSemiLarge
import com.famas.frontendtask.core.ui.theme.defaultScreenPadding
import com.famas.frontendtask.core.util.extensions.primaryTextStyle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun LeaveVacationRequest(
    viewModel: LeaveVacationViewModel = hiltViewModel(),
    navController: NavController,
    scaffoldState: ScaffoldState
) {

    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val state = viewModel.leaveVacationState.value

    LaunchedEffect(key1 = true, block = {
        viewModel.uiEventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    scope.launch {
                        event.actionLabel?.let {
                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                event.message,
                                it,
                                duration = SnackbarDuration.Indefinite
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                viewModel.onEvent(LeaveVacationEvent.OnRetry)
                            }
                        } ?: kotlin.run {
                            scaffoldState.snackbarHostState.showSnackbar(event.message)
                        }
                    }
                }
                is UiEvent.OnNavigate -> {
                    navController.navigate(event.route)
                }
            }
        }
    })

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .defaultScreenPadding(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        state = lazyListState
    ) {
        item {
            DropDown(
                heading = "Select permission type",
                hint = "permission type",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = SpaceMedium),
                list = state.permissionDDState.list.map { it.name },
                selectedIndex = state.permissionDDState.selectedIndex,
                onSelected = { viewModel.onEvent(LeaveVacationEvent.OnSelectPermType(it)) }
            )

            Spacer(modifier = Modifier.height(SpaceSemiLarge))
            DateTimePicker(
                title = "Select from date and time",
                selectedDate = state.fromDate,
                setDate = { viewModel.onEvent(LeaveVacationEvent.OnSelectFromDate(it)) })

            Spacer(modifier = Modifier.height(SpaceSemiLarge))
            DateTimePicker(
                title = "Select to date and time",
                selectedDate = state.toDate,
                setDate = { viewModel.onEvent(LeaveVacationEvent.OnSelectToDate(it)) })

            Spacer(modifier = Modifier.height(SpaceSemiLarge))
            OutlinedTextField(
                value = state.reason,
                onValueChange = {
                    viewModel.onEvent(LeaveVacationEvent.OnReason(it))
                    if (it.length == 1) {
                        scope.launch {
                            lazyListState.animateScrollToItem(1)
                        }
                    }
                },
                maxLines = 5,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                label = { Text(text = "Reason", style = primaryTextStyle(MaterialTheme.colors.primary)) },
                textStyle = primaryTextStyle()
            )
        }

        item {
            Spacer(modifier = Modifier.height(SpaceSemiLarge))
            PrimaryButton(text = "APPLY", modifier = Modifier.padding(horizontal = SpaceMedium)) {

            }
            Spacer(modifier = Modifier.height(SpaceMedium))
        }
    }
}