package com.famas.frontendtask.feature_requests.presentation.screen_leave_vacation

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.famas.frontendtask.core.presentation.components.DatePicker
import com.famas.frontendtask.core.presentation.components.DropDown
import com.famas.frontendtask.core.presentation.components.PrimaryButton
import com.famas.frontendtask.core.ui.theme.SpaceLarge
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSemiLarge

@Composable
fun LeaveVacationRequest() {

    var fromDate by remember {
        mutableStateOf("selected date:")
    }
    var toDate by remember {
        mutableStateOf("selected date:")
    }

    var reason by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(SpaceSemiLarge)
            .scrollable(rememberScrollState(), Orientation.Vertical),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DropDown(
            heading = "Select permission type",
            hint = "permission type",
            modifier = Modifier.fillMaxWidth().padding(SpaceMedium)
        )

        Spacer(modifier = Modifier.height(SpaceMedium))
        DatePicker(title = "Select from date",selectedDate = fromDate, setDate = { fromDate = it })

        Spacer(modifier = Modifier.height(SpaceMedium))
        DatePicker(title = "Select to date",selectedDate = toDate, setDate = { toDate = it })

        Spacer(modifier = Modifier.height(SpaceSemiLarge))
        OutlinedTextField(
            value = reason, onValueChange = { reason = it }, maxLines = 5, modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            label = { Text(text = "Reason") }
        )

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(text = "APPLY") {

        }
    }
}