package com.famas.frontendtask.core.presentation.components

import android.app.DatePickerDialog
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
import java.util.*

@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    title: String? = null,
    btnTxt: String = "Pick date",
    selectedDate: String,
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

        PrimaryButton(
            text = btnTxt,
            icon = Icons.Default.CalendarToday,
            color = MaterialTheme.colors.primary.copy(alpha = 0.2f),
            textColor = Color.Black,
            modifier = Modifier.fillMaxWidth().padding(SpaceMedium),
            shape = QuarterCornerShape,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DatePickerDialog(
                context,
                { _, i, i2, i3 ->
                    setDate("selected date: $i3-$i2-$i")
                },
                calendar.get(Calendar.YEAR),
                calendar[Calendar.MONTH],
                calendar[Calendar.DATE]
            ).show()
        }
        EmphasisText(
            text = selectedDate,
            modifier = Modifier.padding(start = SpaceSemiLarge),
            style = MaterialTheme.typography.h5
        )
    }

}