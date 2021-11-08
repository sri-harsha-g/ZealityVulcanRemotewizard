package com.famas.frontendtask.feature_dash_board.data.remote

import com.famas.frontendtask.feature_dash_board.data.remote.response.DashBoardResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface DashboardApi {
    @POST("dashboard.php/")
    suspend fun getDashboard(
        @Body user_id: String
    ): DashBoardResponse
}