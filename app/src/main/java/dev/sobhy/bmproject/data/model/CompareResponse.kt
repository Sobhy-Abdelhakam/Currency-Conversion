package dev.sobhy.bmproject.data.model

import com.google.gson.annotations.SerializedName

data class CompareResponse(
    @SerializedName("conversion_rates")
    val comparisonRates: List<ComparisonRate>,
    @SerializedName("time_last_update_utc")
    val timeLastUpdateUtc: String
)