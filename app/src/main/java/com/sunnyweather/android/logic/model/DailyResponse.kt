package com.sunnyweather.android.logic.model

data class DailyResponse(val status: String,
val forecasts: List<Forecast>) {
    data class Forecast(val city: String,
                        val adcode: String,
                        val reporttime: String,
                        val province: String,
                        val casts: List<Cast>)
    data class Cast(
        val date: String,
        val week: String,
        val dayweather: String,
        val nightweather: String,
        val daytemp: String,
        val nighttemp: String,
        val daywind: String,
        val nightwind: String,
        val daypower: String,
        val nightpower: String
    )


}
