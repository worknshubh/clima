package com.example.clima

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Weatherapi {
    @GET("/v1/forecast.json")
    fun getWeather(
        @Query("key") apikey:String,
        @Query("q") city:String,
        @Query("days") days:String
    ):Call<weatherdata>
}