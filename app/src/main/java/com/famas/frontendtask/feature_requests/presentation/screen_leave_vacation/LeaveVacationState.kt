package com.famas.frontendtask.feature_requests.presentation.screen_leave_vacation

import com.famas.frontendtask.core.presentation.compose_states.DropDownPickerState
import com.famas.frontendtask.feature_requests.domain.model.PermissionType

data class LeaveVacationState(
    val loading: Boolean = false,
    val permissionDDState: DropDownPickerState<PermissionType> = DropDownPickerState(
        list = listOf(
            PermissionType.Leave,
            PermissionType.Vacation
        )
    ),
    val fromDate: String? = null,
    val toDate: String? = null,
    val reason: String = ""
)
