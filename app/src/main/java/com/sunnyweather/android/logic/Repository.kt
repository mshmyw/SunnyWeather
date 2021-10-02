package com.sunnyweather.android.logic

import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.dao.PlaceDao
import com.sunnyweather.android.logic.model.Live
import com.sunnyweather.android.logic.model.Weather
import com.sunnyweather.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.async

object Repository {
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        // gz 440100 hz 330110 zz 410100 sq 411400
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
    //            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        if(placeResponse.status == "1") {

            val places = placeResponse.lives
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun refreshWeather(query: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                SunnyWeatherNetwork.getRealtimeWeather(query)
            }
            val realtimeResponse = deferredRealtime.await()
            if(realtimeResponse.status == "1") {
                val weather = Weather(realtimeResponse)
                Result.success(weather)
            } else {
                Result.failure(RuntimeException(
                    "realtime response status is ${realtimeResponse.status}"
                ))
            }
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>)
            = liveData<Result<T>>(context) {
        val result = try {
            block()
        } catch (e: Exception) {
            Result.failure<T>(e)
        }
        emit(result)
    }

    fun savePlace(place: Live) = PlaceDao.savePlace(place)
    fun getSavedPlace() = PlaceDao.getSavedPlace()
    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}

