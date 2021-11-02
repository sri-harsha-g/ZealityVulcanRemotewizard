package com.famas.frontendtask.feature_requests.domain.user_case

import com.famas.frontendtask.core.data.Response
import com.famas.frontendtask.feature_requests.data.remote.response.RequestsResponse
import com.famas.frontendtask.feature_requests.domain.repository.RequestsRepository

class GetRequestsUseCase(
    private val requestsRepository: RequestsRepository
) {
    suspend operator fun invoke(userId: String): Response<RequestsResponse> {
        return requestsRepository.getRequests(userId)
    }
}