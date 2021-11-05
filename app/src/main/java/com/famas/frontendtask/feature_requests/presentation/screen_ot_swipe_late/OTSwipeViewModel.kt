package com.famas.frontendtask.feature_requests.presentation.screen_ot_swipe_late

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.famas.frontendtask.core.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class OTSwipeViewModel @Inject constructor(

) : ViewModel() {

    private val _uiEventFlow = MutableSharedFlow<UiEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    private val _otSwipeState = mutableStateOf(OTSwipeState())
    val otSwipeState: State<OTSwipeState> = _otSwipeState


    fun onEvent(event: OTSwipeEvent) {
        when (event) {
            is OTSwipeEvent.OnSelectPerType -> {
                _otSwipeState.value = otSwipeState.value.copy(
                    permissionDDState = otSwipeState.value.permissionDDState.copy(
                        selectedIndex = event.index
                    )
                )
            }

            is OTSwipeEvent.SetSelectedDate -> {
                _otSwipeState.value = otSwipeState.value.copy(selectedDate = event.date)
            }

            is OTSwipeEvent.SetHour -> {
                _otSwipeState.value = otSwipeState.value.copy(
                    hoursPicker = otSwipeState.value.hoursPicker.copy(selectedIndex = event.hourIndex)
                )
            }

            is OTSwipeEvent.SetMinute -> {
                _otSwipeState.value = otSwipeState.value.copy(
                    minutesPicker = otSwipeState.value.minutesPicker.copy(selectedIndex = event.minuteIndex)
                )
            }

            is OTSwipeEvent.SetReason -> {
                _otSwipeState.value = otSwipeState.value.copy(
                    reason = event.reason
                )
            }

            is OTSwipeEvent.OnClickApply -> {

            }
        }
    }
}