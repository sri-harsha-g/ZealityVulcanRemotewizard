package com.famas.frontendtask.feature_auth.data.remote.responce


import com.google.gson.annotations.SerializedName

data class AuthResponseData(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("user_type")
    val userType: String
)