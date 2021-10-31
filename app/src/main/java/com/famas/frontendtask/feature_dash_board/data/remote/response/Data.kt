package com.famas.frontendtask.feature_dash_board.data.remote.response


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("branch_name")
    val branchName: String,
    @SerializedName("department_name")
    val departmentName: String,
    @SerializedName("grade_name")
    val gradeName: String,
    @SerializedName("hours_summary")
    val hoursSummary: List<HoursSummary>,
    @SerializedName("leaves_summary")
    val leavesSummary: List<LeavesSummary>,
    @SerializedName("position")
    val position: String
)