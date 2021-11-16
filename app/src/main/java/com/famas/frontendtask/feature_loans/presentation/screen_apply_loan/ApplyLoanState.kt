package com.famas.frontendtask.feature_loans.presentation.screen_apply_loan

import com.famas.frontendtask.core.presentation.compose_states.DropDownPickerState

data class ApplyLoanState(
    val loading: Boolean = false,
    val amount: String? = null,
    val applyLoanDropDown: DropDownPickerState<LoanType> = DropDownPickerState(list = LoanType.values().toList())
)