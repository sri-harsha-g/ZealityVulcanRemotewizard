package com.famas.frontendtask.core.presentation.compose_states

data class DropDownPickerState<T>(
    val list: List<T>,
    val selectedIndex: Int? = null,
)

data class DropDownTextFieldPickerState<T>(
    val fieldValue: String,
    val list: List<T>,
    val selectedIndex: Int? = null,
)

