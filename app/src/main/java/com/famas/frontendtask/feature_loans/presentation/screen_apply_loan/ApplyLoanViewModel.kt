package com.famas.frontendtask.feature_loans.presentation.screen_apply_loan

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.famas.frontendtask.core.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ApplyLoanViewModel @Inject constructor(

) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _applyLoanState = mutableStateOf(ApplyLoanState())
    val applyLoanState: State<ApplyLoanState> = _applyLoanState

    fun onEvent(event: ApplyLoanEvent) {
        viewModelScope.launch {
            when(event) {
                is ApplyLoanEvent.OnEnterAmount -> {
                    _applyLoanState.value = applyLoanState.value.copy(
                        amount = event.value
                    )
                }

                is ApplyLoanEvent.OnSelectLoan -> {
                    _applyLoanState.value = applyLoanState.value.copy(
                        applyLoanDropDown = applyLoanState.value.applyLoanDropDown.copy(
                            selectedIndex = event.index
                        )
                    )
                }
            }
        }
    }
}