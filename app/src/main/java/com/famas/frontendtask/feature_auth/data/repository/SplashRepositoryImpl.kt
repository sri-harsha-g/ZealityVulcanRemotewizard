package com.famas.frontendtask.feature_auth.data.repository

import com.famas.frontendtask.core.data.Response
import com.famas.frontendtask.feature_auth.data.remote.SplashApi
import com.famas.frontendtask.feature_auth.domain.repository.SplashRepository
import kotlinx.coroutines.delay

class SplashRepositoryImpl(
    private val splashApi : SplashApi
) : SplashRepository {
    override suspend fun checkForUpdates(): Response<Boolean> {
        delay(1500)
        return Response.Success(false)
    }
}