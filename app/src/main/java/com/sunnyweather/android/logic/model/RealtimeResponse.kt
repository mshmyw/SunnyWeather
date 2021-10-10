package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName

data class RealtimeResponse(val status: String,
                         val lives: List<Live>
                         ) {
    data class Live(val city: String, val adcode: String,
                    val weather: String,
                    val temperature: String,
                    val reporttime: String,
                    val humidity: String, // 空气湿度
                    val province: String)

    data class Location(val lng: String, val lat: String)
}



