package com.famas.frontendtask.feature_requests.presentation.screen_ot_swipe_late

sealed class OTSwipeEvent {
    data class SetSelectedDate(val date: String): OTSwipeEvent()
    data class SetHour(val hourIndex: Int): OTSwipeEvent()
    data class SetMinute(val minuteIndex: Int): OTSwipeEvent()
    data class SetReason(val reason: String): OTSwipeEvent()
    data class OnSelectPerType(val index: Int) : OTSwipeEvent()

    object OnClickApply: OTSwipeEvent()
}