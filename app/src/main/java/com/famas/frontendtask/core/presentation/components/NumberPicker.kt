package com.famas.frontendtask.core.presentation.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.famas.frontendtask.core.ui.theme.SpaceLarge
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSemiLarge
import com.famas.frontendtask.core.ui.theme.SpaceSemiSmall


@ExperimentalAnimationApi
@Composable
fun NumberPicker(
    modifier: Modifier = Modifier,
    list: List<Int>,
    currentIndex: Int,
    setIndex: (Int) -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.h6

) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Column {
        ConstraintLayout(
            modifier = modifier
                .defaultMinSize(minHeight = 30.dp, minWidth = 30.dp)
                .padding(SpaceSemiSmall)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colors.primary.copy(0.2f))
                .clickable { expanded = true }
                .padding(SpaceSemiSmall)
        ) {
            val (animContentLt, upBtn, downBtn, drpDown) = createRefs()

            AnimatedContent(
                targetState = list[currentIndex],
                modifier = Modifier.constrainAs(animContentLt) {
                    start.linkTo(parent.start)
                    centerVerticallyTo(parent)
                },
                transitionSpec = {
                    // Compare the incoming number with the previous number.
                    if (targetState > initialState) {
                        // If the target number is larger, it slides up and fades in
                        // while the initial (smaller) number slides up and fades out.
                        slideInVertically({ height -> height }) + fadeIn() with
                                slideOutVertically({ height -> -height }) + fadeOut()
                    } else {
                        // If the target number is smaller, it slides down and fades in
                        // while the initial number slides down and fades out.
                        slideInVertically({ height -> -height }) + fadeIn() with
                                slideOutVertically({ height -> height }) + fadeOut()
                    }.using(
                        // Disable clipping since the faded slide-in/out should
                        // be displayed out of bounds.
                        SizeTransform(clip = false)
                    )
                }
            ) { targetCount ->
                Box {
                    Text(
                        text = "$targetCount",
                        style = textStyle,
                        modifier = Modifier
                            .padding(horizontal = SpaceMedium)
                            .align(Alignment.Center)
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.ArrowUpward,
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(upBtn) {
                        top.linkTo(parent.top)
                        start.linkTo(animContentLt.end)
                        end.linkTo(parent.end)
                    }
                    .clickable {
                        if (currentIndex < list.size - 1) setIndex(
                            currentIndex + 1
                        )
                    }
                    .padding(2.dp)
            )
            Icon(
                imageVector = Icons.Default.ArrowDownward,
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(downBtn) {
                        top.linkTo(upBtn.bottom, SpaceSemiSmall)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(animContentLt.end)
                        end.linkTo(parent.end)
                    }
                    .clickable { if (currentIndex > 0) setIndex(currentIndex - 1) }
                    .padding(2.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            list.forEachIndexed { index, num ->
                DropdownMenuItem(
                    onClick = {
                        setIndex(index)
                        expanded = false
                    },
                    enabled = list[currentIndex] != num
                ) {
                    Text(text = num.toString())
                }
            }
        }
    }
}