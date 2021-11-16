package com.famas.frontendtask.feature_requests.presentation.screen_requests

data class RequestsScreenState(
    val requestDialogBtnState: RequestDialogBtnState = RequestDialogBtnState(),
    val isAdmin: Boolean = false
)

enum class BottomSheetEvent {
    Expand, Collapse
}