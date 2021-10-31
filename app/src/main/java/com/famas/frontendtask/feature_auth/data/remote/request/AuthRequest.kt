package com.famas.frontendtask.feature_auth.data.remote.request

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    @SerializedName("user_name")
    val user_name: String,
    @SerializedName("password")
    val password: String
)
