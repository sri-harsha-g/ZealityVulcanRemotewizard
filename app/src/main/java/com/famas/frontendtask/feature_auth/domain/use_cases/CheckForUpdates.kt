package com.famas.frontendtask.feature_auth.domain.use_cases

import com.famas.frontendtask.core.data.Response
import com.famas.frontendtask.feature_auth.domain.repository.SplashRepository

class CheckForUpdates(
    private val splashRepository: SplashRepository
) {
    suspend operator fun invoke(): Response<Boolean> {
        return splashRepository.checkForUpdates()
    }
}