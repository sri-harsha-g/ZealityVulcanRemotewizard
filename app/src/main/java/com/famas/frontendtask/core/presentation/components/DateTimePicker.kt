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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.famas.frontendtask.core.ui.theme.DefaultShape
import com.famas.frontendtask.core.ui.theme.SpaceMedium
import com.famas.frontendtask.core.ui.theme.SpaceSemiLarge
import com.famas.frontendtask.core.util.Size
import com.famas.frontendtask.core.util.extensions.boldTextStyle
import com.famas.frontendtask.core.util.extensions.height
import com.famas.frontendtask.core.util.extensions.primaryTextStyle
import com.famas.frontendtask.core.util.extensions.secondaryTextStyle
import java.util.*

@Composable
fun DateTimePicker(
    modifier: Modifier = Modifier,
    title: String? = null,
    btnTxt: String = "  Pick date & time",
    selectedDate: String?,
    setDate: (String) -> Unit
) {
    val calendar = remember {
        Calendar.getInstance()
    }
    val context = LocalContext.current

    Column(modifier = modifier) {
        title?.let {
            Text(text = it, style = primaryTextStyle())
        }
        Text(
            text = "format:YYYY-MM-dd HH:MM",
            style = secondaryTextStyle(fontSize = Size.textCaption, alpha = 0.4f)
        )

        PrimaryButton(
            text = btnTxt,
            icon = Icons.Default.CalendarToday,
            color = MaterialTheme.colors.surface,
            textColor = MaterialTheme.colors.onSurface.copy(0.6f),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = SpaceMedium),
            shape = DefaultShape,
            textStyle = primaryTextStyle(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.onSurface,
                alpha = 0.6f
            ),
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
        Column {
            Text(
                text = "Selected date and time:",
                modifier = Modifier.padding(start = SpaceSemiLarge),
                style = boldTextStyle(fontSize = Size.textExtraSize, alpha = 0.6f)
            )
            4.height()
            Text(
                text = selectedDate ?: "",
                modifier = Modifier.padding(start = SpaceSemiLarge),
                style = boldTextStyle(fontSize = Size.textExtraSize, alpha = 0.6f)
            )
        }
    }

}


fun Int.formatDateDigit(): String = if (this < 10) "0$this" else "$this"