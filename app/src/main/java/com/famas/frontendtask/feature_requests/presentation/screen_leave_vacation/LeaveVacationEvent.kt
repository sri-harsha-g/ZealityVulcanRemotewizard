package com.famas.frontendtask.feature_requests.presentation.screen_leave_vacation

sealed class LeaveVacationEvent {
    data class OnSelectFromDate(val date: String) : LeaveVacationEvent()
    data class OnSelectToDate(val date: String) : LeaveVacationEvent()
    data class OnSelectPermType(val index: Int) : LeaveVacationEvent()
    data class OnReason(val reason: String) : LeaveVacationEvent()
    object OnApply : LeaveVacationEvent()
    object OnRetry : LeaveVacationEvent()
}
