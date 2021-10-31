package com.famas.frontendtask.feature_manual_attendence.data.remote.response


import com.google.gson.annotations.SerializedName

data class BasicResponse(
    @SerializedName("data")
    val `data`: List<Any>,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("status")
    val status: String
)