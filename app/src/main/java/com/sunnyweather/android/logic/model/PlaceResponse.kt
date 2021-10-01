package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(val status: String,
                         val info: String,
                         val key: String,
                         val lives: List<Live>)

data class Live(val city: String, val adcode: String,
                val weather: String,
                val temperature: String,
                val reporttime: String,
                 val province: String)

//data class PlaceResponse(val status: String, val places: List<Place>)
//
//data class Place(val name: String, val location: Location,
//@SerializedName("formatted_address") val address: String)

data class Location(val lng: String, val lat: String)