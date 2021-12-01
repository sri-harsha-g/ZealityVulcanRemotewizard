package com.famas.frontendtask.feature_manual_attendence.presentation

import com.famas.frontendtask.core.presentation.compose_states.DropDownPickerState
import com.famas.frontendtask.core.presentation.compose_states.DropDownTextFieldPickerState

data class MAttendanceState(
    val loading: Boolean = false,
    val selectedDateTime: String? = null,
    val departmentsDropDown: DropDownPickerState<String> = DropDownPickerState(
        list = listOf(
            "dep1",
            "dep2",
            "dep3"
        )
    ),
    val employeeDropDown: DropDownTextFieldPickerState<String> = DropDownTextFieldPickerState(
        fieldValue = "",
        list = listOf(
            "emp1",
            "emp2",
            "emp3",
            "emp4",
            "emp5",
            "emp6",
            "emp7",
            "emp8",
            "emp9",
            "emp10",
            "emp11",
        )
    )
)