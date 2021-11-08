package com.famas.frontendtask.feature_requests.presentation.screen_requests

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.famas.frontendtask.core.presentation.components.EmphasisText
import com.famas.frontendtask.core.presentation.util.UiEvent
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSemiLarge
import com.famas.frontendtask.core.ui.theme.SpaceSemiSmall
import com.famas.frontendtask.feature_requests.presentation.components.RequestBtnLayout
import com.famas.frontendtask.feature_requests.presentation.screen_request_status.UserRequestsStatusScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun RequestScreen(
    viewModel: RequestsScreenViewModel = viewModel(),
    navController: NavController,
    scaffoldState: ScaffoldState
) {
    val firstLazyListState = rememberLazyListState()
    val secondLazyListState = rememberLazyListState()
    val state = viewModel.requestsScreenState.value
    val coroutine = rememberCoroutineScope()

    LaunchedEffect(key1 = true, block = {
        viewModel.uiEventFlow.collectLatest {
            when (it) {
                is UiEvent.OnNavigate -> {
                    navController.navigate(it.route)
                }

                is UiEvent.ShowSnackBar -> {
                    coroutine.launch {
                        scaffoldState.snackbarHostState.showSnackbar(it.message)
                    }
                }
            }
        }
    })

    Box(modifier = Modifier.fillMaxSize()) {
        UserRequestsStatusScreen(
            modifier = Modifier.fillMaxSize(),
            firstLazyListState = firstLazyListState,
            secondLazyListState = secondLazyListState,
        )

        AnimatedVisibility(
            visible = !firstLazyListState.isScrollInProgress && !secondLazyListState.isScrollInProgress,
            enter = slideInVertically({ it + it / 2 }, spring()),
            exit = slideOutVertically({ it + it / 2 }, spring()),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            RequestBtnLayout(
                modifier = Modifier.padding(SpaceSemiSmall),
                onClick = {
                    viewModel.onEvent(RequestsScreenEvent.OnRequestBtnLtClick(it))
                },
                isAdmin = state.isAdmin
            )
        }
    }

    if (state.requestDialogBtnState.showDialog) {
        Dialog(onDismissRequest = { }) {
            Card {
                Column(modifier = Modifier.padding(SpaceSemiLarge)) {
                    EmphasisText(
                        text = "Please select your action",
                        style = MaterialTheme.typography.subtitle1
                    )
                    Spacer(modifier = Modifier.height(SpaceSemiLarge))
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                viewModel.onEvent(RequestsScreenEvent.OnApply)
                            }
                        ) {
                            Text(text = "APPLY")
                        }

                        Button(
                            onClick = {
                                viewModel.onEvent(RequestsScreenEvent.OnApprove)
                            }
                        ) {
                            Text(text = "APPROVE")
                        }
                    }
                    Spacer(modifier = Modifier.height(SpaceMedium))
                }
            }
        }
    }
}