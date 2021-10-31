package com.famas.frontendtask.feature_auth.domain.use_cases

import com.famas.frontendtask.core.util.Response
import com.famas.frontendtask.feature_auth.data.remote.request.AuthRequest
import com.famas.frontendtask.feature_auth.data.remote.responce.AuthResponse
import com.famas.frontendtask.feature_auth.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(emailOrUserName: String, password: String): Response<AuthResponse> {
        return authRepository.login(AuthRequest(user_name = emailOrUserName, password = password))
    }
}