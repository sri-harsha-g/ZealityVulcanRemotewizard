package com.famas.frontendtask.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.famas.frontendtask.core.presentation.components.NavigationDrawer
import com.famas.frontendtask.core.presentation.components.StandardToolbar
import com.famas.frontendtask.core.navigation.MainNavHost
import com.famas.frontendtask.core.navigation.Screen.*
import com.famas.frontendtask.core.navigation.getScreen
import com.famas.frontendtask.core.presentation.components.CustomBottomBar
import com.famas.frontendtask.core.presentation.util.BottomNavItem
import com.famas.frontendtask.core.ui.theme.FrontendTaskTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberAnimatedNavController()
            val scaffoldState = rememberScaffoldState()
            val coroutine = rememberCoroutineScope()
            val screens = remember {
                listOf(DashBoard, ManualAttendance, HRMS, Requests, Payslips, IDCard, Reports)
            }

            val currentRoute =
                navController.currentBackStackEntryAsState().value?.destination?.route
            val currentScreen = currentRoute?.getScreen()

            var showProfileDialog by remember { mutableStateOf(false) }

            FrontendTaskTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        topBar = {
                            StandardToolbar(
                                title = { Text(text = currentScreen?.title ?: currentRoute?.substringBefore("/") ?: "") },
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
                        },
                        drawerContent = {
                            NavigationDrawer(
                                screens = screens,
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
                        drawerGesturesEnabled = true,
                        bottomBar = {
                            if (currentScreen != DashBoard) CustomBottomBar(
                                currentScreen = currentRoute ?: ""
                            ) {
                                navController.navigate(it.route) {
                                    popUpTo(DashBoard.route)
                                    launchSingleTop = true
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
        }
    }
}