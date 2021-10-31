package com.famas.frontendtask.feature_auth.data.remote.responce


import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("status")
    val status: String
)