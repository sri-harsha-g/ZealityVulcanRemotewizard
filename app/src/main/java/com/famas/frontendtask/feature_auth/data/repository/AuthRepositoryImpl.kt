package com.famas.frontendtask.feature_auth.data.repository

import android.util.Log
import com.famas.frontendtask.core.util.Response
import com.famas.frontendtask.feature_auth.data.remote.AuthApi
import com.famas.frontendtask.feature_auth.data.remote.request.AuthRequest
import com.famas.frontendtask.feature_auth.data.remote.responce.AuthResponse
import com.famas.frontendtask.feature_auth.domain.repository.AuthRepository
import okio.IOException
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val authApi: AuthApi
): AuthRepository {
    override suspend fun login(loginRequest: AuthRequest): Response<AuthResponse> {
        return try {
            val response = authApi.login(loginRequest)
            Response.Success(response)
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