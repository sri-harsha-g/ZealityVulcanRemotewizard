package com.famas.frontendtask.feature_dash_board.data.repository

import android.util.Log
import com.famas.frontendtask.core.data.Response
import com.famas.frontendtask.feature_dash_board.data.remote.DashboardApi
import com.famas.frontendtask.feature_dash_board.data.remote.response.DashBoardResponse
import com.famas.frontendtask.feature_dash_board.domain.presentation.DashboardRepository
import okio.IOException
import retrofit2.HttpException

class DashboardRepositoryImpl(
    private val dashboardApi: DashboardApi
) : DashboardRepository {
    override suspend fun getDashboard(userId: String): Response<DashBoardResponse> {
        return try {
            val result = dashboardApi.getDashboard(userId)
            Response.Success(result)
        } catch (e: HttpException) {
            Log.d("myTag", e.localizedMessage, e)
            Response.Error(e.localizedMessage ?: "An unexpected error occurred")
        } catch (e: IOException) {
            Log.d("myTag", e.localizedMessage, e)
            Response.Error("Couldn't reach server. Check your internet connection.")
        } catch (e: Exception) {
            Log.d("myTag", e.localizedMessage, e)
            Response.Error(e.localizedMessage)
        }
    }
}