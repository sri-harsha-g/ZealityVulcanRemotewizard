package com.famas.frontendtask.core.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationEntity(
    @PrimaryKey
    val time: Long,
    val userId: String,
    val latitude: Double,
    val longitude: Double
)
