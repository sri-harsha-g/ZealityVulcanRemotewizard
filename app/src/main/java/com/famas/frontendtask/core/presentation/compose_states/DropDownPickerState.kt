package com.famas.frontendtask.core.presentation.compose_states

data class DropDownPickerState<T>(
    val list: List<T>,
    val selectedIndex: Int = 0,
)
