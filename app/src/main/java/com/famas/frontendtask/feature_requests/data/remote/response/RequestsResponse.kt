package com.famas.frontendtask.feature_requests.data.remote.response


import com.google.gson.annotations.SerializedName

data class RequestsResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("status")
    val status: String
)