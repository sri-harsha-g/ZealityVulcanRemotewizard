package com.famas.frontendtask.feature_dash_board.presentation.screen_dashboard

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.famas.frontendtask.core.presentation.util.UiEvent
import com.famas.frontendtask.core.ui.theme.*
import com.famas.frontendtask.core.util.getWishString
import com.famas.frontendtask.feature_dash_board.presentation.components.IndicatorRow
import com.famas.frontendtask.feature_dash_board.presentation.components.ProgressPie
import com.famas.frontendtask.feature_dash_board.presentation.components.ProgressPieStyle
import com.famas.frontendtask.feature_dash_board.presentation.components.ProgressValues
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun DashBoardScreen(
    scaffoldState: ScaffoldState,
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val coroutine = rememberCoroutineScope()
    val wish = remember { getWishString() }

    LaunchedEffect(key1 = true, block = {
        viewModel.uiEventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    coroutine.launch {
                        event.actionLabel?.let {
                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                event.message,
                                it,
                                duration = SnackbarDuration.Indefinite
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                viewModel.onEvent(DashboardEvent.OnRetry)
                            }
                        } ?: kotlin.run {
                            scaffoldState.snackbarHostState.showSnackbar(event.message)
                        }
                    }
                }
                is UiEvent.OnNavigate -> {
                    navController.navigate(event.route)
                }
            }
        }
    })

    val state = viewModel.dashboardState.value
    val data = state.source?.data?.first()
    val hoursSummary = data?.hoursSummary?.first()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .defaultScreenPadding()
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = SpaceMedium), contentAlignment = Center
            ) {
                if (state.loading)
                    CircularProgressIndicator()
            }
        }

        item {
            Spacer(modifier = Modifier.height(SpaceLarge))
            Text(
                text = wish + " ${data?.position ?: "Venkatesh"}!",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.height(SpaceSmall))
        }
        item {
            Spacer(modifier = Modifier.height(SpaceMedium))
            Text(
                text = "WORK",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(top = SpaceLarge, bottom = SpaceSemiLarge),
                color = MaterialTheme.colors.onSurface
            )
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = SpaceSemiSmall,
                shape = TenthPerShape
            ) {
                Column(
                    modifier = Modifier.padding(SpaceSemiLarge)
                ) {
                    Text(
                        text = "Attendance",
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = MaterialTheme.colors.onSurface.copy(0.7f)
                        )
                    )
                    Spacer(modifier = Modifier.height(SpaceMedium))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                    }
                }
            }

            Spacer(modifier = Modifier.height(SpaceMedium))
            Text(
                text = "WORKING HOURS",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(top = SpaceLarge, bottom = SpaceSemiLarge),
                color = MaterialTheme.colors.onSurface
            )
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = SpaceSemiSmall,
                shape = TenthPerShape
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(SpaceSemiLarge)
                ) {
                    val (subHead, indexes, pie) = createRefs()

                    Text(
                        modifier = Modifier.constrainAs(subHead) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        },
                        text = "Summary",
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = MaterialTheme.colors.onSurface.copy(0.7f)
                        )
                    )

                    Column(modifier = Modifier.constrainAs(indexes) {
                        start.linkTo(subHead.start, SpaceLarge)
                        top.linkTo(subHead.bottom, SpaceMedium)
                    }) {
                        IndicatorRow(
                            modifier = Modifier.padding(vertical = 2.dp),
                            color = progress1,
                            indicatorName = "total hours"
                        )
                        IndicatorRow(
                            modifier = Modifier.padding(vertical = 2.dp),
                            color = progress2,
                            indicatorName = "total hours"
                        )
                        IndicatorRow(
                            modifier = Modifier.padding(vertical = 2.dp),
                            color = progress3,
                            indicatorName = "total hours"
                        )
                        IndicatorRow(
                            modifier = Modifier.padding(vertical = 2.dp),
                            color = progress5,
                            indicatorName = "total hours"
                        )
                    }

                    ProgressPie(values = remember {
                        listOf(
                            ProgressValues(/*hoursSummary?.avgWorkHours?.toFloat() ?: 0f*/
                                20f,
                                progress1
                            ),
                            ProgressValues(/*hoursSummary?.daysPerMonth?.toFloat() ?: 0f*/8f,
                                progress2
                            ),
                            ProgressValues(/*hoursSummary?.presentDays?.toFloat() ?: 0f*/ 9f,
                                progress3
                            ),
                            ProgressValues(/*hoursSummary?.workedHours?.toFloat() ?: 0f*/10f,
                                progress5
                            ),
                        )
                    }, modifier = Modifier.constrainAs(pie) {
                        end.linkTo(parent.end, SpaceMedium)
                        centerVerticallyTo(parent)
                    })
                }
            }

            Spacer(modifier = Modifier.height(SpaceMedium))
            Text(
                text = "LEAVE/VACATION",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(top = SpaceLarge, bottom = SpaceSemiLarge),
                color = MaterialTheme.colors.onSurface
            )
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = SpaceSemiSmall,
                shape = TenthPerShape
            ) {
                Column(
                    modifier = Modifier.padding(SpaceSemiLarge)
                ) {
                    Text(
                        text = "Summary",
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = MaterialTheme.colors.onSurface.copy(0.7f)
                        )
                    )
                    Spacer(modifier = Modifier.height(SpaceMedium))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            IndicatorRow(
                                modifier = Modifier.padding(vertical = 2.dp),
                                color = progress5,
                                indicatorName = "total hours"
                            )
                            IndicatorRow(
                                modifier = Modifier.padding(vertical = 2.dp),
                                color = progress2,
                                indicatorName = "total hours"
                            )
                            IndicatorRow(
                                modifier = Modifier.padding(vertical = 2.dp),
                                color = progress4,
                                indicatorName = "total hours"
                            )
                            IndicatorRow(
                                modifier = Modifier.padding(vertical = 2.dp),
                                color = progress1,
                                indicatorName = "total hours"
                            )
                        }
                        ProgressPie(values = remember {
                            listOf(
                                ProgressValues(/*hoursSummary?.avgWorkHours?.toFloat() ?: 0f*/ 20f,
                                    progress5
                                ),
                                ProgressValues(/*hoursSummary?.daysPerMonth?.toFloat() ?: 0f*/8f,
                                    progress2
                                ),
                                ProgressValues(/*hoursSummary?.presentDays?.toFloat() ?: 0f*/ 9f,
                                    progress4
                                ),
                                ProgressValues(/*hoursSummary?.workedHours?.toFloat() ?: 0f*/ 10f,
                                    progress1
                                ),
                            )
                        }, style = ProgressPieStyle.Full)
                    }
                }
            }
        }
    }
}