package com.famas.frontendtask.feature_dash_board.domain.presentation

import com.famas.frontendtask.core.data.Response
import com.famas.frontendtask.feature_dash_board.data.remote.response.DashBoardResponse

interface DashboardRepository {
    suspend fun getDashboard(userId: String): Response<DashBoardResponse>
}