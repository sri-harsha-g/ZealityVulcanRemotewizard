package com.famas.frontendtask.feature_manual_attendence.presentation

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.famas.frontendtask.R
import com.famas.frontendtask.core.presentation.components.DatePicker
import com.famas.frontendtask.core.presentation.components.DropDown
import com.famas.frontendtask.core.presentation.components.PrimaryButton
import com.famas.frontendtask.core.presentation.components.TimePicker
import com.famas.frontendtask.core.ui.theme.SpaceLarge
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSemiLarge
import com.famas.frontendtask.core.ui.theme.SpaceSemiSmall
import kotlinx.coroutines.launch

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

            DatePicker(
                title = "Select date for attendance",
                selectedDate = selectedDate,
                setDate = { selectedDate = it }
            )

            Spacer(modifier = Modifier.height(SpaceSemiLarge))
            TimePicker(
                title = "Select time for attendance",
                selectedTime = selectedTime,
                setTime = {
                    selectedTime = it
                    scope.launch {
                        lazyListState.animateScrollToItem(1)
                    }
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(SpaceMedium))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                PrimaryButton(text = "Clock-In", modifier = Modifier.padding(SpaceSemiSmall)) {

                }
                PrimaryButton(text = "Clock-Out", modifier = Modifier.padding(SpaceSemiSmall)) {

                }
            }
        }
    }
}