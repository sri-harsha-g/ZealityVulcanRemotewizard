package com.famas.frontendtask.feature_requests.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.famas.frontendtask.core.ui.theme.SpaceLarge

@Composable
fun RequestBottomSheetContent(
    toggleBottomSheet: () -> Unit,
    onRequestBtnLtClick: (RequestButtonType) -> Unit,
    isExpanded: Boolean = false
) {
    val rotationZ = animateFloatAsState(targetValue = if (isExpanded) 180f else 0f)
    Column {
        IconButton(onClick = toggleBottomSheet, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "",
                modifier = Modifier.graphicsLayer(rotationZ = rotationZ.value)
            )
        }
        Spacer(modifier = Modifier.height(SpaceLarge))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedButton(onClick = { onRequestBtnLtClick(RequestButtonType.SWIPE_OT) }) {
                Text(text = "OT/SWIPE/LATE")
            }

            OutlinedButton(onClick = { onRequestBtnLtClick(RequestButtonType.LEAVE_VACATION) }) {
                Text(text = "LEAVE/VACATION")
            }
        }
        Spacer(modifier = Modifier.height(SpaceLarge))
    }
}