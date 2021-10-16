package com.sunnyweather.android.logic.network

import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.DailyResponse
import com.sunnyweather.android.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("v3/weather/weatherInfo?key=${SunnyWeatherApplication.TOKEN}")
    fun getRealtimeWeather(@Query("city") query: String): Call<RealtimeResponse>

    @GET("v3/weather/weatherInfo?extensions=all&key=${SunnyWeatherApplication.TOKEN}")
    fun getDailyWeather(@Query("city") query: String): Call<DailyResponse>

}