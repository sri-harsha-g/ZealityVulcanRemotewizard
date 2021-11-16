package com.famas.frontendtask.core.util

object Constants {

    const val IS_IN_DARK: String = "is_in_dark"
    const val REQUEST_CODE_CHECK_SETTINGS: Int = 999
    const val LOCATION_DATABASE_NAME: String = "_loc_database"
    const val DATASTORE_NAME: String = "_datastore_preferences"
    const val ALPHA_FOR_PRIMARY = 0.2f
    const val SCREEN_SLIDE_DURATION = 500

    const val REQUEST_CODE_LOCATION_PERMISSIONS = 0

    const val ACTION_START_OR_RESUME_SERVICE = "_action_start_or_resume_service"
    const val ACTION_PAUSE_SERVICE = "_action_pause_service"
    const val ACTION_STOP_SERVICE = "_action_stop_service"

    const val NOTIFICATION_CHANNEL_ID = "location_tracking_channel"
    const val NOTIFICATION_CHANNEL_NAME = "Location Tracking"
    const val NOTIFICATION_ID = 1

    const val LOCATION_UPDATE_INTERVAL = 5000L
    const val FASTEST_LOCATION_UPDATE_INTERVAL = 3000L


    //screens need not to be in Screen class
    const val AUTH_SCREEN = "screen_auth"
    const val SPLASH_SCREEN = "screen_splash"


    //Retrofit
    const val BASE_URL = "http://65.0.15.246/vcz/mobile_apis/"
}