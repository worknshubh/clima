package com.example.clima

data class Current(
    val condition: Condition,
    val humidity: Int,
    val is_day: Int,
    val last_updated: String,
    val last_updated_epoch: Int,
    val temp_c: Double,
    val wind_kph: Double
)