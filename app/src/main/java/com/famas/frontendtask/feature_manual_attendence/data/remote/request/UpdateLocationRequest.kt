package com.famas.frontendtask.feature_manual_attendence.data.remote.request

import com.google.gson.annotations.SerializedName

data class UpdateLocationRequest(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("lat")
    val latitude: String,
    @SerializedName("lang")
    val longitude: String,
    @SerializedName("time")
    val time: String
)
