package com.famas.frontendtask.feature_requests.presentation.screen_request_status

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSmall
import com.famas.frontendtask.feature_requests.presentation.components.EmployeeCard
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun UserRequestsStatusScreen(
    modifier: Modifier = Modifier,
    firstLazyListState: LazyListState = rememberLazyListState(),
    secondLazyListState: LazyListState = rememberLazyListState()
) {
    val pagerState = rememberPagerState(initialPage = 0)
    val tabs = remember { listOf(RequestStatusTab.Pending, RequestStatusTab.Approved) }
    val coroutine = rememberCoroutineScope()

    Column(modifier = modifier) {
        TabRow(selectedTabIndex = pagerState.currentPage, indicator = {
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, it)
            )
        }) {
            tabs.forEachIndexed { index, requestStatusTab ->
                Tab(selected = pagerState.currentPage == index, onClick = {
                    coroutine.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
                ) {
                    Text(text = requestStatusTab.label, modifier = Modifier.padding(SpaceMedium))
                }
            }
        }


        HorizontalPager(count = tabs.size, state = pagerState) { page ->
            when (page) {
                0 -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = SpaceMedium),
                        state = firstLazyListState
                    ) {
                        items(15) {
                            EmployeeCard(
                                showUserDetails = false,
                                modifier = Modifier.padding(vertical = SpaceSmall)
                            )
                        }
                    }
                }

                1 -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = SpaceMedium),
                        state = secondLazyListState
                    ) {
                        items(15) {
                            EmployeeCard(
                                showUserDetails = false,
                                modifier = Modifier.padding(vertical = SpaceSmall)
                            )
                        }
                    }
                }
            }
        }
    }
}

enum class RequestStatusTab(val label: String) {
    Pending("Pending"), Approved("Approved")
}