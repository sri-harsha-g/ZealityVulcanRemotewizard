package com.famas.frontendtask.core.presentation.components

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.famas.frontendtask.core.ui.theme.DefaultShape
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSemiLarge
import java.util.*

@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    selectedTime: String,
    title: String? = null,
    btnTxt: String = "Pick time",
    setTime: (String) -> Unit
) {
    val calendar = remember {
        Calendar.getInstance()
    }
    val context = LocalContext.current

    Column(modifier = modifier) {
        title?.let {
            Text(text = it, style = MaterialTheme.typography.subtitle1)
        }

        PrimaryButton(
            text = btnTxt,
            icon = Icons.Default.AccessTime,
            color = MaterialTheme.colors.primary.copy(alpha = 0.2f),
            textColor = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceMedium),
            shape = DefaultShape,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TimePickerDialog(
                context,
                { _, i, i2 ->
                    setTime("selected time: ${i}:$i2")
                },
                calendar[Calendar.HOUR_OF_DAY],
                calendar[Calendar.MINUTE],
                true,
            ).show()
        }
        EmphasisText(
            text = selectedTime,
            modifier = Modifier.padding(start = SpaceSemiLarge),
            style = MaterialTheme.typography.h5
        )
    }
}