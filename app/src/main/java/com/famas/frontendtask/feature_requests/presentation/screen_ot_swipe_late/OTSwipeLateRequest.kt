package com.famas.frontendtask.feature_requests.presentation.screen_ot_swipe_late

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.famas.frontendtask.core.presentation.components.DateTimePicker
import com.famas.frontendtask.core.presentation.components.DropDown
import com.famas.frontendtask.core.presentation.components.PrimaryButton
import com.famas.frontendtask.core.ui.theme.SpaceLarge
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSemiLarge
import kotlinx.coroutines.launch

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

    val lazyListState = rememberLazyListState()

    val scope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(SpaceSemiLarge),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        state = lazyListState
    ) {
        item {
            DropDown(
                heading = "Select permission type",
                hint = "permission type",
                modifier = Modifier.padding(SpaceMedium)
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            DateTimePicker(
                title = "Select from date and time",
                selectedDate = selectedDate,
                setDate = { selectedDate = it }
            )

            Spacer(modifier = Modifier.height(SpaceSemiLarge))
            /*TimePicker(
                title = "Select time for OT/Swipe/Late",
                selectedTime = selectedTime,
                setTime = { selectedTime = it }
            )*/
            DateTimePicker(
                title = "Select to date and time",
                selectedDate = selectedDate,
                setDate = { selectedDate = it }
            )

            Spacer(modifier = Modifier.height(SpaceLarge))
            OutlinedTextField(
                value = reason,
                onValueChange = {
                    reason = it
                    if (it.length == 1) {
                        scope.launch {
                            lazyListState.animateScrollToItem(1)
                        }
                    }
                },
                maxLines = 6,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                label = { Text(text = "Reason") }
            )
        }

        item {
            PrimaryButton(text = "APPLY", modifier = Modifier.padding(horizontal = SpaceMedium)) {

            }
        }
    }
}