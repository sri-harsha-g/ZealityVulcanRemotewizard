package com.famas.frontendtask.feature_requests.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.ExperimentalUnitApi
import com.famas.frontendtask.core.presentation.components.EmphasisText
import com.famas.frontendtask.core.ui.theme.SpaceLarge
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSemiSmall
import com.famas.frontendtask.core.ui.theme.SpaceSmall

@OptIn(ExperimentalUnitApi::class)
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun EmployeeCard(
    modifier: Modifier = Modifier,
    showButtons: Boolean = false,
    showUserDetails: Boolean = true,
    onAccept: () -> Unit = {},
    onReject: () -> Unit = {}
) {
    var expand by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        onClick = { expand = !expand },
        elevation = SpaceSemiSmall
    ) {
        Column(modifier = Modifier.padding(horizontal = SpaceMedium, vertical = SpaceLarge)) {
            HorizontalCardDetailItem(head = "Request type: ", text = "Request type")
            if (showUserDetails)
                HorizontalCardDetailItem(head = "Employee number: ", text = "1245313")

            HorizontalCardDetailItem(head = "No of days/hours: ", text = "10days")
            HorizontalCardDetailItem(head = "Requested date: ", text = "12-09-2021")

            AnimatedVisibility(visible = expand) {
                Column {
                    if (showUserDetails) {
                        HorizontalCardDetailItem(head = "Employee name: ", text = "Sai kunkupudi")
                        HorizontalCardDetailItem(head = "Branch: ", text = "Chemical")
                        HorizontalCardDetailItem(head = "Department: ", text = "Executive")
                    }
                    VerticalCardDetailItem(
                        head = "Reason:",
                        text = "Reason is the reason that might not be the reason.Reason Reason is the reason that might not be the reason.Reason Reason Reason is the reason that might not be the reason."
                    )
                    if (showButtons) {
                        Divider()
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            TextButton(onClick = onAccept, modifier = Modifier.weight(0.5f)) {
                                Text(text = "Accept")
                            }
                            TextButton(onClick = onReject, modifier = Modifier.weight(0.5f)) {
                                Text(text = "Reject")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HorizontalCardDetailItem(
    head: String,
    text: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SpaceSemiSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = head,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(end = SpaceSmall)
                .alignByBaseline(),
            color = Color.DarkGray
        )
        EmphasisText(
            text = text,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.alignByBaseline()
        )
    }
}

@Composable
fun VerticalCardDetailItem(
    head: String,
    text: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SpaceSemiSmall)
    ) {
        Text(
            text = head,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(end = SpaceSmall)
        )
        EmphasisText(
            text = text,
            style = MaterialTheme.typography.body1,
        )
    }
}