package com.famas.frontendtask.core.util

import android.util.Log
import java.util.*

fun getWishString() : String {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY).also {
        Log.d("myTag", "hour is: $it")
    }
    return when(hour) {
        in 0..11 -> "Good morning"

        in 12..16 -> "Good afternoon"

        else -> "Good evening"
    }
}