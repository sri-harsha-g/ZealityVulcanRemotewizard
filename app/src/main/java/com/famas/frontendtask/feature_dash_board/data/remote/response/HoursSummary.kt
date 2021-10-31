package com.famas.frontendtask.feature_dash_board.data.remote.response


import com.google.gson.annotations.SerializedName

data class HoursSummary(
    @SerializedName("avg_work_hours")
    val avgWorkHours: String,
    @SerializedName("days_per_month")
    val daysPerMonth: Int,
    @SerializedName("present_days")
    val presentDays: String,
    @SerializedName("worked_hours")
    val workedHours: Double
)