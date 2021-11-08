package com.famas.frontendtask.core.data.repository

import com.famas.frontendtask.core.data.Response
import com.famas.frontendtask.core.data.local.datastore.Datastore
import com.famas.frontendtask.core.data.local.datastore.DatastoreKeys
import com.famas.frontendtask.core.data.remote.responses.ThemeColorsResponse
import com.famas.frontendtask.core.domain.repository.ThemingRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

class ThemingRepositoryImpl(
    private val datastore: Datastore
) : ThemingRepository {
    override suspend fun getColors(): Response<ThemeColorsResponse> {
        delay(1000L * 5)
        return datastore.readLong(DatastoreKeys.primaryColorKey).first()?.let { primary ->
            datastore.readLong(DatastoreKeys.secondaryColorKey).first()?.let { secondary ->
                Response.Success(ThemeColorsResponse(primary, secondary))
            } ?: kotlin.run {
                Response.Success(ThemeColorsResponse(0xFFF45B49, 0xFF4A6363))
            }
        } ?: kotlin.run {
            Response.Success(ThemeColorsResponse(0xFFF45B49, 0xFF4A6363))
        }
    }
}