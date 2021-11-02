package com.famas.frontendtask.feature_auth.presentation.screen_splash

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.famas.frontendtask.core.navigation.Screen
import com.famas.frontendtask.core.presentation.util.UiEvent
import com.famas.frontendtask.core.util.Constants
import com.famas.frontendtask.feature_auth.domain.use_cases.GetUserId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserId: GetUserId
): ViewModel() {

    val userId = mutableStateOf<String?>("")

    init {
        viewModelScope.launch {
            val id = async { getUserId() }
            userId.value = id.await()
        }
    }
}