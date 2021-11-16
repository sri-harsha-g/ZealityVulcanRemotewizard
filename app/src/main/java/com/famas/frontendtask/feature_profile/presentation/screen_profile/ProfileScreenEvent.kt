package com.famas.frontendtask.feature_profile.presentation.screen_profile

sealed class ProfileScreenEvent {
    data class OnCheckChange(val value: Boolean): ProfileScreenEvent()
}
