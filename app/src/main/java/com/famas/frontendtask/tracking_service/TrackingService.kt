package com.famas.frontendtask.tracking_service
import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.famas.frontendtask.R
import com.famas.frontendtask.core.presentation.MainActivity
import com.famas.frontendtask.core.util.Constants.ACTION_PAUSE_SERVICE
import com.famas.frontendtask.core.util.Constants.ACTION_START_OR_RESUME_SERVICE
import com.famas.frontendtask.core.util.Constants.ACTION_STOP_SERVICE
import com.famas.frontendtask.core.util.Constants.FASTEST_LOCATION_UPDATE_INTERVAL
import com.famas.frontendtask.core.util.Constants.LOCATION_UPDATE_INTERVAL
import com.famas.frontendtask.core.util.Constants.NOTIFICATION_CHANNEL_ID
import com.famas.frontendtask.core.util.Constants.NOTIFICATION_CHANNEL_NAME
import com.famas.frontendtask.core.util.Constants.NOTIFICATION_ID
import com.famas.frontendtask.core.util.hasLocationPermissions
import com.famas.frontendtask.core.util.isGpsEnabled
import com.famas.frontendtask.feature_manual_attendence.data.remote.request.UpdateLocationRequest
import com.famas.frontendtask.feature_manual_attendence.domain.repository.AttendanceRepository
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class TrackingService : LifecycleService() {

    var isFirstRun = true

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @Inject
    lateinit var attendanceRepository: AttendanceRepository

    companion object {
        val isTracking = MutableLiveData<Boolean>()
        val latestLocation = MutableLiveData<Location>()
    }

    private fun postInitialValues() {
        isTracking.postValue(false)
    }

    override fun onCreate() {
        super.onCreate()
        postInitialValues()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(applicationContext)

        isTracking.observe(this, {
            updateLocationTracking(it)
        })
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {

                    startOrResumeService()
                }

                ACTION_PAUSE_SERVICE -> {
                    Log.d("myTag","paused service")
                    pauseService()
                    sendBroadcast(Intent("Never_Kill"))
                }

                ACTION_STOP_SERVICE -> {
                    stopService()
                }
                else -> {}
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startOrResumeService() {
        if(isFirstRun) {
            startForegroundService()
            isFirstRun = false
        } else {
            Log.d("myTag","resuming service")
            isTracking.postValue(true)
        }
    }

    private fun pauseService() {
        isTracking.postValue(false)
    }

    private fun stopService() {
        isFirstRun = true
        pauseService()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        stopForeground(true)
        stopSelf()
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationTracking(isTracking: Boolean) {
        Log.d("myTag", "updateLocationTracking fun is executed: $isTracking")
        if(isTracking) {
            if(hasLocationPermissions(this)) {
                fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                    latestLocation.postValue(it)
                }

                fusedLocationProviderClient.requestLocationUpdates(
                    request,
                    locationCallback,
                    Looper.getMainLooper()
                )
            }
        } else {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }

    private val request = LocationRequest.create().apply {
        interval = LOCATION_UPDATE_INTERVAL
        fastestInterval = FASTEST_LOCATION_UPDATE_INTERVAL
        priority = PRIORITY_HIGH_ACCURACY
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            if(isTracking.value!!) {
                result.locations.let { locations ->
                    locations.forEach {
                        latestLocation.postValue(it)
                        Log.d("myTag","NEW LOCATION: ${it.latitude}, ${it.longitude}")
                    }
                }
            }
        }
    }

    private fun startForegroundService() {
        isTracking.postValue(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("You are tracking in background")
            .setContentIntent(getMainActivityPendingIntent())

        startForeground(NOTIFICATION_ID, notificationBuilder.build())

        latestLocation.observe(this, {
            if (isGpsEnabled(this)) {
                try {
                    val notification = notificationBuilder.setContentTitle("lat:${it.latitude} lon:${it.longitude}")
                    notificationManager.notify(NOTIFICATION_ID, notification.build())
                    /*lifecycleScope.launch {
                        attendanceRepository.updateUserLocation(UpdateLocationRequest(userId = "user_id", latitude = "${it.latitude}", longitude = "${it.longitude}", time = "${it.time}"))
                    }*/
                }
                catch (e: Exception) {
                    if (!isGpsEnabled(this)) {
                        stopService()
                    }
                }
            }
        })
    }

    @OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
    private fun getMainActivityPendingIntent() = PendingIntent.getActivity(
        this,
        0,
        Intent(this, MainActivity::class.java),
        FLAG_UPDATE_CURRENT
    )

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }

    override fun onDestroy() {
        super.onDestroy()
        sendBroadcast(Intent("_action_start_or_resume_service"))
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        sendBroadcast(Intent("_action_start_or_resume_service"))
    }
}