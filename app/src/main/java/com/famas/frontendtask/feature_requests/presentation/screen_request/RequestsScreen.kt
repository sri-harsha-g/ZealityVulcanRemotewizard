package com.famas.frontendtask.feature_requests.presentation.screen_request

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.famas.frontendtask.core.navigation.Screen
import com.famas.frontendtask.core.presentation.components.EmphasisText
import com.famas.frontendtask.core.ui.theme.*
import com.famas.frontendtask.feature_requests.presentation.components.RequestBtnLayout
import com.famas.frontendtask.feature_requests.presentation.screen_request_status.UserRequestsStatusScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun RequestScreen(
    isAdmin: Boolean = true,
    navController: NavController
) {
    var requestDialogBtnState by remember { mutableStateOf(RequestDialogBtnState()) }
    val firstLazyListState = rememberLazyListState()
    val secondLazyListState = rememberLazyListState()

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
                    if (isAdmin) requestDialogBtnState = RequestDialogBtnState(showDialog = true, dialogFor = it)
                    else navController.navigate(it.route)
                },
                isAdmin = isAdmin
            )
        }
    }

    if (requestDialogBtnState.showDialog) {
        Dialog(onDismissRequest = { requestDialogBtnState = RequestDialogBtnState(showDialog = false) }) {
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
                                requestDialogBtnState.dialogFor?.let {
                                    navController.navigate(it.route)
                                }
                                requestDialogBtnState = RequestDialogBtnState(showDialog = false)
                            }
                        ) {
                            Text(text = "APPLY")
                        }

                        Button(
                            onClick = {
                                requestDialogBtnState = RequestDialogBtnState(showDialog = false)
                                navController.navigate(Screen.PendingRequests.route)
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