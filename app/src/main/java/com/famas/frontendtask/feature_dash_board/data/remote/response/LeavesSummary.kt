package com.famas.frontendtask.feature_dash_board.data.remote.response


import com.google.gson.annotations.SerializedName

data class LeavesSummary(
    @SerializedName("leaves_applied")
    val leavesApplied: Int,
    @SerializedName("leaves_assigned")
    val leavesAssigned: Int,
    @SerializedName("leaves_available")
    val leavesAvailable: String
)