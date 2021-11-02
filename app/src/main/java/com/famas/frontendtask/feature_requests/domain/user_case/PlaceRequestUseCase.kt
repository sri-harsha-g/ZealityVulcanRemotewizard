package com.famas.frontendtask.feature_requests.domain.user_case

import com.famas.frontendtask.core.data.Response
import com.famas.frontendtask.feature_manual_attendence.data.remote.response.BasicResponse
import com.famas.frontendtask.feature_requests.data.remote.request.PermissionRequest
import com.famas.frontendtask.feature_requests.domain.repository.RequestsRepository

class PlaceRequestUseCase(
    private val requestsRepository: RequestsRepository
) {
    suspend operator fun invoke(permissionRequest: PermissionRequest): Response<BasicResponse> {
        return requestsRepository.placeRequest(permissionRequest)
    }
}