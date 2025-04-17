package com.example.clima

data class Hour(
    val chance_of_rain: Int,
    val condition: ConditionXX,
    val humidity: Int,
    val is_day: Int,
    val temp_c: Double,
    val time: String,
    val time_epoch: Int,
    val wind_kph: Double
)