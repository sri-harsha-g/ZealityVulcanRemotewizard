package com.famas.frontendtask.feature_loans.presentation.screen_apply_loan

sealed class ApplyLoanEvent {
    data class OnEnterAmount(val value: String) : ApplyLoanEvent()
    data class OnSelectLoan(val index: Int) : ApplyLoanEvent()
}
