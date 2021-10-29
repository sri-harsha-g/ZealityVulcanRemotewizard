package com.famas.frontendtask.feature_requests.presentation

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
import com.famas.frontendtask.core.presentation.components.TimePicker
import com.famas.frontendtask.core.ui.theme.SpaceLarge
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSemiLarge

@Composable
fun OTSwipeLateRequestScreen() {

    var selectedDate by remember {
        mutableStateOf("selected date:")
    }

    var selectedTime by remember {
        mutableStateOf("selected time:")
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
            modifier = Modifier.padding(SpaceMedium)
        )
        Spacer(modifier = Modifier.height(SpaceMedium))
        DatePicker(
            title = "Select date for OT/Swipe/Late",
            selectedDate = selectedDate,
            setDate = { selectedDate = it }
        )

        Spacer(modifier = Modifier.height(SpaceSemiLarge))
        TimePicker(
            title = "Select time for OT/Swipe/Late",
            selectedTime = selectedTime,
            setTime = { selectedTime = it }
        )

        Spacer(modifier = Modifier.height(SpaceLarge))
        OutlinedTextField(
            value = reason, onValueChange = { reason = it }, maxLines = 5, modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            label = { Text(text = "Reason") }
        )

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(text = "APPLY") {

        }
    }
}