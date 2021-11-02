package com.famas.frontendtask.feature_requests.data.remote

import com.famas.frontendtask.feature_manual_attendence.data.remote.response.BasicResponse
import com.famas.frontendtask.feature_requests.data.remote.request.PermissionRequest
import com.famas.frontendtask.feature_requests.data.remote.response.RequestsResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RequestsApi {
    @POST("clockin.php/")
    suspend fun placeRequest(
        @Body permissionRequest: PermissionRequest
    ): BasicResponse


    @POST("getrequests.php")
    suspend fun getRequests(
        @Body user_id: String
    ): RequestsResponse
}