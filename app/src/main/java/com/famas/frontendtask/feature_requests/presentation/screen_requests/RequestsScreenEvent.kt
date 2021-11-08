package com.famas.frontendtask.feature_requests.presentation.screen_requests

import com.famas.frontendtask.feature_requests.presentation.components.RequestButtonType

sealed class RequestsScreenEvent {
    data class OnRequestBtnLtClick(val requestButtonType: RequestButtonType) : RequestsScreenEvent()
    object OnApply : RequestsScreenEvent()
    object OnApprove : RequestsScreenEvent()
}
