package com.famas.frontendtask.core.presentation.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.famas.frontendtask.core.ui.theme.*
import okhttp3.internal.http.toHttpDateString
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


fun Int.formatDateDigit(): String = if (this<10) "0$this" else "$this"