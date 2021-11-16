package com.famas.frontendtask.feature_auth.domain.repository

import com.famas.frontendtask.core.data.Response

interface SplashRepository {
    suspend fun checkForUpdates() : Response<Boolean>
}