package com.famas.frontendtask.feature_auth.presentation.screen_splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.famas.frontendtask.core.data.Response
import com.famas.frontendtask.core.data.local.datastore.Datastore
import com.famas.frontendtask.core.data.local.datastore.DatastoreKeys
import com.famas.frontendtask.feature_auth.domain.use_cases.CheckForUpdates
import com.famas.frontendtask.feature_auth.domain.use_cases.GetUserId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserId: GetUserId,
    private val checkForUpdates: CheckForUpdates,
    private val datastore: Datastore
) : ViewModel() {

    private val _splashState = mutableStateOf(SplashState())
    val splashState: State<SplashState> = _splashState

    init {
        _splashState.value = splashState.value.copy(loading = true)
        viewModelScope.launch {

            val isInDarkTheme = async {
                datastore.readBoolean(DatastoreKeys.isInDarkThemeKey).first() ?: false
            }
            val updateResponse = async {
                checkForUpdates()
            }
            val id = async { getUserId() }


            _splashState.value = splashState.value.copy(isInDarkTheme = isInDarkTheme.await())
            when(val response = updateResponse.await()) {
                is Response.Success -> {
                    _splashState.value = splashState.value.copy(loading = false, isUpdateAvailable = response.source ?: false)
                }
                is Response.Error -> {
                    _splashState.value = splashState.value.copy(loading = false)
                }
            }
            _splashState.value = splashState.value.copy(userId = id.await())
        }
    }
}