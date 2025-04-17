package com.example.clima

data class Day(
    val avghumidity: Int,
    val avgtemp_c: Double,
    val condition: Condition,
    val daily_chance_of_rain: Int,
    val maxwind_kph: Double
)