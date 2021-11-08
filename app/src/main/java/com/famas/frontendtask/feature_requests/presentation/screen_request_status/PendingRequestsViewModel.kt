package com.famas.frontendtask.feature_requests.presentation.screen_request_status

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.famas.frontendtask.core.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PendingRequestsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiEventFlow = MutableSharedFlow<UiEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    private val _pendingRequestsState = mutableStateOf(PendingRequestsState())
    val pendingRequestsState: State<PendingRequestsState> = _pendingRequestsState

    fun onEvent(event: PendingRequestsEvent) {
        viewModelScope.launch {
            when (event) {
                is PendingRequestsEvent.OnReason -> {
                    _pendingRequestsState.value = pendingRequestsState.value.copy(
                        reasonDialogState = pendingRequestsState.value.reasonDialogState.copy(reason = event.reason)
                    )
                }

                PendingRequestsEvent.OnAccept -> {
                    //TODO
                }

                PendingRequestsEvent.OnReject -> {
                    _pendingRequestsState.value = pendingRequestsState.value.copy(
                        reasonDialogState = ReasonDialogState(showDialog = true)
                    )
                }

                PendingRequestsEvent.OnSubmitDialog -> {
                    //TODO
                }

                PendingRequestsEvent.OnCancelDialog -> {
                    _pendingRequestsState.value = pendingRequestsState.value.copy(
                        reasonDialogState = ReasonDialogState(showDialog = false)
                    )
                }
            }
        }
    }
}