package com.famas.frontendtask.feature_profile.presentation.screen_profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.famas.frontendtask.core.data.local.datastore.Datastore
import com.famas.frontendtask.core.data.local.datastore.DatastoreKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val datastore: Datastore
) : ViewModel() {

    private val _profileState = mutableStateOf(ProfileState())
    val profileState: State<ProfileState> = _profileState

    fun onEvent(event: ProfileScreenEvent) {
        viewModelScope.launch {
            when(event) {
                is ProfileScreenEvent.OnCheckChange -> {
                    _profileState.value = profileState.value.copy(
                        isInDarkMode = event.value
                    )
                    datastore.storeBoolean(DatastoreKeys.isInDarkThemeKey, event.value)
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            datastore.readBoolean(DatastoreKeys.isInDarkThemeKey).first()?.let {
                _profileState.value = profileState.value.copy(isInDarkMode = it)
            }
        }
    }
}