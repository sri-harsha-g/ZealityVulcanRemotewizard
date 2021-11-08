package com.famas.frontendtask.di

import com.famas.frontendtask.core.data.local.database.location_db.LocationDao
import com.famas.frontendtask.core.util.Constants
import com.famas.frontendtask.feature_manual_attendence.data.remote.ManualAttendanceApi
import com.famas.frontendtask.feature_manual_attendence.data.repository.AttendanceRepositoryImpl
import com.famas.frontendtask.feature_manual_attendence.domain.repository.AttendanceRepository
import com.famas.frontendtask.feature_manual_attendence.domain.use_cases.PlaceAttendanceUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AttendanceModule {

    @Provides
    @Singleton
    fun provideAttendanceApi(gson: Gson): ManualAttendanceApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(Constants.BASE_URL)
        .build()
        .create(ManualAttendanceApi::class.java)

    @Provides
    @Singleton
    fun provideAttendanceRepository(
        attendanceApi: ManualAttendanceApi,
        locationDao: LocationDao
    ): AttendanceRepository {
        return AttendanceRepositoryImpl(manualAttendanceApi = attendanceApi, locationDao = locationDao)
    }

    @Provides
    @Singleton
    fun providePlaceAttendanceUseCase(attendanceRepository: AttendanceRepository): PlaceAttendanceUseCase {
        return PlaceAttendanceUseCase(attendanceRepository)
    }
}