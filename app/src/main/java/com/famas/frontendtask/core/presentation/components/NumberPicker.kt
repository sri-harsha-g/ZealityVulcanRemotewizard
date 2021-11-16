package com.famas.frontendtask.core.presentation.components

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.famas.frontendtask.R
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.appIconColor
import com.famas.frontendtask.core.util.extensions.boldTextStyle
import com.famas.frontendtask.core.util.extensions.height
import com.famas.frontendtask.core.util.extensions.radius
import com.famas.frontendtask.core.util.extensions.secondaryTextStyle


@ExperimentalAnimationApi
@Composable
fun NumberPicker(
    modifier: Modifier = Modifier,
    title: String,
    list: List<Int>,
    currentIndex: Int?,
    setIndex: (Int) -> Unit,
    textStyle: TextStyle = boldTextStyle()
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Column(modifier = modifier) {
        Text(title, style = secondaryTextStyle())
        8.height()
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpaceMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = {
                    currentIndex?.let {
                        if (it > 0) setIndex(it - 1)
                    } ?: kotlin.run {
                        expanded = true
                    }
                },
                border = BorderStroke(1.dp, appIconColor),
                shape = 12.radius(),
                modifier = Modifier
                    .width(40.dp)
                    .height(50.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.t1_ic_minus),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                    contentDescription = ""
                )
            }
            AnimatedContent(
                targetState = currentIndex?.let { list[it] } ?: 0,
                modifier = Modifier
                    .clickable { expanded = true }
                    .padding(SpaceMedium),
                transitionSpec = {
                    // Compare the incoming number with the previous number.
                    if (targetState > initialState) {
                        // If the target number is larger, it slides up and fades in
                        // while the initial (smaller) number slides up and fades out.
                        slideInVertically({ height -> -height }) + fadeIn() with
                                slideOutVertically({ height -> height }) + fadeOut()
                    } else {
                        // If the target number is smaller, it slides down and fades in
                        // while the initial number slides down and fades out.
                        slideInVertically({ height -> height }) + fadeIn() with
                                slideOutVertically({ height -> -height }) + fadeOut()
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
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            OutlinedButton(
                onClick = {
                    currentIndex?.let {
                        if (it < list.size - 1) setIndex(it + 1)
                    } ?: setIndex(1)
                },
                border = BorderStroke(1.dp, appIconColor),
                shape = 12.radius(),
                modifier = Modifier
                    .width(40.dp)
                    .height(50.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.t1_ic_plus),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                    contentDescription = ""
                )
            }
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
                    enabled = currentIndex?.let { list[it] != num } ?: true
                ) {
                    Text(text = num.toString())
                }
            }
        }
    }


    /*Column {
        ConstraintLayout(
            modifier = modifier
                .defaultMinSize(minHeight = 30.dp, minWidth = 30.dp)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colors.surface)
                .padding(SpaceSemiSmall)
        ) {
            val (animContentLt, upBtn, downBtn) = createRefs()

            AnimatedContent(
                targetState = currentIndex?.let { list[it] } ?: 0,
                modifier = Modifier
                    .constrainAs(animContentLt) {
                        start.linkTo(parent.start)
                        centerVerticallyTo(parent)
                    }
                    .clickable { expanded = true }
                    .padding(SpaceMedium),
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
                        currentIndex?.let {
                            if (it < list.size - 1) setIndex(it + 1)
                        }
                    }
                    .padding(2.dp),
                tint = contentColorFor(backgroundColor = MaterialTheme.colors.surface)
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
                    .clickable {
                        currentIndex?.let {
                            if (it > 0) setIndex(it - 1)
                        }
                    }
                    .padding(2.dp),
                tint = contentColorFor(backgroundColor = MaterialTheme.colors.surface)
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
                    enabled = currentIndex?.let { list[it] != num } ?: true
                ) {
                    Text(text = num.toString())
                }
            }
        }
    }*/
}