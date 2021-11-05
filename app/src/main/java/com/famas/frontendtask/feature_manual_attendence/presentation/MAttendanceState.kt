package com.famas.frontendtask.feature_manual_attendence.presentation

import com.famas.frontendtask.core.presentation.compose_states.DropDownPickerState

data class MAttendanceState(
    val loading: Boolean = false,
    val selectedDateTime: String? = null,
    val departmentsDropDown: DropDownPickerState<String> = DropDownPickerState(list = listOf("dep1", "dep2", "dep3")),
    val employeeDropDown: DropDownPickerState<String> = DropDownPickerState(list = listOf("emp1", "emp2", "emp3")),
)