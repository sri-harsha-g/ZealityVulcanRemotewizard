package com.famas.frontendtask.feature_profile.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.famas.frontendtask.R
import com.famas.frontendtask.core.ui.theme.SpaceMedium

@Composable
fun ViewProfileLt() {
    Column(modifier = Modifier.fillMaxWidth()) {
        ProfileItemLt(caption = "Photo", content = {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_shape),
                    contentDescription = "Employee photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                )
                Text(text = "View full image", color = MaterialTheme.colors.primary)
            }
        })

        ProfileItemLt(caption = "Employee name") {
            Text(
                text = "Venkatesh Paithireddy",
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.subtitle1
            )
        }

        ProfileItemLt(caption = "Employee id") {
            Text(
                text = "1234567890",
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.subtitle1
            )
        }

        ProfileItemLt(caption = "Employee grade") {
            Text(
                text = "F",
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.subtitle1
            )
        }

        ProfileItemLt(caption = "Employee designation") {
            Text(
                text = "Supervisor",
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.subtitle1
            )
        }

        ProfileItemLt(caption = "Employee branch") {
            Text(
                text = "Electrical",
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}


@Composable
fun ProfileItemLt(
    caption: String,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = SpaceMedium, bottom = SpaceMedium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = caption,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f, fill = true)
        )
        Spacer(modifier = Modifier.width(36.dp))
        content()
    }
}