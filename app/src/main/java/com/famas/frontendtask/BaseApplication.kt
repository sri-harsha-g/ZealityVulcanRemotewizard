package com.famas.frontendtask

import android.app.Application
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import com.famas.frontendtask.core.data.local.shared_pref.SharedPrefUtils
import com.famas.frontendtask.core.util.Constants
import com.famas.frontendtask.tracking_service.RestartReceiver
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.HiltAndroidApp

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@HiltAndroidApp
open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private lateinit var appInstance: BaseApplication
        var sharedPrefUtils: SharedPrefUtils? = null

        fun getAppInstance(): BaseApplication {
            return appInstance
        }
    }
}