package com.famas.frontendtask.feature_dash_board.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.famas.frontendtask.core.presentation.components.EmphasisText
import com.famas.frontendtask.core.presentation.components.PrimaryButton
import com.famas.frontendtask.core.ui.theme.*

@Composable
fun DashBoardScreen(
    scaffoldState: ScaffoldState,
    navController: NavController,
    dashboardViewModel: DashboardViewModel = hiltViewModel()
) {
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current

    val state = dashboardViewModel.dashboardState.value
    val data = state.source?.data?.first()
    val hoursSummary = data?.hoursSummary?.first()

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
                    Spacer(modifier = Modifier.height(SpaceSemiSmall))
                    Text(text = "Summary", style = MaterialTheme.typography.h5)
                    Text(text = "total: ${hoursSummary?.workedHours}", style = MaterialTheme.typography.body1)
                    Text(text = "average: ${hoursSummary?.avgWorkHours}", style = MaterialTheme.typography.body1)
                    Text(text = "days per month: ${hoursSummary?.daysPerMonth}", style = MaterialTheme.typography.body1)
                    Text(text = "present days : ${hoursSummary?.presentDays}", style = MaterialTheme.typography.body1)
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

            if (state.loading) {
                Dialog(onDismissRequest = { /*TODO*/ }) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}