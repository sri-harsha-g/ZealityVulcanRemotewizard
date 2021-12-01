package com.famas.frontendtask.core.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.famas.frontendtask.core.navigation.Screen.*
import com.famas.frontendtask.core.presentation.util.BottomNavItem.*
import com.famas.frontendtask.core.util.Constants.AUTH_SCREEN
import com.famas.frontendtask.core.util.Constants.SCREEN_SLIDE_DURATION
import com.famas.frontendtask.core.util.Constants.SPLASH_SCREEN
import com.famas.frontendtask.feature_auth.presentation.screen_auth.AuthScreen
import com.famas.frontendtask.feature_auth.presentation.screen_splash.SplashScreen
import com.famas.frontendtask.feature_dash_board.presentation.screen_dashboard.DashBoardScreen
import com.famas.frontendtask.feature_face_auth.presentation.CameraPreviewScreen
import com.famas.frontendtask.feature_hrms.presentation.HRMS
import com.famas.frontendtask.feature_id_card.presentation.IDCardScreen
import com.famas.frontendtask.feature_loans.presentation.screen_apply_loan.ApplyLoanScreen
import com.famas.frontendtask.feature_loans.presentation.screen_manage_loans.ManageLoansScreen
import com.famas.frontendtask.feature_manual_attendence.presentation.ManualAttendanceScreen
import com.famas.frontendtask.feature_payslips.presentation.PayslipsScreen
import com.famas.frontendtask.feature_profile.presentation.screen_profile.ScreenProfile
import com.famas.frontendtask.feature_reports.presentation.ReportsScreen
import com.famas.frontendtask.feature_requests.presentation.screen_leave_vacation.LeaveVacationRequest
import com.famas.frontendtask.feature_requests.presentation.screen_ot_swipe_late.OTSwipeLateRequestScreen
import com.famas.frontendtask.feature_requests.presentation.screen_request_status.PendingRequests
import com.famas.frontendtask.feature_requests.presentation.screen_requests.RequestScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    scaffoldState: ScaffoldState
) {
    val scope = rememberCoroutineScope()

    AnimatedNavHost(
        navController = navController,
        startDestination = SPLASH_SCREEN,
        modifier = modifier
    ) {

        composable(SPLASH_SCREEN) {
            SplashScreen(
                onNavigate = {
                    navController.navigate(it) {
                        popUpTo(SPLASH_SCREEN) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(AUTH_SCREEN) {
            AuthScreen(
                navigate = {
                    navController.navigate(it) {
                        popUpTo(AUTH_SCREEN) {
                            inclusive = true
                        }
                    }
                },
                showSnackBar = {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(it)
                    }
                }
            )
        }

        composable(DashBoard.route) {
            DashBoardScreen(scaffoldState, navController)
        }

        composable(
            ManualAttendance.route,
            popEnterTransition = {
                fadeIn()
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 },
                    animationSpec = tween(SCREEN_SLIDE_DURATION)
                )
            }
        ) {
            ManualAttendanceScreen(navController = navController, scaffoldState = scaffoldState)
        }

        composable(
            HRMS.route,
            popEnterTransition = {
                fadeIn()
            },
        ) {
            HRMS()
        }

        composable(
            Requests.route
        ) {
            RequestScreen(navController = navController, scaffoldState = scaffoldState)
        }

        composable(
            Payslips.route,
            popEnterTransition = {
                fadeIn()
            },
        ) {
            PayslipsScreen()
        }

        composable(
            IDCard.route,
            popEnterTransition = {
                fadeIn()
            },
        ) {
            IDCardScreen()
        }

        composable(CameraAuth.route) {
            CameraPreviewScreen(navigate = { navController.navigate(it) }, showSnackbar = {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(it)
                }
            })
        }

        composable(
            Reports.route,
            popEnterTransition = {
                fadeIn()
            },
        ) {
            ReportsScreen()
        }

        composable(
            LeaveOrVacationRequest.route,
            popEnterTransition = {
                fadeIn()
            },
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(SCREEN_SLIDE_DURATION)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 1000 }, animationSpec = tween(
                        SCREEN_SLIDE_DURATION
                    )
                )
            }
        ) {
            LeaveVacationRequest(navController = navController, scaffoldState = scaffoldState)
        }

        composable(
            PendingRequests.route,
            popEnterTransition = {
                fadeIn()
            },
        ) {
            PendingRequests()
        }

        composable(OTSwipeLateRequest.route,
            popEnterTransition = {
                fadeIn()
            },
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 }, animationSpec = tween(
                        SCREEN_SLIDE_DURATION
                    )
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 1000 }, animationSpec = tween(
                        SCREEN_SLIDE_DURATION
                    )
                )
            }
        ) {
            OTSwipeLateRequestScreen()
        }


        composable(
            Search.route,
            enterTransition = {
                slideInVertically(initialOffsetY = { 1000 },
                    animationSpec = tween(SCREEN_SLIDE_DURATION)
                )
            },
            exitTransition = {
                slideOutVertically(targetOffsetY = { 1000 },
                    animationSpec = tween(SCREEN_SLIDE_DURATION)
                )
            }
        ) {

        }

        composable(
            Notifications.route,
            enterTransition = {
                slideInVertically(initialOffsetY = { 1000 },
                    animationSpec = tween(SCREEN_SLIDE_DURATION)
                )
            },
            exitTransition = {
                slideOutVertically(targetOffsetY = { 1000 },
                    animationSpec = tween(SCREEN_SLIDE_DURATION)
                )
            }
        ) {

        }

        composable(
            Profile.route,
            enterTransition = {
                slideInVertically(initialOffsetY = { 1000 },
                    animationSpec = tween(SCREEN_SLIDE_DURATION)
                )
            },
            exitTransition = {
                slideOutVertically(targetOffsetY = { 1000 },
                    animationSpec = tween(SCREEN_SLIDE_DURATION)
                )
            }
        ) {
            ScreenProfile()
        }


        composable(
            ApplyLoan.route,
            popEnterTransition = {
                fadeIn()
            },
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(SCREEN_SLIDE_DURATION)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 1000 }, animationSpec = tween(
                        SCREEN_SLIDE_DURATION
                    )
                )
            }
        ) {
            ApplyLoanScreen()
        }

        composable(
            ManageLoans.route,
            popEnterTransition = {
                fadeIn()
            },
        ) {
            ManageLoansScreen()
        }
    }
}