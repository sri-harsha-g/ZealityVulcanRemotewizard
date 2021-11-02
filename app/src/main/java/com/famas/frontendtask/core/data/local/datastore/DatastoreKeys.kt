package com.famas.frontendtask.core.data.local.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object DatastoreKeys {
    val userNameKey = stringPreferencesKey("_user_name_key")
    val userIdKey = stringPreferencesKey("_user_id_key")
    val fenceIdKey = stringPreferencesKey("fence_id_key")
    val userTypeKey = stringPreferencesKey("_user_type_key")
}