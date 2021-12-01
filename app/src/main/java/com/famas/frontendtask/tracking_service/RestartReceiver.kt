package com.famas.frontendtask.tracking_service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.content.res.ResourcesCompat
import com.famas.frontendtask.R
import com.famas.frontendtask.core.util.Constants
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
class RestartReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Intent.ACTION_BOOT_COMPLETED == intent?.action) {
            Log.d("myTag", "Got broadcast for boot: ${context?.applicationContext}")
            /*context?.startService(Intent(context, TrackingService::class.java).apply {
                action = Constants.ACTION_START_OR_RESUME_SERVICE
            })*/
        }

    }
}