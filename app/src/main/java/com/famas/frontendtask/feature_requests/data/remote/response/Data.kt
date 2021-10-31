package com.famas.frontendtask.feature_requests.data.remote.response


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("approved_by")
    val approvedBy: String,
    @SerializedName("approved_date_time")
    val approvedDateTime: String,
    @SerializedName("create_date_time")
    val createDateTime: String,
    @SerializedName("deleted")
    val deleted: String,
    @SerializedName("end_datetime")
    val endDatetime: String,
    @SerializedName("leave_type_id")
    val leaveTypeId: String,
    @SerializedName("permission_desc")
    val permissionDesc: String,
    @SerializedName("permission_id")
    val permissionId: String,
    @SerializedName("start_datetime")
    val startDatetime: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("total_days")
    val totalDays: String,
    @SerializedName("total_hours")
    val totalHours: String,
    @SerializedName("user_id")
    val userId: String
)