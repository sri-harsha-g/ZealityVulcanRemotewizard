package com.famas.frontendtask.feature_requests.data.remote.request

import com.google.gson.annotations.SerializedName

data class PermissionRequest(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("leave_type_id")
    val leaveTypeId: Int,
    @SerializedName("start_datetime")
    val startDatetime: String,
    @SerializedName("end_datetime")
    val endDatetime: String,
    @SerializedName("total_hours")
    val totalHours: Int,
    @SerializedName("total_days")
    val totalDays: Int,
    @SerializedName("permission_desc")
    val permissionDesc: String,
    @SerializedName("create_date_time")
    val createDateTime: String
)