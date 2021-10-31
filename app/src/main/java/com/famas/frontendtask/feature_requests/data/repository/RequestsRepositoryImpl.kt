package com.famas.frontendtask.feature_requests.data.repository

import android.util.Log
import com.famas.frontendtask.core.util.Response
import com.famas.frontendtask.feature_manual_attendence.data.remote.response.BasicResponse
import com.famas.frontendtask.feature_requests.data.remote.RequestsApi
import com.famas.frontendtask.feature_requests.data.remote.request.PermissionRequest
import com.famas.frontendtask.feature_requests.data.remote.response.RequestsResponse
import com.famas.frontendtask.feature_requests.domain.repository.RequestsRepository
import okio.IOException
import retrofit2.HttpException

class RequestsRepositoryImpl(
    private val requestsApi: RequestsApi
): RequestsRepository {
    override suspend fun placeRequest(permissionRequest: PermissionRequest): Response<BasicResponse> {
        return try {
            val result = requestsApi.placeRequest(permissionRequest)
            Response.Success(result)
        }
        catch(e: HttpException) {
            Log.d("myTag", e.localizedMessage, e)
            Response.Error(e.localizedMessage ?: "An unexpected error occurred")
        } catch(e: IOException) {
            Log.d("myTag", e.localizedMessage, e)
            Response.Error("Couldn't reach server. Check your internet connection.")
        }
        catch (e: Exception) {
            Log.d("myTag", e.localizedMessage, e)
            Response.Error(e.localizedMessage)
        }
    }

    override suspend fun getRequests(userId: String): Response<RequestsResponse> {
        return try {
            requestsApi.getRequests(userId).let {
                Response.Success(it)
            }
        }
        catch(e: HttpException) {
            Log.d("myTag", e.localizedMessage, e)
            Response.Error(e.localizedMessage ?: "An unexpected error occurred")
        } catch(e: IOException) {
            Log.d("myTag", e.localizedMessage, e)
            Response.Error("Couldn't reach server. Check your internet connection.")
        }
        catch (e: Exception) {
            Log.d("myTag", e.localizedMessage, e)
            Response.Error(e.localizedMessage)
        }
    }
}