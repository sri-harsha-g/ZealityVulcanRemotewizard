package com.famas.frontendtask.feature_requests.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.famas.frontendtask.R
import com.famas.frontendtask.core.navigation.Screen
import com.famas.frontendtask.core.presentation.components.EmphasisText
import com.famas.frontendtask.core.ui.theme.*

@Composable
fun RequestBtnLayout(
    modifier: Modifier = Modifier,
    isAdmin: Boolean = false,
    onClick: (RequestButtonType) -> Unit
) {
    val buttons = remember { listOf(RequestButtonType.SWIPE_OT, RequestButtonType.LEAVE_VACATION) }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        buttons.forEach {
            RequestBtnItem(
                title = it.btnTxt,
                isAdmin = isAdmin
            ) { onClick(it) }
        }
    }
}

@Composable
fun RequestBtnItem(
    modifier: Modifier = Modifier,
    title: String,
    isAdmin: Boolean,
    onClick: () -> Unit
) {
    val elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp)
    Surface(
        modifier = modifier.padding(SpaceSmall),
        color = MaterialTheme.colors.secondary,
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, style = MaterialTheme.typography.subtitle1, color = contentColorFor(backgroundColor = MaterialTheme.colors.secondary))
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onClick,
                shape = QuarterCornerShape,
                elevation = elevation
            ) {
                Text(text = "Apply${if(isAdmin) " / Approve" else ""}")
            }
        }
    }
}

enum class RequestButtonType(val btnTxt: String, val route: String) {
    SWIPE_OT("Swipe/OT", Screen.OTSwipeLateRequest.route), LEAVE_VACATION("Leave/Vacation", Screen.LeaveOrVacationRequest.route)
}