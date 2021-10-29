package com.famas.frontendtask.core.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.famas.frontendtask.core.ui.theme.SpaceSmall

@Composable
fun DropDown(
    modifier: Modifier = Modifier,
    heading: String,
    hint: String
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }

    val rotation = if (expanded) 180f else 0f
    Column {
        Text(text = heading, style = MaterialTheme.typography.subtitle1)
        Row(
            modifier
                .clip(RoundedCornerShape(25))
                .background(color = MaterialTheme.colors.primary.copy(0.2f))
                .clickable { expanded = true }
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (selectedItem.isNotBlank()) selectedItem else hint,
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
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            val items =
                listOf(
                    "item item 1",
                    "item item 1",
                    "item item 1",
                    "item item 1",
                    "item item 1",
                    "item item 1",
                    "item item 1"
                )
            items.forEach { item ->
                OutlinedButton(
                    modifier = Modifier.padding(
                        horizontal = 4.dp,
                        vertical = 2.dp
                    ),
                    onClick = {
                        expanded = false
                        selectedItem = item
                    },
                    enabled = selectedItem != item
                ) {
                    Text(text = item)
                }
            }
        }
    }
}