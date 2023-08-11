package com.avv2050soft.weatherscope.domain.models.forecast


import com.google.gson.annotations.SerializedName

data class Alert(
    @SerializedName("areas")
    val areas: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("certainty")
    val certainty: String,
    @SerializedName("desc")
    val desc: String,
    @SerializedName("effective")
    val effective: String,
    @SerializedName("event")
    val event: String,
    @SerializedName("expires")
    val expires: String,
    @SerializedName("headline")
    val headline: String,
    @SerializedName("instruction")
    val instruction: String,
    @SerializedName("msgtype")
    val msgtype: String,
    @SerializedName("note")
    val note: String,
    @SerializedName("severity")
    val severity: String,
    @SerializedName("urgency")
    val urgency: String
)