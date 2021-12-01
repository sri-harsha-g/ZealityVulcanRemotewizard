package com.famas.frontendtask.feature_dash_board.presentation.screen_dashboard

import com.famas.frontendtask.feature_dash_board.data.remote.response.DashBoardResponse

data class DashboardState(
    val loading: Boolean = false,
    val source: DashBoardResponse? = null,
    val error: String? = null
)
