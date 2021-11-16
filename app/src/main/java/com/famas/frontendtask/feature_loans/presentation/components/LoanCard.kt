package com.famas.frontendtask.feature_loans.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.feature_loans.presentation.screen_apply_loan.LoanType
import com.famas.frontendtask.feature_requests.presentation.components.HorizontalCardDetailItem

@Composable
fun LoanCard(
    modifier: Modifier = Modifier,
    showReviewButtons: Boolean = false,
    onAccept: () -> Unit = {},
    onReject: () -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
    ) {
        Column(modifier = Modifier.padding(SpaceMedium)) {
            HorizontalCardDetailItem(head = "Username:", text = "venky")
            HorizontalCardDetailItem(head = "Employee id:", text = "5465d45416")
            HorizontalCardDetailItem(head = "contact number:", text = "1234567890")
            HorizontalCardDetailItem(head = "type:", text = LoanType.LOAN.label)
            HorizontalCardDetailItem(head = "Amount:", text = "25000")
            if (showReviewButtons) {
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
            } else HorizontalCardDetailItem(head = "Status", text = "approved")
        }
    }
}