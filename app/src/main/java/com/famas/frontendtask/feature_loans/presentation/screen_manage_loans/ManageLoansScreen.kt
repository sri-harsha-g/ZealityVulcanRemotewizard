package com.famas.frontendtask.feature_loans.presentation.screen_manage_loans

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSemiSmall
import com.famas.frontendtask.feature_loans.presentation.components.LoanCard
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun ManageLoansScreen(
    modifier: Modifier = Modifier,
    viewModel: ManageLoansViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(initialPage = 0)
    val tabs = remember { ManageLoanTabs.values() }
    val coroutine = rememberCoroutineScope()

    Column(modifier = modifier) {
        TabRow(selectedTabIndex = pagerState.currentPage, indicator = {
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, it)
            )
        }) {
            tabs.forEachIndexed { index, manageLoanTab ->
                Tab(selected = pagerState.currentPage == index, onClick = {
                    coroutine.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
                ) {
                    Text(text = manageLoanTab.label, modifier = Modifier.padding(SpaceMedium))
                }
            }
        }


        HorizontalPager(count = tabs.size, state = pagerState) { page ->
            when (page) {
                ManageLoanTabs.PENDING.ordinal -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = SpaceMedium),
                        state = rememberLazyListState()
                    ) {
                        items(15) {
                            LoanCard(
                                showReviewButtons = true,
                                onReject = {},
                                onAccept = {},
                                modifier = Modifier.padding(SpaceSemiSmall)
                            )
                        }
                    }
                }

                ManageLoanTabs.REVIEWED.ordinal -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = SpaceMedium),
                        state = rememberLazyListState()
                    ) {
                        items(15) {
                            LoanCard(
                                showReviewButtons = false,
                                modifier = Modifier.padding(SpaceSemiSmall)
                            )
                        }
                    }
                }
            }
        }
    }
}

enum class ManageLoanTabs(val label: String) {
    PENDING("Pending"), REVIEWED("Reviewed")
}