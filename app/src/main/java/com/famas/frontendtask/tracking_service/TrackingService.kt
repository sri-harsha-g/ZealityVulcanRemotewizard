package com.famas.frontendtask.tracking_service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent.*
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.famas.frontendtask.R
import com.famas.frontendtask.core.data.local.database.location_db.LocationEntity
import com.famas.frontendtask.core.presentation.activity_main.MainActivity
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
import com.famas.frontendtask.core.util.network_status.ConnectionState
import com.famas.frontendtask.core.util.network_status.ConnectionState.*
import com.famas.frontendtask.core.util.network_status.currentConnectivityState
import com.famas.frontendtask.core.util.network_status.observeConnectivityAsFlow
import com.famas.frontendtask.feature_auth.domain.use_cases.GetUserId
import com.famas.frontendtask.feature_manual_attendence.domain.repository.AttendanceRepository
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
class TrackingService : LifecycleService() {

    var isFirstRun = true
    var userId: String? = null

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @Inject
    lateinit var attendanceRepository: AttendanceRepository

    @Inject
    lateinit var getUserId: GetUserId

    private var isInternetAvailable = false

    companion object {
        val isTracking = MutableLiveData<Boolean>()
        val latestLocation = MutableLiveData<Location>()
        val networkLostTime = MutableLiveData<Long?>()
        val isLocationAvailable = MutableLiveData(true)

        val request: LocationRequest = LocationRequest.create().apply {
            interval = LOCATION_UPDATE_INTERVAL
            fastestInterval = FASTEST_LOCATION_UPDATE_INTERVAL
            priority = PRIORITY_HIGH_ACCURACY
        }
    }

    private fun postInitialValues() {
        isTracking.postValue(false)
        observeConnectivityAsFlow().asLiveData().observe(this) {
            isInternetAvailable = it == Available
        }
    }

    override fun onCreate() {
        postInitialValues()
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(applicationContext)

        isTracking.observe(this, {
            updateLocationTracking(it)
        })
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    lifecycleScope.launch {
                        userId = getUserId()
                    }
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
        return super.onStartCommand(intent, flags, Service.START_STICKY)
    }

    private fun startOrResumeService() {
        //to start service safely we are checking that the service is running already
        if (isFirstRun) {
            startForegroundService()
            isFirstRun = false
        } else {
            Log.d("myTag", "resuming service")
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

        override fun onLocationAvailability(p0: LocationAvailability) {
            super.onLocationAvailability(p0)
            Log.d("myTag", "location availability: ${p0.isLocationAvailable}")
            isLocationAvailable.postValue(p0.isLocationAvailable)
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

        val turnOnLocationIntent =
            getActivity(this, 1, Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), FLAG_IMMUTABLE)
        //Observe location availability
        isLocationAvailable.observe(this) {
            if (!it) {
                notificationBuilder
                    .setContentTitle("Tracking service paused due to inactive location service")
                    .setContentText("Please turn on location service")
                    .setColorized(true)
                    .setColor(Color.RED)
                    .addAction(R.drawable.ic_launcher_background, "turn on", turnOnLocationIntent)
                    .setContentIntent(turnOnLocationIntent)

                notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
            }
            else {
                notificationBuilder.setColorized(false)
                    .setContentTitle("Tracking service is running in background")
                    .setContentIntent(getMainActivityPendingIntent())
                    .setContentText("")
                    .setColorized(false)
                    .setColor(Color.GREEN)
                    .clearActions()
                notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
            }
        }

        //Observe location
        latestLocation.observe(this, { location ->
            if (isGpsEnabled(this)) {
                try {
                    Log.d(
                        "myTag",
                        "network connection status: ${if (isInternetAvailable) "active" else "inactive"}"
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        userId?.let { user_id ->
                            Log.d("myTag", "user id in service: $user_id")

                            //Network connection available
                            if (isInternetAvailable) {
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
                        val intent = getActivity(
                            this,
                            100,
                            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                            FLAG_IMMUTABLE
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

        /*attendanceRepository.postLocationIntoDB(
            locationEntity = LocationEntity(
                time = location.time,
                userId = userId,
                latitude = location.latitude,
                longitude = location.longitude
            )
        )*/
    }

    private suspend fun postAvailableOfflineLocationsIntoServer(userId: String) {
        //Some location values are posted into DB after networkLostTime to post into server
        networkLostTime.value?.let { lostTime ->
            Log.d("myTag", "post available offline locations called: $lostTime")
            CoroutineScope(Dispatchers.IO).launch {
                val locations = attendanceRepository.getLocations(
                    userId = userId,
                    networkLostTime = lostTime
                )
                locations.forEach {
                    //TODO post location into server
                    Log.d("myTag", it.time.toString())
                }
                attendanceRepository.deleteLocations(locationEntity = locations)
            }
            networkLostTime.postValue(null)
        }
    }

    private fun getMainActivityPendingIntent() = getActivity(
        this,
        0,
        Intent(this, MainActivity::class.java),
        FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
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