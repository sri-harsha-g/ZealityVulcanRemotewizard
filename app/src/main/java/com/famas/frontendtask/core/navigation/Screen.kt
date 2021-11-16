package com.famas.frontendtask.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Money
import androidx.compose.ui.graphics.vector.ImageVector
import com.famas.frontendtask.core.navigation.Screen.*
import com.famas.frontendtask.core.util.Constants.IS_IN_DARK


sealed class Screen(
    val route: String,
    val title: String,
    val icon: (Boolean) -> ImageVector? = { null },
    val getRoute: (String) -> String = { route }
) {
    object DashBoard : Screen(
        "_dash_board/{$USER_ID}?$IS_IN_DARK={$IS_IN_DARK}",
        "Dashboard",
        { if (it) Icons.Filled.Dashboard else Icons.Default.Dashboard },
        getRoute = { "_dash_board/$it" })

    object ManualAttendance : Screen(
        "_man_atte",
        "Manual Attendance",
        { if (it) Icons.Filled.Analytics else Icons.Default.Analytics })

    object HRMS : Screen(
        "_hrms",
        "HRMS",
        { if (it) Icons.Filled.Preview else Icons.Default.Preview }
    )

    object Requests : Screen(
        "_req",
        "My Requests",
        { if (it) Icons.Filled.HealthAndSafety else Icons.Default.HealthAndSafety })

    object PendingRequests : Screen(
        "PendingRequests",
        "Pending Requests",
        { if (it) Icons.Filled.HealthAndSafety else Icons.Default.HealthAndSafety })

    object Payslips : Screen(
        "_pay",
        "Payslips",
        { if (it) Icons.Filled.Payments else Icons.Default.Payments }
    )

    object IDCard : Screen(
        "_id_card",
        "IDCard",
        { if (it) Icons.Filled.PermIdentity else Icons.Default.PermIdentity })

    object Reports : Screen(
        "_reports",
        "Reports",
        { if (it) Icons.Filled.Report else Icons.Default.Report }
    )

    object LeaveOrVacationRequest :
        Screen("_leave_or_vacation_request", "Leave Or Vacation Request")

    object OTSwipeLateRequest : Screen("ot_swipe_late_request", "OT/Swipe/Late Request")
    object CameraAuth : Screen(
        "_camera_auth",
        "Camera auth",
        { if (it) Icons.Filled.CameraAlt else Icons.Outlined.CameraAlt })

    object ManageLoans : Screen("manage_loans", "Manage Loans")

    object ApplyLoan : Screen("apply_loan", "Apply Loan")

    object Loans : Screen("", "Loans", icon = { if (it) Icons.Filled.Money else Icons.Outlined.Money })

    companion object {
        const val IS_ADMIN: String = "is_admin"
        const val USER_ID = "user_id"
    }
}


fun String.getScreen(): Screen? {
    return listOf(
        DashBoard,
        ManualAttendance,
        HRMS,
        Requests,
        Payslips,
        IDCard,
        Reports,
        LeaveOrVacationRequest,
        OTSwipeLateRequest,
        PendingRequests,
        CameraAuth,
        ManageLoans,
        ApplyLoan
    ).find {
        it.route == this
    }
}