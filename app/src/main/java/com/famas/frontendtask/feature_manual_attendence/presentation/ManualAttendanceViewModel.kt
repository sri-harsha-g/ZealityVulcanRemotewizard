package com.famas.frontendtask.feature_manual_attendence.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.famas.frontendtask.core.presentation.util.UiEvent
import com.famas.frontendtask.feature_manual_attendence.domain.use_cases.PlaceAttendanceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class ManualAttendanceViewModel @Inject constructor(
    private val placeAttendanceUseCase: PlaceAttendanceUseCase
) : ViewModel() {

    private val _uiEventFlow = MutableSharedFlow<UiEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    private val _mAttendanceState = mutableStateOf(MAttendanceState())
    val mAttendanceState: State<MAttendanceState> = _mAttendanceState

    fun onEvent(event: MAttendanceEvent) {
        when (event) {
            is MAttendanceEvent.OnDepartmentSelected -> {
                _mAttendanceState.value = mAttendanceState.value.copy(
                    departmentsDropDown = mAttendanceState.value.departmentsDropDown.copy(
                        selectedIndex = event.index
                    )
                )
            }

            is MAttendanceEvent.OnEmployeeSelected -> {
                _mAttendanceState.value = mAttendanceState.value.copy(
                    employeeDropDown = mAttendanceState.value.employeeDropDown.copy(
                        selectedIndex = event.index
                    )
                )
            }

            is MAttendanceEvent.OnDateTimeSelected -> {
                _mAttendanceState.value =
                    mAttendanceState.value.copy(selectedDateTime = event.dateTime)
            }

            is MAttendanceEvent.OnClockIn -> {

            }

            is MAttendanceEvent.OnClockOut -> {

            }
        }
    }
}