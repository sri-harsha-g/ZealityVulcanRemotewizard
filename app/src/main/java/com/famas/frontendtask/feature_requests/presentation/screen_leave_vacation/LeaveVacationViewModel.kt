package com.famas.frontendtask.feature_requests.presentation.screen_leave_vacation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.famas.frontendtask.core.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class LeaveVacationViewModel @Inject constructor(

) : ViewModel() {
    private val _uiEventFlow = MutableSharedFlow<UiEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    private val _leaveVacationState = mutableStateOf(LeaveVacationState())
    val leaveVacationState: State<LeaveVacationState> = _leaveVacationState

    fun onEvent(event: LeaveVacationEvent) {
        when (event) {
            is LeaveVacationEvent.OnSelectFromDate -> {
                _leaveVacationState.value = leaveVacationState.value.copy(fromDate = event.date)
            }

            is LeaveVacationEvent.OnSelectToDate -> {
                _leaveVacationState.value = leaveVacationState.value.copy(toDate = event.date)
            }

            is LeaveVacationEvent.OnSelectPermType -> {
                _leaveVacationState.value = leaveVacationState.value.copy(
                    permissionDDState = leaveVacationState.value.permissionDDState.copy(
                        selectedIndex = event.index
                    )
                )
            }

            is LeaveVacationEvent.OnReason -> {
                _leaveVacationState.value = leaveVacationState.value.copy(
                    reason = event.reason
                )
            }

            is LeaveVacationEvent.OnApply -> {

            }

            is LeaveVacationEvent.OnRetry -> {

            }
        }
    }

}