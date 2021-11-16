package com.famas.frontendtask.core.presentation.activity_main

import android.Manifest
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.famas.frontendtask.core.navigation.MainNavHost
import com.famas.frontendtask.core.navigation.Screen.*
import com.famas.frontendtask.core.navigation.getScreen
import com.famas.frontendtask.core.presentation.activity_main.Util.menuScreens
import com.famas.frontendtask.core.presentation.components.CustomBottomBar
import com.famas.frontendtask.core.presentation.components.NavigationDrawer
import com.famas.frontendtask.core.presentation.components.StandardToolbar
import com.famas.frontendtask.core.presentation.util.BottomNavItem
import com.famas.frontendtask.core.ui.theme.FrontendTaskTheme
import com.famas.frontendtask.core.ui.theme.appOrangeColor
import com.famas.frontendtask.core.ui.theme.appPurpleColor
import com.famas.frontendtask.core.util.Constants
import com.famas.frontendtask.core.util.Constants.AUTH_SCREEN
import com.famas.frontendtask.core.util.Constants.SPLASH_SCREEN
import com.famas.frontendtask.core.util.hasLocationPermissions
import com.famas.frontendtask.core.util.isGpsEnabled
import com.famas.frontendtask.tracking_service.TrackingService
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    var currentRoute: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initial actions
        observeLocationAvailability()

        setContent {
            val navController = rememberAnimatedNavController()
            val state = viewModel.mainActivityState.value
            val scaffoldState = rememberScaffoldState()
            val coroutine = rememberCoroutineScope()

            currentRoute =
                navController.currentBackStackEntryAsState().value?.destination?.route
            val currentScreen = currentRoute?.getScreen()
            val showGestures =
                if (currentRoute == null) false
                else currentRoute != AUTH_SCREEN && currentRoute != SPLASH_SCREEN && currentRoute != CameraAuth.route

            FrontendTaskTheme(darkTheme = state.isInDarkTheme) {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        topBar = {
                            if (showGestures) {
                                StandardToolbar(
                                    title = {
                                        Text(
                                            fontWeight = FontWeight.Bold,
                                            //we are show route here in case of null title
                                            text = currentScreen?.title
                                                ?: currentRoute?.substringBefore("/")
                                                ?: "" //Formatting route for clean view

                                        )
                                    },
                                    showBackArrow = currentScreen != DashBoard,
                                    navActions = {
                                        if (currentRoute == DashBoard.route) {
                                            IconButton(
                                                onClick = {
                                                    viewModel.onEvent(MainActivityEvent.OpenBottomBar(BottomNavItem.Search, currentRoute))
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Search,
                                                    contentDescription = null,
                                                    tint = appPurpleColor,
                                                    modifier = Modifier
                                                        .clip(CircleShape)
                                                        .background(appPurpleColor.copy(0.2f))
                                                        .padding(4.dp)
                                                )
                                            }
                                            IconButton(
                                                onClick = {
                                                    viewModel.onEvent(MainActivityEvent.OpenBottomBar(BottomNavItem.Notifications, currentRoute))
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Notifications,
                                                    contentDescription = null,
                                                    tint = appOrangeColor,
                                                    modifier = Modifier
                                                        .clip(CircleShape)
                                                        .background(appOrangeColor.copy(0.1f))
                                                        .padding(4.dp)
                                                )
                                            }
                                            IconButton(
                                                onClick = {
                                                    viewModel.onEvent(MainActivityEvent.OpenBottomBar(BottomNavItem.Profile, currentRoute))
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Person,
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colors.primary,
                                                    modifier = Modifier
                                                        .clip(CircleShape)
                                                        .background(
                                                            MaterialTheme.colors.primary.copy(
                                                                0.2f
                                                            )
                                                        )
                                                        .padding(4.dp)
                                                )
                                            }
                                        }
                                    },
                                    onNavigateUp = {
                                        navController.navigateUp()
                                    },
                                    onNavIconClick = {
                                        coroutine.launch {
                                            scaffoldState.drawerState.open()
                                        }
                                    },
                                )
                            }
                        },
                        drawerContent = {
                            if (showGestures)
                                NavigationDrawer(
                                    screens = remember { menuScreens },
                                    currentScreen = currentScreen ?: DashBoard,
                                    onItemClick = {
                                        when {
                                            it is Loans -> {
                                                navController.navigate(ApplyLoan.route)
                                            }

                                            it != currentScreen -> {
                                                if (it == DashBoard) navController.navigateUp()
                                                else navController.navigate(it.route) {
                                                    popUpTo(DashBoard.route)
                                                }
                                            }
                                        }
                                        coroutine.launch {
                                            scaffoldState.drawerState.close()
                                        }
                                    }
                                )
                        },
                        drawerShape = RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp),
                        drawerGesturesEnabled = true,
                        bottomBar = {
                            if (showGestures) {
                                CustomBottomBar(
                                    currentScreen = currentRoute ?: ""
                                ) {
                                    if (currentRoute != it.route) {
                                        viewModel.onEvent(MainActivityEvent.OpenBottomBar(it, currentRoute))
                                    }
                                }
                            }
                        }
                    ) {
                        MainNavHost(
                            navController = navController,
                            modifier = Modifier
                                .padding(bottom = it.calculateBottomPadding()),
                            scaffoldState = scaffoldState
                        )
                    }
                }
            }


            val isTracking by viewModel.isTracking.observeAsState(initial = false)

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions(),
                onResult = {
                    when {
                        it.getOrElse(Manifest.permission.ACCESS_FINE_LOCATION, { false }) -> {
                            // Precise location access granted.
                            Log.d("myTag", "Precise location access granted")
                            if (!isGpsEnabled(this@MainActivity)) {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Please turn on location",
                                    Toast.LENGTH_LONG
                                ).show()
                                enableLocationSettings()
                            } else sendCommandToService(Constants.ACTION_START_OR_RESUME_SERVICE)
                        }

                        it.getOrElse(Manifest.permission.ACCESS_COARSE_LOCATION, { false }) -> {
                            // Only approximate location access granted.
                            Log.d("myTag", "Only approximate location access granted")
                            if (!isGpsEnabled(this@MainActivity)) {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Please turn on location",
                                    Toast.LENGTH_LONG
                                ).show()
                                enableLocationSettings()
                            } else sendCommandToService(Constants.ACTION_START_OR_RESUME_SERVICE)
                        }

                        else -> {
                            // No location access granted.
                            Log.d("myTag", "No location access granted")
                            Toast.makeText(
                                this, "Please accept location permissions",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            )

            //Observe main ui event flow
            LaunchedEffect(key1 = Unit, block = {
                viewModel.mainActivityUiEventFlow.collectLatest {
                    when(it) {
                        is MainActivityUiEventFlow.Navigate -> {
                            navController.navigate(it.route)
                        }

                        is MainActivityUiEventFlow.ShowSnackBar -> {
                            scaffoldState.snackbarHostState.showSnackbar(it.message)
                        }
                        is MainActivityUiEventFlow.OpenBottomBar -> {
                            navController.navigate(it.route) {
                                it.fromRoute?.let { fr -> popUpTo(fr) }
                            }
                        }
                    }
                }
            })

            //requesting permissions
            LaunchedEffect(key1 = currentRoute, key2 = isTracking, block = {
                if (!showGestures) delay(5000L)
                if (!hasLocationPermissions(this@MainActivity) && currentRoute != SPLASH_SCREEN) {
                    launcher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }

                if (showGestures && !isTracking && hasLocationPermissions(this@MainActivity)) {
                    if (isGpsEnabled(this@MainActivity)) {
                        sendCommandToService(Constants.ACTION_START_OR_RESUME_SERVICE)
                    } else enableLocationSettings()
                }

                if (isTracking && !isGpsEnabled(this@MainActivity)) {
                    sendCommandToService(Constants.ACTION_STOP_SERVICE)
                    sendCommandToService(Constants.ACTION_START_OR_RESUME_SERVICE)
                }
            })
        }
    }

    private fun observeLocationAvailability() {
        TrackingService.isLocationAvailable.observe(this) {
            if (!it) {
                enableLocationSettings()
            }
        }
    }

    private fun sendCommandToService(command: String) {
        if (currentRoute != null && currentRoute != SPLASH_SCREEN && currentRoute != AUTH_SCREEN)
            Intent(this, TrackingService::class.java).also {
                it.action = command
                startService(it)
            }
    }

    private fun enableLocationSettings() {
        val locationRequest: LocationRequest = TrackingService.request
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        LocationServices
            .getSettingsClient(this)
            .checkLocationSettings(builder.build())
            .addOnSuccessListener(
                this
            ) { response: LocationSettingsResponse? ->
                Log.d("myTag", "success response: ${response?.locationSettingsStates?.isGpsUsable}")
            }
            .addOnFailureListener(
                this
            ) { ex: Exception? ->
                if (ex is ResolvableApiException) {
                    Log.d("myTag", "exception enableLocationSettings")
                    // Location settings are NOT satisfied,  but this can be fixed  by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),  and check the result in onActivityResult().
                        ex.startResolutionForResult(
                            this,
                            Constants.REQUEST_CODE_CHECK_SETTINGS
                        )
                    } catch (sendEx: SendIntentException) {
                        // Ignore the error.
                    }
                }
            }
    }
}