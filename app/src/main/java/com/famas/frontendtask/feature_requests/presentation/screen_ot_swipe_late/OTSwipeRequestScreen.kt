package com.famas.frontendtask.feature_requests.presentation.screen_ot_swipe_late

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.famas.frontendtask.R
import com.famas.frontendtask.core.presentation.components.DateTimePicker
import com.famas.frontendtask.core.presentation.components.DropDown
import com.famas.frontendtask.core.presentation.components.NumberPicker
import com.famas.frontendtask.core.presentation.components.PrimaryButton
import com.famas.frontendtask.core.presentation.util.UiEvent
import com.famas.frontendtask.core.ui.theme.SpaceLarge
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSemiLarge
import com.famas.frontendtask.core.ui.theme.defaultScreenPadding
import com.famas.frontendtask.core.util.extensions.primaryTextStyle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun OTSwipeLateRequestScreen(
    viewModel: OTSwipeViewModel = hiltViewModel()
) {

    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val state = viewModel.otSwipeState.value

    LaunchedEffect(key1 = true, block = {
        viewModel.uiEventFlow.collectLatest {
            when (it) {
                is UiEvent.ShowSnackBar -> {

                }
                is UiEvent.OnNavigate -> {

                }
            }
        }
    })

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .defaultScreenPadding(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        state = lazyListState
    ) {
        item {
            DropDown(
                heading = stringResource(R.string.select_perm_type),
                hint = stringResource(R.string.perm_type),
                modifier = Modifier.padding(vertical = SpaceMedium),
                list = state.permissionDDState.list.map { it.toString() },
                selectedIndex = state.permissionDDState.selectedIndex,
                onSelected = { viewModel.onEvent(OTSwipeEvent.OnSelectPerType(it)) }
            )

            Spacer(modifier = Modifier.height(SpaceMedium))
            DateTimePicker(
                title = stringResource(id = R.string.select_date_time),
                selectedDate = state.selectedDate,
                setDate = { viewModel.onEvent(OTSwipeEvent.SetSelectedDate(it)) },
            )

            Spacer(modifier = Modifier.height(SpaceSemiLarge))
            NumberPicker(
                list = state.hoursPicker.list,
                currentIndex = state.hoursPicker.selectedIndex,
                setIndex = { viewModel.onEvent(OTSwipeEvent.SetHour(it)) },
                title = "Pick the required hours"
            )

            Spacer(modifier = Modifier.height(SpaceLarge))
            NumberPicker(
                list = state.minutesPicker.list,
                currentIndex = state.minutesPicker.selectedIndex,
                setIndex = { viewModel.onEvent(OTSwipeEvent.SetMinute(it)) },
                title = "Pick the required minutes"
            )

            Spacer(modifier = Modifier.height(SpaceLarge))
            OutlinedTextField(
                value = state.reason,
                onValueChange = {
                    viewModel.onEvent(OTSwipeEvent.SetReason(it))
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
                label = { Text(text = stringResource(R.string.reson), style = primaryTextStyle(MaterialTheme.colors.primary)) },
                textStyle = primaryTextStyle()
            )
        }

        item {
            Spacer(modifier = Modifier.height(SpaceMedium))
            PrimaryButton(
                text = stringResource(R.string.apply_btn_txt)
            ) {
                viewModel.onEvent(OTSwipeEvent.OnClickApply)
            }
            Spacer(modifier = Modifier.height(SpaceMedium))
        }
    }
}