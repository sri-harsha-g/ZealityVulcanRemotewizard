package com.famas.frontendtask.feature_dash_board.presentation

import com.famas.frontendtask.tracking_service.TrackingService
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.famas.frontendtask.core.presentation.components.EmphasisText
import com.famas.frontendtask.core.presentation.components.PrimaryButton
import com.famas.frontendtask.core.ui.theme.*
import com.famas.frontendtask.core.util.Constants
import com.famas.frontendtask.core.util.hasLocationPermissions

@Composable
fun DashBoardScreen(
    scaffoldState: ScaffoldState,
    onNavigate: (String) -> Unit
) {
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = SpaceSemiLarge)
    ) {
        item {
            Spacer(modifier = Modifier.height(SpaceLarge))
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(SpaceMedium)
                ) {
                    EmphasisText(text = "WORK", modifier = Modifier.padding(vertical = SpaceSmall))
                    Text(text = "Attendance", style = MaterialTheme.typography.subtitle1)
                    Spacer(modifier = Modifier.height(SpaceLarge))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = SpaceSmall),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        PrimaryButton(
                            text = "Clock-In",
                            modifier = Modifier.padding(SpaceSemiSmall)
                        ) {

                        }
                        PrimaryButton(
                            text = "Clock-Out",
                            modifier = Modifier.padding(SpaceSemiSmall)
                        ) {

                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(SpaceSemiLarge))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .height(150.dp)
                        .padding(SpaceSemiLarge)
                ) {
                    EmphasisText(text = "WORKING HOURS")
                    Spacer(modifier = Modifier.height(SpaceSmall))
                    Text(text = "Summary", style = MaterialTheme.typography.h5)
                }
            }

            Spacer(modifier = Modifier.height(SpaceSemiLarge))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .height(150.dp)
                        .padding(SpaceSemiLarge)
                ) {
                    EmphasisText(text = "LEAVE/VACATION")
                    Spacer(modifier = Modifier.height(SpaceSmall))
                    Text(text = "Summary", style = MaterialTheme.typography.h5)
                }
            }

            //Spacer(modifier = Modifier.weight(1f))

            /*EmphasisText(
                text = "BRANCH:",
                modifier = Modifier.padding(horizontal = SpaceSemiSmall),
                style = MaterialTheme.typography.h5
            )
            EmphasisText(
                text = "DEPARTMENT:",
                modifier = Modifier.padding(horizontal = SpaceSemiSmall),
                style = MaterialTheme.typography.h5
            )
            EmphasisText(
                text = "DESIGNATION:",
                modifier = Modifier.padding(horizontal = SpaceSemiSmall),
                style = MaterialTheme.typography.h5
            )*/
        }
    }

    val isTracking by TrackingService.isTracking.observeAsState(false)

    //Starting service
    LaunchedEffect(key1 = isTracking, block = {
        if (!isTracking && hasLocationPermissions(context)) {
            Intent(context, TrackingService::class.java).also {
                it.action = Constants.ACTION_START_OR_RESUME_SERVICE
                context.startService(it)
            }
        }
    })
}