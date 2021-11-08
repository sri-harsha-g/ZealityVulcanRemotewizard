package com.famas.frontendtask.feature_requests.presentation.screen_request_status

data class PendingRequestsState(
    val loading: Boolean = false,
    val reasonDialogState: ReasonDialogState = ReasonDialogState()
)


data class ReasonDialogState(
    val showDialog: Boolean = false,
    val reason: String? = null
)