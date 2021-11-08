package com.famas.frontendtask.core.presentation

import android.app.Application
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.famas.frontendtask.core.data.Response
import com.famas.frontendtask.core.data.local.datastore.Datastore
import com.famas.frontendtask.core.data.remote.responses.ThemeColorsResponse
import com.famas.frontendtask.core.domain.repository.ThemingRepository
import com.famas.frontendtask.core.util.Constants
import com.famas.frontendtask.core.util.hasLocationPermissions
import com.famas.frontendtask.core.util.isGpsEnabled
import com.famas.frontendtask.tracking_service.TrackingService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val application: Application,
    private val themingRepository: ThemingRepository
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

    suspend fun fetchColors(): ThemeColorsResponse {
        val sampleColors = ThemeColorsResponse(0xFFF45B49, 0xFF4A6363)
        return when(val response = themingRepository.getColors()) {
            is Response.Success -> response.source ?: sampleColors

            is Response.Error -> sampleColors
        }
    }
}