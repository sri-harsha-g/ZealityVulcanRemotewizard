package com.famas.frontendtask.feature_dash_board.domain.use_cases

import com.famas.frontendtask.core.data.Response
import com.famas.frontendtask.feature_dash_board.data.remote.response.DashBoardResponse
import com.famas.frontendtask.feature_dash_board.domain.presentation.DashboardRepository

class GetDashboardUseCase(
    private val dashboardRepository: DashboardRepository
) {
    suspend operator fun invoke(userId: String): Response<DashBoardResponse> {
        return dashboardRepository.getDashboard(userId = userId)
    }
}