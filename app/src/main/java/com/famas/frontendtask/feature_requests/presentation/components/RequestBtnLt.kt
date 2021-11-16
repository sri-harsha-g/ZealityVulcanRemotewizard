package com.famas.frontendtask.feature_requests.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.famas.frontendtask.core.navigation.Screen
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSmall

@Composable
fun RequestBtnLayout(
    modifier: Modifier = Modifier,
    onClick: (RequestButtonType) -> Unit
) {
    val elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp)
    val buttons = remember { listOf(RequestButtonType.SWIPE_OT, RequestButtonType.LEAVE_VACATION) }

    Surface(
        modifier = modifier.padding(SpaceSmall),
        color = MaterialTheme.colors.surface,
        shape = MaterialTheme.shapes.small,
        elevation = SpaceSmall,
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colors.primary)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            buttons.forEach {
                TextButton(onClick = { onClick(it) }, modifier = Modifier.weight(0.5f)) {
                    Text(
                        text = it.btnTxt,
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.padding(SpaceMedium)
                    )
                }
            }
        }
    }
}

enum class RequestButtonType(val btnTxt: String, val route: String) {
    SWIPE_OT("Swipe/OT", Screen.OTSwipeLateRequest.route), LEAVE_VACATION(
        "Leave/Vacation",
        Screen.LeaveOrVacationRequest.route
    )
}