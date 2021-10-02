package com.sunnyweather.android.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.Live

object PlaceDao {
    fun savePlace(place: Live) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }


    fun getSavedPlace(): Live {
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Live::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() = SunnyWeatherApplication.context
        .getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)
}