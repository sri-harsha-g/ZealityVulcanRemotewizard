package com.famas.frontendtask.feature_manual_attendence.presentation

sealed class MAttendanceEvent {
    data class OnDateTimeSelected(val dateTime: String): MAttendanceEvent()
    data class OnDepartmentSelected(val index: Int): MAttendanceEvent()
    data class OnEmployeeSelected(val index: Int): MAttendanceEvent()
    object OnClockIn: MAttendanceEvent()
    object OnClockOut: MAttendanceEvent()
    object OnRetry : MAttendanceEvent()
}