package com.famas.frontendtask.feature_requests.presentation.screen_requests

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.famas.frontendtask.core.navigation.Screen
import com.famas.frontendtask.core.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestsScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiEventFlow = MutableSharedFlow<UiEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    private val _requestsScreenState = mutableStateOf(RequestsScreenState())
    val requestsScreenState: State<RequestsScreenState> = _requestsScreenState

    fun onEvent(event: RequestsScreenEvent) {
        viewModelScope.launch {
            when (event) {
                is RequestsScreenEvent.OnRequestBtnLtClick -> {
                    if (requestsScreenState.value.isAdmin) {
                        _requestsScreenState.value = requestsScreenState.value.copy(
                            requestDialogBtnState = RequestDialogBtnState(
                                showDialog = true,
                                dialogFor = event.requestButtonType
                            )
                        )
                    } else _uiEventFlow.emit(UiEvent.OnNavigate(event.requestButtonType.route))
                }

                RequestsScreenEvent.OnApply -> {
                    requestsScreenState.value.requestDialogBtnState.dialogFor?.let {
                        _uiEventFlow.emit(UiEvent.OnNavigate(it.route))
                    }
                    _requestsScreenState.value = requestsScreenState.value.copy(
                        requestDialogBtnState = RequestDialogBtnState(showDialog = false)
                    )
                }

                RequestsScreenEvent.OnApprove -> {
                    _requestsScreenState.value = requestsScreenState.value.copy(
                        requestDialogBtnState = RequestDialogBtnState(showDialog = false)
                    )
                    _uiEventFlow.emit(UiEvent.OnNavigate(Screen.PendingRequests.route))
                }
            }
        }
    }

    init {
        _requestsScreenState.value = requestsScreenState.value.copy(isAdmin = true)
        /*savedStateHandle.get<Boolean>(IS_ADMIN)?.let {
            _requestsScreenState.value = requestsScreenState.value.copy(isAdmin = true)
        }*/
    }
}