package com.famas.frontendtask.tracking_service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import com.famas.frontendtask.core.util.Constants
import com.famas.frontendtask.core.util.isGpsEnabled
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
class RestartReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, p1: Intent?) {
        Log.d("myTag", "Got broadcast")
        if (TrackingService.isTracking.value == true) return

        CoroutineScope(Dispatchers.IO).launch {
            context?.let {
                if (!isGpsEnabled(it)) {
                    Log.d("myTag", "Gps not enabled")
                }
                if (TrackingService.isTracking.value != true){
                    it.startService(Intent(context, TrackingService::class.java).apply {
                        action = Constants.ACTION_START_OR_RESUME_SERVICE
                    })
                }
            }
        }
    }
}