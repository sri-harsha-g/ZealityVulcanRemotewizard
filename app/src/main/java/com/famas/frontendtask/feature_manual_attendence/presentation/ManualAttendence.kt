package com.famas.frontendtask.feature_manual_attendence.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.famas.frontendtask.core.presentation.components.DateTimePicker
import com.famas.frontendtask.core.presentation.components.DropDown
import com.famas.frontendtask.core.presentation.components.PrimaryButton
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSemiLarge
import com.famas.frontendtask.core.ui.theme.SpaceSemiSmall

@Composable
fun ManualAttendance() {

    var selectedDate by remember {
        mutableStateOf("selected date:")
    }

    var selectedTime by remember {
        mutableStateOf("selected time:")
    }

    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(SpaceSemiLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        state = lazyListState
    ) {
        item {
            DropDown(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = SpaceSemiLarge, horizontal = SpaceMedium),
                heading = "Select department",
                hint = "Department"
            )

            DropDown(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = SpaceSemiLarge, horizontal = SpaceMedium),
                heading = "Select Employee",
                hint = "Employee"
            )

            DateTimePicker(
                title = "Select date and time",
                selectedDate = selectedDate,
                setDate = { selectedDate = it }
            )

            /*Spacer(modifier = Modifier.height(SpaceSemiLarge))
            TimePicker(
                title = "Select time for attendance",
                selectedTime = selectedTime,
                setTime = {
                    selectedTime = it
                    scope.launch {
                        lazyListState.animateScrollToItem(1)
                    }
                }
            )*/
        }

        item {
            Spacer(modifier = Modifier.height(SpaceMedium))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                PrimaryButton(text = "Clock-In", modifier = Modifier.padding(SpaceSemiSmall)) {

                }
                PrimaryButton(text = "Clock-Out", modifier = Modifier.padding(SpaceSemiSmall)) {

                }
            }
            Spacer(modifier = Modifier.height(SpaceMedium))

        }
    }
}