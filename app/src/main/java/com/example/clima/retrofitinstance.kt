package com.example.clima

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object retrofitinstance{
    private const val BASE_URL = "https://api.weatherapi.com"
    val instance:Weatherapi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(Weatherapi::class.java)
    }
}