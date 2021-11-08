package com.famas.frontendtask.feature_requests.presentation.screen_request

import com.famas.frontendtask.feature_requests.presentation.components.RequestButtonType

data class RequestDialogBtnState(
    val showDialog: Boolean = false,
    val dialogFor: RequestButtonType? = null
)