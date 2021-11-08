package com.famas.frontendtask.core.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.famas.frontendtask.core.ui.theme.SpaceSmall

@Composable
fun DropDown(
    modifier: Modifier = Modifier,
    list: List<String>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    heading: String,
    hint: String
) {
    var notYetSelected by rememberSaveable { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }
    val rotation = if (expanded) 180f else 0f
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = heading,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.align(Alignment.Start)
        )
        Row(
            modifier
                .clip(RoundedCornerShape(25))
                .background(color = MaterialTheme.colors.primary.copy(0.2f))
                .clickable { expanded = true }
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (notYetSelected) hint else list[selectedIndex],
                style = MaterialTheme.typography.button,
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
                )
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
                            if (notYetSelected) notYetSelected = false
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