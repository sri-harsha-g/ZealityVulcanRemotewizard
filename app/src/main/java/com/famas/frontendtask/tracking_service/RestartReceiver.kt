package com.famas.frontendtask.tracking_service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import com.famas.frontendtask.core.util.Constants
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
class RestartReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        p0?.startService(Intent(p0, TrackingService::class.java).apply {
            action = Constants.ACTION_START_OR_RESUME_SERVICE
        })
    }
}