package com.famas.frontendtask.core.presentation.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import com.famas.frontendtask.core.presentation.util.toDp
import com.famas.frontendtask.core.ui.theme.DefaultShape
import com.famas.frontendtask.core.ui.theme.SpaceSmall
import com.famas.frontendtask.core.util.extensions.primaryTextStyle

@ExperimentalAnimationApi
@Composable
fun DropDown(
    modifier: Modifier = Modifier,
    dropDownItems: List<String>,
    selectedIndex: Int?,
    onItemSelected: (Int) -> Unit,
    heading: String,
    hint: String,
    inactiveIndicatorColor: Color = Color.LightGray,
    indicatorColor: Color = MaterialTheme.colors.primary,
    indicatorWidth: Dp = 5.dp,
    shape: Shape = MaterialTheme.shapes.small
) {
    var expanded by remember { mutableStateOf(false) }
    val rotation = if (expanded) 180f else 0f
    var rowSize by remember { mutableStateOf(Size.Zero) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = heading,
            style = primaryTextStyle(),
            modifier = Modifier.align(Alignment.Start)
        )
        Row(
            modifier
                .clip(DefaultShape)
                .background(color = MaterialTheme.colors.surface)
                .clickable { expanded = true }
                .padding(6.dp)
                .onGloballyPositioned { rowSize = it.size.toSize() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedIndex?.let { dropDownItems[it] } ?: hint,
                style = primaryTextStyle(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.onSurface,
                    alpha = 0.6f
                ),
                modifier = Modifier.padding(SpaceSmall)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "drop down menu",
                modifier = Modifier.graphicsLayer(
                    rotationZ = animateFloatAsState(
                        targetValue = rotation,
                        animationSpec = tween(800)
                    ).value
                ),
                tint = contentColorFor(backgroundColor = MaterialTheme.colors.surface).copy(0.6f)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },
                modifier = Modifier.width(rowSize.width.toDp())
            ) {
                dropDownItems.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onItemSelected(index)
                        },
                        enabled = selectedIndex != index
                    ) {
                        Text(text = item)
                    }
                }
            }
        }
    }
}