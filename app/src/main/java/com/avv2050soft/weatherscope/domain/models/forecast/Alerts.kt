package com.avv2050soft.weatherscope.domain.models.forecast


import com.google.gson.annotations.SerializedName

data class Alerts(
    @SerializedName("alert")
    val alert: List<Alert>
)