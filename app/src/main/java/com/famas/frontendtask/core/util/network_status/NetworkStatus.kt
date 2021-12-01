package com.famas.frontendtask.core.util.network_status

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

val Context.currentConnectivityState: ConnectionState
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return getCurrentConnectivityState(connectivityManager)
    }


fun getCurrentConnectivityState(
    connectivityManager: ConnectivityManager
): ConnectionState {
    val connected = connectivityManager.allNetworks.any {
        connectivityManager.getNetworkCapabilities(it)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    }

    return if (connected) ConnectionState.Available else ConnectionState.UnAvailable
}
