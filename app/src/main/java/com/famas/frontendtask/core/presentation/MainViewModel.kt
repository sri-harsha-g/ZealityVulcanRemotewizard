package com.famas.frontendtask.core.presentation

import android.app.Application
import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.famas.frontendtask.core.navigation.Screen.Companion.USER_ID
import com.famas.frontendtask.core.util.Constants
import com.famas.frontendtask.core.util.hasLocationPermissions
import com.famas.frontendtask.core.util.isGpsEnabled
import com.famas.frontendtask.feature_auth.domain.use_cases.GetUserId
import com.famas.frontendtask.tracking_service.TrackingService
import com.google.android.gms.location.LocationRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val application: Application
) : ViewModel() {

    //Managing permissions and starting service
    val isTracking = TrackingService.isTracking

    init {
        if (isGpsEnabled(application) && hasLocationPermissions(application) && isTracking.value == false) {
            Intent(application, TrackingService::class.java).also {
                it.action = Constants.ACTION_START_OR_RESUME_SERVICE
                application.startService(it)
            }
        }
    }
}