package com.famas.frontendtask.feature_loans.presentation.screen_apply_loan

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Money
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.famas.frontendtask.R
import com.famas.frontendtask.core.presentation.components.DropDown
import com.famas.frontendtask.core.presentation.components.PrimaryButton
import com.famas.frontendtask.core.ui.theme.SpaceLarge
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSemiLarge
import com.famas.frontendtask.core.ui.theme.defaultScreenPadding
import com.famas.frontendtask.core.util.extensions.primaryTextStyle

@ExperimentalAnimationApi
@Composable
fun ApplyLoanScreen(
    viewModel: ApplyLoanViewModel = hiltViewModel()
) {
    val state = viewModel.applyLoanState.value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .defaultScreenPadding(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Spacer(modifier = Modifier.height(SpaceLarge))
            Column {
                Text(text = "Please enter the amount here", style = primaryTextStyle())
                OutlinedTextField(
                    value = state.amount ?: "",
                    onValueChange = { viewModel.onEvent(ApplyLoanEvent.OnEnterAmount(it)) },
                    label = { Text(text = "amount", style = primaryTextStyle(color = MaterialTheme.colors.primary)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = SpaceMedium),
                    placeholder = {
                        Text(text = "₹0")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Money, contentDescription = "money")
                    },
                    textStyle = primaryTextStyle()
                )
                
                Spacer(modifier = Modifier.height(SpaceSemiLarge))
                DropDown(
                    dropDownItems = remember { state.applyLoanDropDown.list.map { it.label } },
                    selectedIndex = state.applyLoanDropDown.selectedIndex,
                    onItemSelected = { viewModel.onEvent(ApplyLoanEvent.OnSelectLoan(it)) },
                    heading = stringResource(R.string.pls_select_loan_type),
                    hint = stringResource(R.string.loan_type_hint),
                    modifier = Modifier.padding(vertical = SpaceSemiLarge)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(SpaceMedium))
            PrimaryButton(text = stringResource(id = R.string.submit)) {

            }
            Spacer(modifier = Modifier.height(SpaceMedium))
        }
    }
}