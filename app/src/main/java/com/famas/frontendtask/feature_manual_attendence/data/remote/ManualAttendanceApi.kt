package com.famas.frontendtask.feature_manual_attendence.data.remote

import com.famas.frontendtask.feature_manual_attendence.data.remote.request.AttendanceRequest
import com.famas.frontendtask.feature_manual_attendence.data.remote.request.UpdateLocationRequest
import com.famas.frontendtask.feature_manual_attendence.data.remote.response.BasicResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ManualAttendanceApi {

    @POST("clockin.php/")
    suspend fun placeAttendance(
        @Body attendanceRequest: AttendanceRequest
    ): BasicResponse

    @POST("clockin.php/")
    suspend fun updateLocation(
        @Body updateLocationRequest: UpdateLocationRequest
    ): BasicResponse
}