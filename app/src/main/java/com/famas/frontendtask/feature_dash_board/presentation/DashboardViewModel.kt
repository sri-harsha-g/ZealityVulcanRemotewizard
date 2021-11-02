package com.famas.frontendtask.feature_dash_board.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.famas.frontendtask.core.data.Response
import com.famas.frontendtask.core.data.ResponseStatus
import com.famas.frontendtask.core.navigation.Screen.Companion.NAV_USER_ID
import com.famas.frontendtask.feature_dash_board.domain.use_cases.GetDashboardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDashboardUseCase: GetDashboardUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _dashboardState = mutableStateOf(DashboardState())
    val dashboardState: State<DashboardState> = _dashboardState

    init {
        savedStateHandle.get<String>(NAV_USER_ID)?.let {
            Log.d("myTag", it)
            viewModelScope.launch {
                _dashboardState.value = dashboardState.value.copy(loading = true)
                when(val result = getDashboardUseCase(it)) {
                    is Response.Success -> {
                        _dashboardState.value = dashboardState.value.copy(loading = false)
                        Log.d("myTag", "got result: ${result.source}")
                        when(result.source?.status) {
                            ResponseStatus.Success.id -> {
                                _dashboardState.value = dashboardState.value.copy(source = result.source)
                            }
                            ResponseStatus.Failure.id -> {
                                _dashboardState.value = dashboardState.value.copy(error = result.message)
                            }
                        }
                    }
                    is Response.Error -> {
                        _dashboardState.value = dashboardState.value.copy(loading = false, error = result.message)
                    }
                }
            }
        } ?: let {
            //should logout the user
            Log.d("myTag", "user id is null")
        }
    }
}