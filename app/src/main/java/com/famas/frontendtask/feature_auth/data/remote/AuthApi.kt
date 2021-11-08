package com.famas.frontendtask.feature_auth.data.remote

import com.famas.frontendtask.feature_auth.data.remote.request.AuthRequest
import com.famas.frontendtask.feature_auth.data.remote.responce.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("login.php")
    suspend fun login(
        @Body authRequest: AuthRequest
    ): AuthResponse
}