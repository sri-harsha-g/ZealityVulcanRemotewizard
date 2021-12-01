package com.famas.frontendtask.feature_loans.presentation.screen_manage_loans

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManageLoansViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel()
