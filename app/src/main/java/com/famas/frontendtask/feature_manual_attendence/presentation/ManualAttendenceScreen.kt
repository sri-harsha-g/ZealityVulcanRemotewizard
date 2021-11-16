package com.famas.frontendtask.feature_manual_attendence.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.famas.frontendtask.R
import com.famas.frontendtask.core.presentation.components.DateTimePicker
import com.famas.frontendtask.core.presentation.components.DropDown
import com.famas.frontendtask.core.presentation.components.PrimaryButton
import com.famas.frontendtask.core.presentation.util.UiEvent
import com.famas.frontendtask.core.ui.theme.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ManualAttendanceScreen(
    viewModel: ManualAttendanceViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
    navController: NavController
) {
    val state = viewModel.mAttendanceState.value
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

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
                                viewModel.onEvent(MAttendanceEvent.OnRetry)
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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        state = lazyListState
    ) {
        item {
            DropDown(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = SpaceSemiLarge),
                heading = stringResource(R.string.select_department),
                hint = stringResource(R.string.department),
                list = state.departmentsDropDown.list,
                selectedIndex = state.departmentsDropDown.selectedIndex,
                onSelected = { viewModel.onEvent(MAttendanceEvent.OnDepartmentSelected(it)) }
            )
            Spacer(modifier = Modifier.height(SpaceLarge))
            
            DropDown(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = SpaceSemiLarge),
                heading = stringResource(R.string.select_employee),
                hint = stringResource(R.string.employee),
                list = state.employeeDropDown.list,
                selectedIndex = state.employeeDropDown.selectedIndex,
                onSelected = {
                    viewModel.onEvent(MAttendanceEvent.OnEmployeeSelected(it))
                }
            )
            Spacer(modifier = Modifier.height(SpaceLarge))

            DateTimePicker(
                title = stringResource(id = R.string.select_date_time),
                selectedDate = state.selectedDateTime,
                setDate = { viewModel.onEvent(MAttendanceEvent.OnDateTimeSelected(it)) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(SpaceMedium))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                PrimaryButton(
                    text = stringResource(R.string.btn_clock_in_txt),
                    modifier = Modifier.padding(SpaceSemiSmall)
                ) {

                }
                PrimaryButton(
                    text = stringResource(R.string.btn_clock_out_txt),
                    modifier = Modifier.padding(SpaceSemiSmall)
                ) {

                }
            }
            Spacer(modifier = Modifier.height(SpaceMedium))

        }
    }
}