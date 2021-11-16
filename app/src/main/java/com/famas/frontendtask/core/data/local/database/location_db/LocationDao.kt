package com.famas.frontendtask.core.data.local.database.location_db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface LocationDao {

    @Query("SELECT * FROM locationentity WHERE time >= :networkLostTime AND userId=:userId")
    suspend fun getLocations(userId: String, networkLostTime: Long) : Array<LocationEntity>

    @Insert
    suspend fun insertLocation(locationEntity: LocationEntity)

    @Query("SELECT * FROM locationentity")
    suspend fun getAllLocations() : Array<LocationEntity>

    @Delete
    suspend fun deleteLocations(vararg locationEntity: LocationEntity)
}