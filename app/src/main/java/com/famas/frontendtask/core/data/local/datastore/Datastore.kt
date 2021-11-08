package com.famas.frontendtask.core.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.famas.frontendtask.core.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Datastore(
    private val context: Context
) {
    private val Context.datastore by preferencesDataStore(Constants.DATASTORE_NAME)

    suspend fun storeString(stringPrefKey: Preferences.Key<String>, value: String) {
        context.datastore.edit {
            it[stringPrefKey] = value
        }
    }

    suspend fun storeLong(stringPrefKey: Preferences.Key<Long>, value: Long) {
        context.datastore.edit {
            it[stringPrefKey] = value
        }
    }

    suspend fun storeInt(intPrefKey: Preferences.Key<Int>, value: Int) {
        context.datastore.edit {
            it[intPrefKey] = value
        }
    }

    fun readString(stringPrefKey: Preferences.Key<String>): Flow<String?> {
        return context.datastore.data.map {
            it[stringPrefKey]
        }
    }

    fun readLong(stringPrefKey: Preferences.Key<Long>): Flow<Long?> {
        return context.datastore.data.map {
            it[stringPrefKey]
        }
    }

    fun readInt(intPrefKey: Preferences.Key<Int>): Flow<Int?> {
        return context.datastore.data.map {
            it[intPrefKey]
        }
    }
}