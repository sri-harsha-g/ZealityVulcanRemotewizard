package com.famas.frontendtask.feature_manual_attendence.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.famas.frontendtask.core.presentation.compose_states.DropDownTextFieldPickerState
import com.famas.frontendtask.core.presentation.util.UiEvent
import com.famas.frontendtask.feature_manual_attendence.domain.use_cases.PlaceAttendanceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
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
        viewModelScope.launch {
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
                    event.index?.let {
                        try {
                            Log.d("myTag", "selected employee " + mAttendanceState.value.employeeDropDown.list[it])
                        } catch (e: Exception) {
                            Log.d("myTag", e.localizedMessage, e)
                        }
                    }
                    delay(2000)
                    Log.d("myTag", "selected index: ${mAttendanceState.value.employeeDropDown.selectedIndex}")
                }

                is MAttendanceEvent.OnDateTimeSelected -> {
                    _mAttendanceState.value =
                        mAttendanceState.value.copy(selectedDateTime = event.dateTime)
                }

                is MAttendanceEvent.OnEmployeeTextValue -> {
                    _mAttendanceState.value = mAttendanceState.value.copy(
                        employeeDropDown = mAttendanceState.value.employeeDropDown.copy(
                            fieldValue = event.value
                        )
                    )

                    //TODO: Have to update the list from server
                    _mAttendanceState.value = mAttendanceState.value.copy(
                        employeeDropDown = mAttendanceState.value.employeeDropDown.copy(
                            list = MAttendanceState().employeeDropDown.list.filter { it.contains(
                                mAttendanceState.value.employeeDropDown.fieldValue
                            ) }
                        )
                    )
                }

                is MAttendanceEvent.OnClockIn -> {

                }

                is MAttendanceEvent.OnClockOut -> {

                }
                MAttendanceEvent.OnRetry -> {

                }
            }

        }
    }
}