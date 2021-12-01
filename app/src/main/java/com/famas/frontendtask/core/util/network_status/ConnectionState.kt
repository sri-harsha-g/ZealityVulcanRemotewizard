package com.famas.frontendtask.core.util.network_status

sealed class ConnectionState {
    object Available : ConnectionState()
    object UnAvailable : ConnectionState()
}
