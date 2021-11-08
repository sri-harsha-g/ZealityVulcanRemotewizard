package com.famas.frontendtask.feature_dash_board.presentation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.famas.frontendtask.R
import com.famas.frontendtask.core.data.Response
import com.famas.frontendtask.core.data.ResponseStatus
import com.famas.frontendtask.core.navigation.Screen.Companion.USER_ID
import com.famas.frontendtask.core.presentation.util.UiEvent
import com.famas.frontendtask.feature_dash_board.domain.use_cases.GetDashboardUseCase
import com.famas.frontendtask.feature_manual_attendence.domain.repository.AttendanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDashboardUseCase: GetDashboardUseCase,
    private val savedStateHandle: SavedStateHandle,
    attendanceRepository: AttendanceRepository,
    private val application: Application
) : ViewModel() {

    private val _uiEventFlow = MutableSharedFlow<UiEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    private val _dashboardState = mutableStateOf(DashboardState())
    val dashboardState: State<DashboardState> = _dashboardState

    fun onEvent(event: DashboardEvent) {
        viewModelScope.launch {
            when (event) {
                DashboardEvent.OnRetry -> {
                    fetchData()
                }
            }
        }
    }

    private fun fetchData() {
        savedStateHandle.get<String>(USER_ID)?.let {
            Log.d("myTag", it)
            viewModelScope.launch {
                _dashboardState.value = dashboardState.value.copy(loading = true)
                when (val result = getDashboardUseCase(it)) {
                    is Response.Success -> {
                        _dashboardState.value = dashboardState.value.copy(loading = false)
                        when (result.source?.status) {
                            ResponseStatus.Success.id -> {
                                _dashboardState.value =
                                    dashboardState.value.copy(source = result.source)
                            }
                            ResponseStatus.Failure.id -> {
                                _dashboardState.value =
                                    dashboardState.value.copy(error = result.message)
                                _uiEventFlow.emit(UiEvent.ShowSnackBar(result.source.msg))
                            }
                        }
                    }
                    is Response.Error -> {
                        _dashboardState.value =
                            dashboardState.value.copy(loading = false, error = result.message)
                        _uiEventFlow.emit(
                            UiEvent.ShowSnackBar(
                                result.message
                                    ?: application.getString(R.string.something_went_wrong),
                                actionLabel = "retry"
                            )
                        )
                    }
                }
            }
        } ?: let {
            //should logout the user
            viewModelScope.launch {
                _uiEventFlow.emit(UiEvent.ShowSnackBar("Something went wrong with authentication. Please login again"))
            }
            Log.d("myTag", "user id is null")
        }
    }

    init {
        fetchData()
    }
}