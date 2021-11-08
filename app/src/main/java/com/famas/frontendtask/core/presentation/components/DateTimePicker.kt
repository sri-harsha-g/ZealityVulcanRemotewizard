package com.famas.frontendtask.core.presentation.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.famas.frontendtask.core.ui.theme.QuarterCornerShape
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSemiLarge
import java.util.*

@Composable
fun DateTimePicker(
    modifier: Modifier = Modifier,
    title: String? = null,
    btnTxt: String = "Pick date & time",
    selectedDate: String?,
    setDate: (String) -> Unit
) {
    val calendar = remember {
        Calendar.getInstance()
    }
    val context = LocalContext.current

    Column(modifier = modifier) {
        title?.let {
            Text(text = it, style = MaterialTheme.typography.subtitle1)
        }
        EmphasisText(text = "format:YYYY-MM-dd HH:MM", style = MaterialTheme.typography.caption)

        PrimaryButton(
            text = btnTxt,
            icon = Icons.Default.CalendarToday,
            color = MaterialTheme.colors.primary.copy(alpha = 0.2f),
            textColor = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceMedium),
            shape = QuarterCornerShape,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DatePickerDialog(
                context,
                { _, year, month, date ->
                    TimePickerDialog(
                        context,
                        { _, h, m ->
                            setDate("$year-${month.formatDateDigit()}-${date.formatDateDigit()} ${h.formatDateDigit()}:${m.formatDateDigit()}")
                        },
                        calendar[Calendar.HOUR_OF_DAY],
                        calendar[Calendar.MINUTE],
                        true,
                    ).show()
                },
                calendar.get(Calendar.YEAR),
                calendar[Calendar.MONTH],
                calendar[Calendar.DATE]
            ).show()
        }
        EmphasisText(
            text = "Select date and time: ${selectedDate ?: ""}",
            modifier = Modifier.padding(start = SpaceSemiLarge),
            style = MaterialTheme.typography.h5
        )
    }

}


fun Int.formatDateDigit(): String = if (this < 10) "0$this" else "$this"