package com.famas.frontendtask.feature_dash_board.data.remote.response


import com.google.gson.annotations.SerializedName

data class DashBoardResponse(
    @SerializedName("data")
    val `data`: List<DashboardData>,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("status")
    val status: String
)