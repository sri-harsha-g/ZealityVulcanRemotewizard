package com.famas.frontendtask.feature_manual_attendence.data.remote.request

import com.google.gson.annotations.SerializedName

data class AttendanceRequest(
    @SerializedName("fence_id")
    val fenceId: Number,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("clock_in_out")
    val clockInOut: String,
    @SerializedName("shift_id")
    val shiftId: String,
    @SerializedName("lat")
    val latitude: String,
    @SerializedName("lang")
    val longitude: String
)
