package com.famas.frontendtask.feature_auth.domain.use_cases

import com.famas.frontendtask.core.data.local.datastore.Datastore
import com.famas.frontendtask.core.data.local.datastore.DatastoreKeys
import kotlinx.coroutines.flow.first

class GetUserId(
    private val datastore: Datastore
) {
    suspend operator fun invoke(): String? {
        return datastore.readString(DatastoreKeys.userIdKey).first()
    }
}