package com.famas.frontendtask.feature_manual_attendence.domain.use_cases

import com.famas.frontendtask.feature_manual_attendence.data.remote.request.AttendanceRequest
import com.famas.frontendtask.feature_manual_attendence.domain.repository.AttendanceRepository

class PlaceAttendanceUseCase(
    private val attendanceRepository: AttendanceRepository
) {
    suspend operator fun invoke(attendanceRequest: AttendanceRequest) {
        attendanceRepository.placeAttendance(attendanceRequest)
    }
}