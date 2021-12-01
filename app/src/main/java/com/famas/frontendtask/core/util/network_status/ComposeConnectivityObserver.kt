package com.famas.frontendtask.core.util.network_status

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current

    return produceState(initialValue = context.currentConnectivityState, producer = {
        context.observeConnectivityAsFlow().collect { value = it }
    })
}