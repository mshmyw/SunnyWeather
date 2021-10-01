package com.sunnyweather.android.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.logic.model.Live

class WeatherViewModel: ViewModel() {
    private val searchLiveData = MutableLiveData<String>()
    val placeList = ArrayList<Live>()

    var placeName = ""
    val weatherLiveData = Transformations.switchMap(searchLiveData) {
        query ->
        Repository.refreshWeather(query)

    }

    fun refreshWeather(query: String) {
        searchLiveData.value = query
    }
}