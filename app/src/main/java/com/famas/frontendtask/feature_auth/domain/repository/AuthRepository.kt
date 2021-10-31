package com.famas.frontendtask.feature_auth.domain.repository

import com.famas.frontendtask.core.util.Response
import com.famas.frontendtask.feature_auth.data.remote.request.AuthRequest
import com.famas.frontendtask.feature_auth.data.remote.responce.AuthResponse

interface AuthRepository {
    suspend fun login(loginRequest: AuthRequest): Response<AuthResponse>
}