package com.famas.frontendtask.feature_requests.presentation.screen_ot_swipe_late

import com.famas.frontendtask.core.presentation.compose_states.DropDownPickerState
import com.famas.frontendtask.feature_requests.domain.model.PermissionType

data class OTSwipeState(
    val selectedDate: String? = null,
    val permissionDDState: DropDownPickerState<PermissionType> = DropDownPickerState(
        list = listOf(
            PermissionType.OT, PermissionType.Swipe, PermissionType.Late
        )
    ),
    val hoursPicker: DropDownPickerState<Int> = DropDownPickerState(list = (0..10).toList()),
    val minutesPicker: DropDownPickerState<Int> = DropDownPickerState(list = (0..55).filter { it % 5 == 0 }),
    val reason: String = ""
)
