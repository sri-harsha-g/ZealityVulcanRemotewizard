package com.famas.frontendtask.feature_manual_attendence.domain.repository

import com.famas.frontendtask.core.util.Response
import com.famas.frontendtask.feature_manual_attendence.data.remote.request.AttendanceRequest
import com.famas.frontendtask.feature_manual_attendence.data.remote.request.UpdateLocationRequest
import com.famas.frontendtask.feature_manual_attendence.data.remote.response.BasicResponse

interface AttendanceRepository {
    suspend fun placeAttendance(attendanceRequest: AttendanceRequest): Response<BasicResponse>

    suspend fun updateUserLocation(updateLocationRequest: UpdateLocationRequest)
}