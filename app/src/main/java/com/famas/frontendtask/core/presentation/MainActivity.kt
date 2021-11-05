package com.famas.frontendtask.core.presentation

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.famas.frontendtask.core.navigation.MainNavHost
import com.famas.frontendtask.core.navigation.Screen
import com.famas.frontendtask.core.navigation.Screen.*
import com.famas.frontendtask.core.navigation.getScreen
import com.famas.frontendtask.core.presentation.components.CustomBottomBar
import com.famas.frontendtask.core.presentation.components.NavigationDrawer
import com.famas.frontendtask.core.presentation.components.StandardToolbar
import com.famas.frontendtask.core.presentation.util.BottomNavItem
import com.famas.frontendtask.core.ui.theme.FrontendTaskTheme
import com.famas.frontendtask.core.util.Constants
import com.famas.frontendtask.core.util.hasLocationPermissions
import com.famas.frontendtask.core.util.isGpsEnabled
import com.famas.frontendtask.tracking_service.TrackingService
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            navController = rememberAnimatedNavController()
            val scaffoldState = rememberScaffoldState()
            val coroutine = rememberCoroutineScope()
            val menuScreens = remember {
                listOf(
                    DashBoard,
                    ManualAttendance,
                    HRMS,
                    Requests,
                    Payslips,
                    IDCard,
                    CameraAuth,
                    Reports
                )
            }

            val currentRoute =
                navController.currentBackStackEntryAsState().value?.destination?.route
            val currentScreen = currentRoute?.getScreen()
            val showGestures =
                if (currentRoute == null) false
                else currentRoute != Constants.AUTH_SCREEN && currentRoute != Constants.SPLASH_SCREEN && currentRoute != CameraAuth.route

            var showProfileDialog by remember { mutableStateOf(false) }

            FrontendTaskTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        topBar = {
                            if (showGestures) {
                                StandardToolbar(
                                    title = {
                                        Text(
                                            text = currentScreen?.title
                                                ?: currentRoute?.substringBefore("/") ?: ""
                                        )
                                    },
                                    showBackArrow = currentScreen != DashBoard,
                                    navActions = {
                                        if (currentRoute == DashBoard.route) {
                                            IconButton(onClick = {
                                                navController.navigate(BottomNavItem.Notifications.route)
                                            }) {
                                                Image(
                                                    imageVector = Icons.Default.Notifications,
                                                    contentDescription = "notifications"
                                                )
                                            }
                                            IconButton(onClick = {
                                                navController.navigate(BottomNavItem.Logout.route)
                                            }) {
                                                Image(
                                                    imageVector = Icons.Default.Logout,
                                                    contentDescription = "profile pic"
                                                )
                                            }
                                            IconButton(onClick = {
                                                navController.navigate(BottomNavItem.Search.route)
                                            }) {
                                                Image(
                                                    imageVector = Icons.Default.Search,
                                                    contentDescription = "search"
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
                                    screens = menuScreens,
                                    currentScreen = currentScreen ?: DashBoard,
                                    onItemClick = {
                                        if (it != currentScreen) {
                                            if (it == DashBoard) navController.navigateUp()
                                            else navController.navigate(it.route) {
                                                popUpTo(DashBoard.route)
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
                                if (currentScreen != DashBoard) CustomBottomBar(
                                    currentScreen = currentRoute ?: ""
                                ) {
                                    navController.navigate(it.route) {
                                        popUpTo(DashBoard.route)
                                        launchSingleTop = true
                                    }
                                }
                            }
                        }
                    ) {
                        MainNavHost(
                            navController = navController,
                            scaffoldState = scaffoldState,
                            modifier = Modifier.padding(bottom = it.calculateBottomPadding())
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
                                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                            }
                            else sendCommandToService(Constants.ACTION_START_OR_RESUME_SERVICE)
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
                                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                            }
                            else sendCommandToService(Constants.ACTION_START_OR_RESUME_SERVICE)
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

            //requesting permissions
            LaunchedEffect(key1 = currentRoute, key2 = isTracking, block = {
                if (!showGestures) delay(5000L)
                if (!hasLocationPermissions(this@MainActivity)) {
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
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Please turn on location",
                            Toast.LENGTH_LONG
                        ).show()
                        delay(1000L)
                        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                }

                if (isTracking && !isGpsEnabled(this@MainActivity)) {
                    sendCommandToService(Constants.ACTION_STOP_SERVICE)
                    sendCommandToService(Constants.ACTION_START_OR_RESUME_SERVICE)
                }
            })
        }
    }

    private fun sendCommandToService(command: String) {
        Intent(this, TrackingService::class.java).also {
            it.action = command
            startService(it)
        }
    }

    override fun onBackPressed() {
        navController.navigateUp()
    }
}