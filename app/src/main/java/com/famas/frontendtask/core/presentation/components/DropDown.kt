package com.famas.frontendtask.core.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.famas.frontendtask.core.ui.theme.DefaultShape
import com.famas.frontendtask.core.ui.theme.SpaceSmall
import com.famas.frontendtask.core.util.extensions.primaryTextStyle

@Composable
fun DropDown(
    modifier: Modifier = Modifier,
    list: List<String>,
    selectedIndex: Int?,
    onSelected: (Int) -> Unit,
    heading: String,
    hint: String
) {
    var expanded by remember { mutableStateOf(false) }
    val rotation = if (expanded) 180f else 0f
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
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedIndex?.let { list[it] } ?: hint,
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
                }
            ) {
                list.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onSelected(index)
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