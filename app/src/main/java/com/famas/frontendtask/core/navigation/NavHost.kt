package com.famas.frontendtask.core.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.famas.frontendtask.core.util.Constants.SCREEN_SLIDE_DURATION
import com.famas.frontendtask.core.navigation.Screen.*
import com.famas.frontendtask.core.presentation.util.BottomNavItem.*
import com.famas.frontendtask.core.util.Constants
import com.famas.frontendtask.core.util.Constants.AUTH_SCREEN
import com.famas.frontendtask.feature_auth.presentation.AuthScreen
import com.famas.frontendtask.feature_dash_board.presentation.DashBoardScreen
import com.famas.frontendtask.feature_face_auth.presentation.CameraPreviewScreen
import com.famas.frontendtask.feature_hrms.presentation.HRMS
import com.famas.frontendtask.feature_id_card.presentation.IDCardScreen
import com.famas.frontendtask.feature_manual_attendence.presentation.ManualAttendance
import com.famas.frontendtask.feature_payslips.presentation.PayslipsScreen
import com.famas.frontendtask.feature_reports.presentation.ReportsScreen
import com.famas.frontendtask.feature_requests.presentation.screen_leave_vacation.LeaveVacationRequest
import com.famas.frontendtask.feature_requests.presentation.screen_ot_swipe_late.OTSwipeLateRequestScreen
import com.famas.frontendtask.feature_requests.presentation.screen_request.RequestScreen
import com.famas.frontendtask.feature_requests.presentation.screen_request_status.PendingRequests
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
        startDestination = AUTH_SCREEN,
        modifier = modifier
    ) {

        composable(AUTH_SCREEN) {
            AuthScreen(
                navigate = {
                    navController.navigate(it)
                },
                showSnackBar = {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(it)
                    }
                }
            )
        }

        composable(DashBoard.route) {
            DashBoardScreen(scaffoldState) {
                navController.navigate(it)
            }
        }

        composable(ManualAttendance.route) {
            ManualAttendance()
        }

        composable(HRMS.route) {
            HRMS()
        }

        composable(Requests.route) {
            RequestScreen(navController = navController)
        }

        composable(Payslips.route) {
            PayslipsScreen()
        }

        composable(IDCard.route) {
            IDCardScreen()
        }

        composable(CameraAuth.route) {
            CameraPreviewScreen(navigate = { navController.navigate(it) }, showSnackbar = {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(it)
                }
            })
        }

        composable(Reports.route) {
            ReportsScreen()
        }

        composable(
            LeaveOrVacationRequest.route,
            enterTransition = { _, _ ->
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(SCREEN_SLIDE_DURATION)
                )
            },
            popExitTransition = { _, _ ->
                slideOutHorizontally(
                    targetOffsetX = { 1000 }, animationSpec = tween(
                        SCREEN_SLIDE_DURATION
                    )
                )
            }
        ) {
            LeaveVacationRequest()
        }

        composable(PendingRequests.route) {
            PendingRequests()
        }

        composable(OTSwipeLateRequest.route,
            enterTransition = { _, _ ->
                slideInHorizontally(
                    initialOffsetX = { 1000 }, animationSpec = tween(
                        SCREEN_SLIDE_DURATION
                    )
                )
            },
            popExitTransition = { _, _ ->
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
            enterTransition = { _, _ ->
                slideInVertically(initialOffsetY = { 1000 }, tween(SCREEN_SLIDE_DURATION))
            },
            exitTransition = { _, _ ->
                slideOutVertically(targetOffsetY = { 1000 }, tween(SCREEN_SLIDE_DURATION))
            }) {

        }

        composable(
            Notifications.route,
            enterTransition = { _, _ ->
                slideInVertically(initialOffsetY = { 1000 }, tween(SCREEN_SLIDE_DURATION))
            },
            exitTransition = { _, _ ->
                slideOutVertically(targetOffsetY = { 1000 }, tween(SCREEN_SLIDE_DURATION))
            }
        ) {

        }

        composable(
            Logout.route,
            enterTransition = { _, _ ->
                slideInVertically(initialOffsetY = { 1000 }, tween(SCREEN_SLIDE_DURATION))
            },
            exitTransition = { _, _ ->
                slideOutVertically(targetOffsetY = { 1000 }, tween(SCREEN_SLIDE_DURATION))
            }
        ) {

        }
    }
}