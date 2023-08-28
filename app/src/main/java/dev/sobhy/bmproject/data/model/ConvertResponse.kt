package dev.sobhy.bmproject.data.model


import com.google.gson.annotations.SerializedName

data class ConvertResponse(
    val result: Double,
    @SerializedName("time_last_update_utc")
    val timeLastUpdateUtc: String
)