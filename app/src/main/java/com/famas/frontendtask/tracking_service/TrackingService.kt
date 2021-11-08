package com.famas.frontendtask.tracking_service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.GnssStatus
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.famas.frontendtask.R
import com.famas.frontendtask.core.data.local.database.location_db.LocationEntity
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
import com.famas.frontendtask.core.util.isNetworkAvailable
import com.famas.frontendtask.feature_auth.domain.use_cases.GetUserId
import com.famas.frontendtask.feature_manual_attendence.domain.repository.AttendanceRepository
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class TrackingService : LifecycleService() {

    var isFirstRun = true
    var userId: String? = null

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @Inject
    lateinit var attendanceRepository: AttendanceRepository

    @Inject
    lateinit var getUserId: GetUserId

    companion object {
        val isTracking = MutableLiveData<Boolean>()
        val latestLocation = MutableLiveData<Location>()
        val networkLostTime = MutableLiveData<Long?>()
    }

    private fun postInitialValues() {
        isTracking.postValue(false)
    }

    override fun onCreate() {
        super.onCreate()
        postInitialValues()
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(applicationContext)

        isTracking.observe(this, {
            updateLocationTracking(it)
        })
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    lifecycleScope.launch { userId = getUserId() }
                    startOrResumeService()
                }

                ACTION_PAUSE_SERVICE -> {
                    Log.d("myTag", "paused service")
                    pauseService()
                    sendBroadcast(Intent("Never_Kill"))
                }

                ACTION_STOP_SERVICE -> {
                    stopService()
                }
                else -> {
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startOrResumeService() {
        //to start service safely we are checking that the service is running already
        if (isTracking.value == true) {
            stopService()
            if (isFirstRun) {
                startForegroundService()
                isFirstRun = false
            } else {
                Log.d("myTag", "resuming service")
                isTracking.postValue(true)
            }
        } else {
            if (isFirstRun) {
                startForegroundService()
                isFirstRun = false
            } else {
                Log.d("myTag", "resuming service")
                isTracking.postValue(true)
            }
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
        if (isTracking) {
            if (hasLocationPermissions(this)) {
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
            if (isTracking.value!!) {
                result.locations.let { locations ->
                    locations.forEach {
                        latestLocation.postValue(it)
                        Log.d("myTag", "NEW LOCATION: ${it.latitude}, ${it.longitude}")
                    }
                }
            }
        }
    }

    private fun startForegroundService() {
        isTracking.postValue(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("You are tracking in background")
            .setContentIntent(getMainActivityPendingIntent())

        startForeground(NOTIFICATION_ID, notificationBuilder.build())

        latestLocation.observe(this, { location ->
            if (isGpsEnabled(this)) {
                try {
                    val notification =
                        notificationBuilder.setContentTitle("lat:${location.latitude} lon:${location.longitude}")
                    notificationManager.notify(NOTIFICATION_ID, notification.build())
                    Log.d(
                        "myTag",
                        "network connection status: ${if (isNetworkAvailable(this)) "active" else "inactive"}"
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        userId?.let { user_id ->
                            Log.d("myTag", "user id in service: $user_id")

                            //Network connection available
                            if (isNetworkAvailable(this@TrackingService)) {
                                launch {
                                    //TODO have to update user location into server
                                }

                                launch {
                                    postAvailableOfflineLocationsIntoServer(userId = user_id)
                                }
                            }
                            //network connection not available
                            else {
                                postLocationIntoRoomDB(user_id, location)
                            }
                        } ?: kotlin.run {
                            userId = getUserId()
                        }
                    }
                } catch (e: Exception) {
                    Log.d("myTag", e.localizedMessage, e)
                    if (!isGpsEnabled(this)) {
                        val intent = PendingIntent.getActivity(
                            this,
                            100,
                            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                            PendingIntent.FLAG_IMMUTABLE
                        )
                        val notification = notificationBuilder
                            .setContentTitle("location service turned off")
                            .setColor(Color.RED)
                            .addAction(R.drawable.ic_launcher_foreground, "Turn on", intent)
                        notificationManager.notify(NOTIFICATION_ID, notification.build())
                    }
                }
            }
        })
    }

    private suspend fun postLocationIntoRoomDB(userId: String, location: Location) {
        Log.d("myTag", "saving loc into room db")
        if (networkLostTime.value == null) networkLostTime.postValue(location.time)

        attendanceRepository.postLocationIntoDB(
            locationEntity = LocationEntity(
                time = location.time,
                userId = userId,
                latitude = location.latitude,
                longitude = location.longitude
            )
        )
    }

    private suspend fun postAvailableOfflineLocationsIntoServer(userId: String) {
        //Some location values are posted into DB after networkLostTime to post into server
        networkLostTime.value?.let { lostTime ->
            Log.d("myTag", "post available offline locations called: $lostTime")
            CoroutineScope(Dispatchers.IO).launch {
                val locations = async {
                    attendanceRepository.getLocations(
                        userId = userId,
                        networkLostTime = lostTime
                    )
                }
                locations.await().forEach {
                    //TODO post location into server
                    Log.d("myTag", it.time.toString())
                }
            }
            networkLostTime.postValue(null)
        }
    }

    @OptIn(
        ExperimentalAnimationApi::class,
        ExperimentalPagerApi::class,
        ExperimentalMaterialApi::class
    )
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

    @SuppressLint("MissingPermission")
    private fun addGpsListener() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locationManager.registerGnssStatusCallback(object : GnssStatus.Callback() {
                override fun onStarted() {
                    super.onStarted()
                }

                override fun onStopped() {
                    super.onStopped()
                }
            }, Handler(Looper.getMainLooper()))
        }
    }
}