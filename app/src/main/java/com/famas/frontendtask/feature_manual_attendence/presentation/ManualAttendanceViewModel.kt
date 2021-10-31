package com.famas.frontendtask.feature_manual_attendence.presentation

import androidx.lifecycle.ViewModel
import com.famas.frontendtask.feature_manual_attendence.domain.use_cases.PlaceAttendanceUseCase
import javax.inject.Inject

class ManualAttendanceViewModel @Inject constructor(
    private val placeAttendanceUseCase: PlaceAttendanceUseCase
): ViewModel() {

}