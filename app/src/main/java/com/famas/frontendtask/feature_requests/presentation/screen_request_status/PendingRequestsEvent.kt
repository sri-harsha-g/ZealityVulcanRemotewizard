package com.famas.frontendtask.feature_requests.presentation.screen_request_status

sealed class PendingRequestsEvent {
    object OnReject : PendingRequestsEvent()
    object OnAccept : PendingRequestsEvent()
    data class OnReason(val reason: String) : PendingRequestsEvent()
    object OnCancelDialog : PendingRequestsEvent()
    object OnSubmitDialog : PendingRequestsEvent()
}
