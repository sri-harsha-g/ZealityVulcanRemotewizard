package com.famas.frontendtask.feature_auth.data.repository

import android.util.Log
import com.famas.frontendtask.core.data.Response
import com.famas.frontendtask.core.data.local.datastore.Datastore
import com.famas.frontendtask.core.data.local.datastore.DatastoreKeys
import com.famas.frontendtask.feature_auth.data.remote.AuthApi
import com.famas.frontendtask.feature_auth.data.remote.request.AuthRequest
import com.famas.frontendtask.feature_auth.data.remote.responce.AuthResponse
import com.famas.frontendtask.feature_auth.domain.repository.AuthRepository
import com.google.gson.Gson
import okio.IOException
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val datastore: Datastore
) : AuthRepository {
    override suspend fun login(loginRequest: AuthRequest): Response<AuthResponse> {
        return try {
            Log.d("myTag", Gson().toJson(loginRequest))
            val response = authApi.login(loginRequest)
            Log.d("myTag", response.msg)
            Response.Success(response)
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

    override suspend fun saveUser(userId: String, userName: String, userType: String) {
        datastore.storeString(DatastoreKeys.userIdKey, userId)
        datastore.storeString(DatastoreKeys.userNameKey, userName)
        datastore.storeString(DatastoreKeys.userTypeKey, userType)
    }
}