package com.famas.frontendtask.feature_manual_attendence.domain.repository

import com.famas.frontendtask.core.data.Response
import com.famas.frontendtask.core.data.local.database.location_db.LocationEntity
import com.famas.frontendtask.feature_manual_attendence.data.remote.request.AttendanceRequest
import com.famas.frontendtask.feature_manual_attendence.data.remote.request.UpdateLocationRequest
import com.famas.frontendtask.feature_manual_attendence.data.remote.response.BasicResponse

interface AttendanceRepository {
    suspend fun placeAttendance(attendanceRequest: AttendanceRequest): Response<BasicResponse>

    suspend fun updateUserLocation(updateLocationRequest: UpdateLocationRequest)

    suspend fun postLocationIntoDB(locationEntity: LocationEntity)

    suspend fun getLocations(userId: String, networkLostTime: Long) : Array<LocationEntity>

    suspend fun getAllLocations() : Array<LocationEntity>
}