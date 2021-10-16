package com.sunnyweather.android.logic.model

data class Weather(val realtime: RealtimeResponse,
val daily: DailyResponse)