package com.famas.frontendtask.core.presentation.activity_main

import android.app.Application
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.famas.frontendtask.core.data.Response
import com.famas.frontendtask.core.data.local.datastore.Datastore
import com.famas.frontendtask.core.data.local.datastore.DatastoreKeys
import com.famas.frontendtask.core.data.remote.responses.ThemeColorsResponse
import com.famas.frontendtask.core.domain.repository.ThemingRepository
import com.famas.frontendtask.core.presentation.util.BottomNavItem.*
import com.famas.frontendtask.core.util.Constants
import com.famas.frontendtask.core.util.hasLocationPermissions
import com.famas.frontendtask.core.util.isGpsEnabled
import com.famas.frontendtask.tracking_service.TrackingService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val application: Application,
    private val themingRepository: ThemingRepository,
    savedStateHandle: SavedStateHandle,
    datastore: Datastore
) : ViewModel() {

    private val _mainActivityUiEventFlow = MutableSharedFlow<MainActivityUiEventFlow>()
    val mainActivityUiEventFlow = _mainActivityUiEventFlow.asSharedFlow()

    private val _mainActivityState = mutableStateOf(MainActivityState())
    val mainActivityState: State<MainActivityState> = _mainActivityState

    //Managing permissions and starting service
    val isTracking = TrackingService.isTracking

    fun onEvent(event: MainActivityEvent) {
        viewModelScope.launch {
            when (event) {
                is MainActivityEvent.Navigate -> {
                    _mainActivityUiEventFlow.emit(MainActivityUiEventFlow.Navigate(event.route))
                }

                is MainActivityEvent.OpenBottomBar -> {
                    if (event.screen.route == event.fromRoute) {
                        _mainActivityUiEventFlow.emit(MainActivityUiEventFlow.NavigateUp)
                    } else {
                        if (event.fromRoute !in listOf(
                                Profile,
                                Search,
                                Notifications
                            ).map { it.route }
                        ) {
                            _mainActivityState.value =
                                mainActivityState.value.copy(openedBottomBarFromRoute = event.fromRoute)
                        }
                        _mainActivityUiEventFlow.emit(
                            MainActivityUiEventFlow.OpenBottomBar(
                                route = event.screen.route,
                                fromRoute = mainActivityState.value.openedBottomBarFromRoute
                            )
                        )
                    }
                }

                is MainActivityEvent.ShowLocPermissionsDialog -> {
                    _mainActivityState.value = mainActivityState.value.copy(
                        locationDialogState = LocationDialogState(
                            showLocationDialog = true,
                            isPermissionsDeniedPermanently = event.shouldShowRationale
                        )
                    )
                }

                is MainActivityEvent.CloseLocPermissionsDialog -> {
                    _mainActivityState.value = mainActivityState.value.copy(
                        locationDialogState = LocationDialogState(
                            showLocationDialog = false
                        )
                    )
                }

                MainActivityEvent.HideOpenSettingsText -> {
                    _mainActivityState.value = mainActivityState.value.copy(
                        locationDialogState = mainActivityState.value.locationDialogState.copy(
                            showOpenSettingsText = false
                        )
                    )
                }

                MainActivityEvent.ShowOpenSettingsText -> {
                    _mainActivityState.value = mainActivityState.value.copy(
                        locationDialogState = mainActivityState.value.locationDialogState.copy(
                            showOpenSettingsText = true
                        )
                    )
                }
            }
        }
    }

    suspend fun fetchColors(): ThemeColorsResponse {
        val sampleColors = ThemeColorsResponse(0xFFF45B49, 0xFF4A6363)
        return when (val response = themingRepository.getColors()) {
            is Response.Success -> response.source ?: sampleColors

            is Response.Error -> sampleColors
        }
    }

    init {

        if (isGpsEnabled(application) && hasLocationPermissions(application) && isTracking.value == false) {
            Intent(application, TrackingService::class.java).also {
                it.action = Constants.ACTION_START_OR_RESUME_SERVICE
                application.startService(it)
            }
        }

        viewModelScope.launch {
            launch {
                savedStateHandle.get<Boolean>(Constants.IS_IN_DARK)?.let {
                    _mainActivityState.value = mainActivityState.value.copy(isInDarkTheme = it)
                }
            }

            launch {
                datastore.readBoolean(DatastoreKeys.isInDarkThemeKey).collectLatest {
                    it?.let {
                        _mainActivityState.value = mainActivityState.value.copy(isInDarkTheme = it)
                    }
                }
            }
        }
    }
}