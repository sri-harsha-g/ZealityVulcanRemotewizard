package com.famas.frontendtask.feature_manual_attendence.data.repository

import android.util.Log
import com.famas.frontendtask.core.data.Response
import com.famas.frontendtask.core.data.local.database.location_db.LocationDao
import com.famas.frontendtask.core.data.local.database.location_db.LocationEntity
import com.famas.frontendtask.feature_manual_attendence.data.remote.ManualAttendanceApi
import com.famas.frontendtask.feature_manual_attendence.data.remote.request.AttendanceRequest
import com.famas.frontendtask.feature_manual_attendence.data.remote.request.UpdateLocationRequest
import com.famas.frontendtask.feature_manual_attendence.data.remote.response.BasicResponse
import com.famas.frontendtask.feature_manual_attendence.domain.repository.AttendanceRepository
import kotlinx.coroutines.delay
import okio.IOException
import retrofit2.HttpException

class AttendanceRepositoryImpl(
    private val manualAttendanceApi: ManualAttendanceApi,
    private val locationDao: LocationDao
) : AttendanceRepository {

    override suspend fun placeAttendance(attendanceRequest: AttendanceRequest): Response<BasicResponse> {
        return try {
            val result = manualAttendanceApi.placeAttendance(attendanceRequest)
            Response.Success(result)
        } catch (e: HttpException) {
            Log.d("myTag", e.localizedMessage, e)
            Response.Error(e.localizedMessage ?: "An unexpected error occurred")
        } catch (e: IOException) {
            Log.d("myTag", e.localizedMessage, e)
            Response.Error("Couldn't reach server. Check your internet connection.")
        } catch (e: Exception) {
            Log.d("myTag", e.localizedMessage, e)
            Response.Error(e.localizedMessage)
        }
    }

    override suspend fun updateUserLocation(updateLocationRequest: UpdateLocationRequest) {
        try {
            manualAttendanceApi.updateLocation(updateLocationRequest)
        } catch (e: HttpException) {
            Log.d("myTag", e.localizedMessage, e)
        } catch (e: IOException) {
            Log.d("myTag", e.localizedMessage, e)
        } catch (e: Exception) {
            Log.d("myTag", e.localizedMessage, e)
        }
    }

    override suspend fun postLocationIntoDB(locationEntity: LocationEntity) {
        locationDao.insertLocation(locationEntity = locationEntity)
    }

    override suspend fun getLocations(
        userId: String,
        networkLostTime: Long
    ): Array<LocationEntity> {
        return locationDao.getLocations(userId, networkLostTime)
    }

    override suspend fun getAllLocations(): Array<LocationEntity> {
        delay(3000L)
        return locationDao.getAllLocations()
    }
}