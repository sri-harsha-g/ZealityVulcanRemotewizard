package com.famas.frontendtask.feature_auth.domain.use_cases

import com.famas.frontendtask.core.data.local.datastore.Datastore
import com.famas.frontendtask.feature_auth.domain.repository.AuthRepository

class StoreUserDetails(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        userId: String,
        userName: String,
        userType: String
    ) {
        authRepository.saveUser(userId, userName, userType)
    }
}