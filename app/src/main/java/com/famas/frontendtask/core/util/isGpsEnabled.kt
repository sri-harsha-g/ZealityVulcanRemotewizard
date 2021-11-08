package com.famas.frontendtask.core.util

import android.content.Context
import android.location.LocationManager
import android.os.Build

fun isGpsEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> locationManager.isLocationEnabled
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> locationManager.isProviderEnabled(
            LocationManager.FUSED_PROVIDER
        )
        else -> locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    /*LocationServices.getSettingsClient(application)
        .checkLocationSettings(
            LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true)
                .build())
        .addOnSuccessListener {
            Log.d("myTag", "Gps enabled")
                enableLocation.value = true
        }
        .addOnFailureListener {
            Log.d("myTag", "Gps not enabled")
            enableLocation.value = false
        }*/
}