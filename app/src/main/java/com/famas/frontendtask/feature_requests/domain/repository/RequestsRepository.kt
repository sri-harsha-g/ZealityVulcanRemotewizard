package com.famas.frontendtask.feature_requests.domain.repository

import com.famas.frontendtask.core.data.Response
import com.famas.frontendtask.feature_manual_attendence.data.remote.response.BasicResponse
import com.famas.frontendtask.feature_requests.data.remote.request.PermissionRequest
import com.famas.frontendtask.feature_requests.data.remote.response.RequestsResponse

interface RequestsRepository {
    suspend fun placeRequest(permissionRequest: PermissionRequest): Response<BasicResponse>
    suspend fun getRequests(userId: String): Response<RequestsResponse>
}